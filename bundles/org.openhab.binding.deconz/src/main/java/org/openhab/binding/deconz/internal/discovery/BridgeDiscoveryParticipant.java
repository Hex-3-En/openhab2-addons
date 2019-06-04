/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.discovery;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.upnp.UpnpDiscoveryParticipant;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.jupnp.model.meta.DeviceDetails;
import org.jupnp.model.meta.RemoteDevice;
import org.openhab.binding.deconz.internal.constants.Config;
import org.openhab.binding.deconz.internal.constants.ThingType;
import org.osgi.service.component.annotations.Component;

/**
 * Discover deCONZ software instances. They announce themselves as HUE bridges,
 * and their REST API is compatible to HUE bridges. But they also provide a websocket
 * real-time channel for sensors.
 *
 * We check for the manufacturer url string of "www.dresden-elektronik.de", as the gateway declares itself as Philips
 * Hue in most other details.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
@Component(service = UpnpDiscoveryParticipant.class, immediate = true)
public class BridgeDiscoveryParticipant implements UpnpDiscoveryParticipant {

    @Override
    public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
        return Collections.singleton(ThingType.BRIDGE);
    }

    @Override
    public @Nullable DiscoveryResult createResult(RemoteDevice device) {
        ThingUID uid = getThingUID(device);
        if (uid == null) {
            return null;
        }
        URL descriptorURL = device.getIdentity().getDescriptorURL();
        String UDN = device.getIdentity().getUdn().getIdentifierString();

        String host = descriptorURL.getHost();
        String name = "deCONZ @ " + device.getDetails().getFriendlyName();

        Map<String, Object> properties = new TreeMap<>();

        properties.put(Config.HOST, host);
        properties.put(Config.PORT, descriptorURL.getPort());

        return DiscoveryResultBuilder.create(uid).withProperties(properties).withLabel(name)
                .withRepresentationProperty(UDN).build();
    }

    @Override
    public @Nullable ThingUID getThingUID(RemoteDevice device) {
        DeviceDetails details = device.getDetails();
        if (details != null && details.getManufacturerDetails() != null
        // && "dresden elektronik".equals(details.getManufacturerDetails().getManufacturer())) {
                && "www.dresden-elektronik.de"
                        .equals(details.getManufacturerDetails().getManufacturerURI().getHost())) {
            return new ThingUID(ThingType.BRIDGE, details.getSerialNumber());
        }
        return null;
    }
}
