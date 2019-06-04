/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.storage.Storage;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerService;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.io.net.http.WebSocketFactory;
import org.openhab.binding.deconz.internal.api.DeconzRestClient;
import org.openhab.binding.deconz.internal.api.DeconzRestClientListener;
import org.openhab.binding.deconz.internal.api.DeconzWebSocket;
import org.openhab.binding.deconz.internal.api.DeconzWebSocketStatusListener;
import org.openhab.binding.deconz.internal.api.contract.BeeDeviceConfig;
import org.openhab.binding.deconz.internal.api.contract.Light;
import org.openhab.binding.deconz.internal.api.contract.Sensor;
import org.openhab.binding.deconz.internal.constants.Config;
import org.openhab.binding.deconz.internal.constants.Web;
import org.openhab.binding.deconz.internal.discovery.ThingDiscoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The bridge Thing is responsible for requesting all available sensors and switches and propagate
 * them to the discovery service.
 *
 * It performs the authorization process if necessary.
 *
 * A websocket connection is established to the deCONZ software and kept alive.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public class DeconzBridgeHandler extends BaseBridgeHandler
        implements DeconzWebSocketStatusListener, DeconzRestClientListener {
    private final Logger logger = LoggerFactory.getLogger(DeconzBridgeHandler.class);
    private @NonNullByDefault({}) ThingDiscoveryService thingDiscoveryService;
    private DeconzRestClient client;
    private DeconzWebSocket websocket;
    private DeconzBridgeConfig config = new DeconzBridgeConfig();
    private @Nullable ScheduledFuture<?> pollingJob;
    private @Nullable ScheduledFuture<?> pollingSensorsJob;
    private final Storage<String> storage;

    public DeconzBridgeHandler(Bridge thing, WebSocketFactory webSocketFactory, HttpClient http,
            Storage<String> storage) {
        super(thing);
        String websocketID = thing.getUID().getAsString().replace(':', '-');
        websocketID = websocketID.length() < 3 ? websocketID : websocketID.substring(websocketID.length() - 20);
        this.websocket = new DeconzWebSocket(this, webSocketFactory.createWebSocketClient(websocketID));
        this.storage = storage;
        this.client = new DeconzRestClient(http, this);
    }

    /**
     * This starts the no-websocket-fallback-process of polling from deconz.
     */
    private void startPolling() {
        pollingJob = scheduler.scheduleWithFixedDelay(() -> client.pollRegular(), Web.REST_POLLING_REGULAR_MILLIS,
                Web.REST_POLLING_REGULAR_MILLIS, TimeUnit.MILLISECONDS);
        pollingSensorsJob = scheduler.scheduleWithFixedDelay(() -> client.pollSensors(), Web.REST_POLLING_QUICK_MILLIS,
                Web.REST_POLLING_QUICK_MILLIS, TimeUnit.MILLISECONDS);

    }

    private void stopPolling() {
        if (pollingJob != null) {
            pollingJob.cancel(true);
            pollingJob = null;
        }
        if (pollingSensorsJob != null) {
            pollingSensorsJob.cancel(true);
            pollingSensorsJob = null;
        }
    }

    @Override
    public void dispose() {
        stopPolling();
        client.dispose();
        websocket.dispose();
    }

    @Override
    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return Collections.singleton(ThingDiscoveryService.class);
    }

    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        Configuration oldConf = editConfiguration();
        super.handleConfigurationUpdate(configurationParameters);
        Configuration newConf = editConfiguration();
        if (!oldConf.get(Config.HOST).equals(newConf.get(Config.HOST))
                || !oldConf.get(Config.PORT).equals(newConf.get(Config.PORT))) {
            adressChanged();
        }
        if (!oldConf.get(Config.WEBSOCKETPORT).equals(newConf.get(Config.WEBSOCKETPORT))) {
            websocketChanged();
        }
        /**
         * NOT YET IMPLEMENTED
         * if (!oldConf.get(CONFIG_USER).equals(newConf.get(CONFIG_USER))) {
         * handleUsernameChange();
         * }
         */
    }

    private void adressChanged() {

    }

    private void websocketChanged() {

    }

    /**
     * NOT YET IMPLEMENTED
     * private void handleUsernameChange() {
     * // TODO: Add implementation with user/pass impl
     * }
     */

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    @Override
    public void initialize() {
        logger.debug("Initializing!");
        config = getConfigAs(DeconzBridgeConfig.class);
        config.apikey = storage.get(Config.APIKEY);
        client.initialize(config.host, config.port, config.apikey);
    }

    @Override
    public void wsConnectionError(@Nullable Throwable e) {
        if (e != null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Unknown reason");
        }
    }

    @Override
    public void wsConnectionEstablished() {
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void wsConnectionLost(String reason) {
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, reason);
    }

    @Override
    public void setStatus(ThingStatus status, ThingStatusDetail statusDetail, String description) {
        updateStatus(status, statusDetail, description);
    }

    @Override
    public void updateSensors(Map<String, Sensor> sensors) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateSensor(String sensorId, Sensor sensor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateLights(Map<String, Light> Lights) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateLight(String lightId, Light light) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateConfig(BeeDeviceConfig config) {
        // TODO Auto-generated method stub

    }

    /**
     * Called by the {@link ThingDiscoveryService}. Informs the bridge handler about the service.
     *
     * @param thingDiscoveryService The service
     */
    public void setDiscoveryService(ThingDiscoveryService thingDiscoveryService) {
        this.thingDiscoveryService = thingDiscoveryService;
    }

    @Override
    public void setApiKey(String apiKey) {
        Configuration conf = this.editConfiguration();
        String oldEntry = (String) conf.put(Config.APIKEY, apiKey);
        if (oldEntry != null && !oldEntry.equals(apiKey)) {
            updateConfiguration(conf);
            this.config = getConfigAs(DeconzBridgeConfig.class);
            client.initialize(config.host, config.port, config.apikey);
        }
    }

    @Override
    public void notifyUser(String message) {
        // TODO Auto-generated method stub

    }

    /**
     * Starts the websocket connection.
     * {@link #requestFullState} need to be called first to obtain the websocket port.
     */
    /*
     * private void startWebsocket() {
     * if (websocket.isConnected() || websocketport == 0) {
     * return;
     * }
     *
     * String host = config.host;
     * if (host.indexOf(':') > 0) {
     * host = host.substring(0, host.indexOf(':'));
     * }
     * stopScheduledJob();
     * // scheduledFuture = scheduler.scheduleWithFixedDelay(this::startWebsocket, POLL_FREQUENCY_SEC,
     * // POLL_FREQUENCY_SEC,
     * // TimeUnit.SECONDS);
     *
     * websocket.start(host + ":" + String.valueOf(websocketport));
     * }
     */
}
