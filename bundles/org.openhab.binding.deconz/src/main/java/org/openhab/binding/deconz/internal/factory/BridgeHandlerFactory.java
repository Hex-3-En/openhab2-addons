/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.factory;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.eclipse.smarthome.io.net.http.HttpClientFactory;
import org.eclipse.smarthome.io.net.http.WebSocketFactory;
import org.openhab.binding.deconz.internal.api.AsyncHttpClient;
import org.openhab.binding.deconz.internal.constants.ThingType;
import org.openhab.binding.deconz.internal.handler.DeconzBridgeHandler;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link BridgeHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author David Graeff - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.deconz")
@NonNullByDefault
public class BridgeHandlerFactory extends BaseThingHandlerFactory {
    private @NonNullByDefault({}) WebSocketFactory webSocketFactory;
    private @NonNullByDefault({}) HttpClientFactory httpClientFactory;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return ThingType.BRIDGE.equals(thingTypeUID);
    }

    @Reference
    public void setWebSocketFactory(WebSocketFactory factory) {
        webSocketFactory = factory;
    }

    @Reference
    public void setHttpClientFactory(HttpClientFactory factory) {
        httpClientFactory = factory;
    }

    public void unsetWebSocketFactory(WebSocketFactory factory) {
        webSocketFactory = null;
    }

    public void unsetHttpClientFactory(HttpClientFactory factory) {
        httpClientFactory = null;
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        return new DeconzBridgeHandler((Bridge) thing, webSocketFactory,
                AsyncHttpClient.INSTANCE.withClient(httpClientFactory.getCommonHttpClient()));
    }
}
