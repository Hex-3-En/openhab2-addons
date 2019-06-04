/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.api;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.binding.deconz.internal.api.contract.Event;
import org.openhab.binding.deconz.internal.api.contract.Message;
import org.openhab.binding.deconz.internal.constants.Web;
import org.openhab.binding.deconz.internal.discovery.ThingDiscoveryService;
import org.openhab.binding.deconz.internal.handler.ActuatorThingHandler;
import org.openhab.binding.deconz.internal.handler.ControllerThingHandler;
import org.openhab.binding.deconz.internal.handler.LightThingHandler;
import org.openhab.binding.deconz.internal.handler.SensorThingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Establishes and keeps a websocket connection to the deCONZ software.
 *
 * The connection is closed by deCONZ now and then and needs to be re-established.
 *
 * @author David Graeff - Initial contribution
 */
@WebSocket
@NonNullByDefault
public class DeconzWebSocket {
    private final Logger logger = LoggerFactory.getLogger(DeconzWebSocket.class);

    private final WebSocketClient client;
    private final DeconzWebSocketStatusListener bridge;
    private @Nullable DeconzWebSocketDiscoveryListener discoveryService = null;
    // private final Map<String, SensorWebSocketListener> valueListener = new HashMap<>();
    private final Gson gson = new Gson();
    private boolean connected = false;
    private final Map<String, DeconzWebSocketMessageListener> registeredLightResources = Collections.emptyMap();
    private final Map<String, DeconzWebSocketMessageListener> registeredSensorResources = Collections.emptyMap();

    public DeconzWebSocket(DeconzWebSocketStatusListener bridge, WebSocketClient client) {
        this.bridge = bridge;
        this.client = client;
        this.client.setMaxIdleTimeout(Web.WEBSOCKET_TIMEOUT_MILLIS);
    }

    public void start(String address) {
        if (connected) {
            logger.debug("We have an active Connection, but you want to connect to {}, so we do...", address);
            stop();
        }
        try {
            URI destUri = URI.create("ws://" + address);

            client.start();

            logger.debug("Connecting to: {}", destUri);
            client.connect(this, destUri).get();
        } catch (Exception e) {
            bridge.wsConnectionError(e);
        }
    }

    private void stop() {
        try {
            connected = false;
            client.stop();
        } catch (Exception e) {
            logger.warn("Error while closing connection: {}", e);
        }
    }

    public void dispose() {
        stop();
        client.destroy();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        connected = true;
        logger.debug("Connect: {}", session.getRemoteAddress().getAddress());
        bridge.wsConnectionEstablished();
    }

    @SuppressWarnings("null") // the field discoveryService creates false positives after null check here...
    @OnWebSocketMessage
    public void onMessage(String message) {
        Message messageObject = gson.fromJson(message, Message.class);
        if (messageObject.e().equals(Event.TYPE_CHANGED)) {
            DeconzWebSocketMessageListener listener = null;
            if (messageObject.r().equals(Event.RESOURCE_LIGHTS)) {
                listener = registeredLightResources.get(messageObject.id());
            } else if (messageObject.r().equals(Event.RESOURCE_SENSORS)) {
                listener = registeredSensorResources.get(messageObject.id());
            }
            if (listener != null) {
                listener.wsEvent(messageObject);
            } else {
                logger.trace("Event on Websocket discarded because device not registered as thing.");
            }
        } else if (messageObject.e().equals(Event.TYPE_SCENE_CALLED)) {
            logger.trace("Event on websocket discarded because ScenesAPI not implemented.");
        } else if (messageObject.e().equals(Event.TYPE_ADDED) || messageObject.e().equals(Event.TYPE_DELETED)) {
            if (discoveryService != null) {
                if (messageObject.e().equals(Event.TYPE_ADDED)) {
                    if (messageObject.r().equals(Event.RESOURCE_LIGHTS)) {
                        discoveryService.lightResourceAdded(messageObject);
                    } else if (messageObject.r().equals(Event.RESOURCE_SENSORS)) {
                        discoveryService.sensorResourceAdded(messageObject);
                    }
                } else if (messageObject.e().equals(Event.TYPE_DELETED)) {
                    if (messageObject.r().equals(Event.RESOURCE_LIGHTS)) {
                        discoveryService.lightResourceDeleted(messageObject);
                    } else if (messageObject.r().equals(Event.RESOURCE_SENSORS)) {
                        discoveryService.sensorResourceDeleted(messageObject);
                    }
                }
            } else {
                logger.debug(
                        "There is no DiscoveryService registered, so we should actually not receive anything in this situation!");
            }
        } else {
            logger.error("Wild UNKNOWNEVENT appeared! Seems we need to implement another type for onMessage().");
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        connected = false;
        bridge.wsConnectionError(cause);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        connected = false;
        bridge.wsConnectionLost(reason);
    }

    public boolean isConnected() {
        return connected;
    }

    public void registerDiscoveryService(ThingDiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    public void unregisterDiscoveryService(ThingDiscoveryService discoveryService) {
        if (discoveryService.equals(this.discoveryService)) {
            this.discoveryService = null;
        } else {
            logger.warn("Tried to unregister not registered discovery service.");
        }
    }

    public void registerResource(String id, DeconzWebSocketMessageListener device) {
        if (device instanceof LightThingHandler || device instanceof ActuatorThingHandler) {
            registeredLightResources.put(id, device);
        }
        if (device instanceof SensorThingHandler || device instanceof ControllerThingHandler) {
            registeredSensorResources.put(id, device);
        }
    }

    public void unregisterResource(String id, DeconzWebSocketMessageListener device) {
        boolean removed = false;
        if (device instanceof LightThingHandler || device instanceof ActuatorThingHandler) {
            removed = registeredLightResources.remove(id, device);
        }
        if (device instanceof SensorThingHandler || device instanceof ControllerThingHandler) {
            removed = registeredSensorResources.remove(id, device);
        }
        if (removed) {
            logger.trace("Unregistered device {} from websocket", device.getName());
        } else {
            logger.error("Could not remove device {} with id {} from websocket", device.getName(), id);
        }
    }
}
