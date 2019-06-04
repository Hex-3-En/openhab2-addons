/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.discovery;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerService;
import org.openhab.binding.deconz.internal.api.DeconzWebSocketDiscoveryListener;
import org.openhab.binding.deconz.internal.api.contract.Light;
import org.openhab.binding.deconz.internal.api.contract.Sensor;
import org.openhab.binding.deconz.internal.constants.Binding;
import org.openhab.binding.deconz.internal.handler.DeconzBridgeHandler;
import org.osgi.service.component.annotations.Component;

/**
 * Every bridge will add its discovered sensors to this discovery service to make them
 * available to the framework.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
@Component(service = DiscoveryService.class, immediate = true)
public class ThingDiscoveryService extends AbstractDiscoveryService
        implements ThingHandlerService, DeconzWebSocketDiscoveryListener {

    private @NonNullByDefault({}) DeconzBridgeHandler handler;

    public ThingDiscoveryService() {
        super(Stream.of(Binding.THING_TYPE_PRESENCE_SENSOR, Binding.THING_TYPE_POWER_SENSOR,
                Binding.THING_TYPE_DAYLIGHT_SENSOR, Binding.THING_TYPE_SWITCH, Binding.THING_TYPE_OPENCLOSE_SENSOR)
                .collect(Collectors.toSet()), 0, true);
    }

    /**
     * Perform a new bridge full state request.
     */
    @Override
    protected void startScan() {
        // TODO whoooooooooooooooa
    }

    @Override
    protected void startBackgroundDiscovery() {

    }

    @Override
    protected void stopBackgroundDiscovery() {

    }

    /**
     * Add a sensor device to the discovery inbox.
     *
     * @param sensor    The sensor description
     * @param bridgeUID The bridge UID
     */
    private void addDevice(String sensorID, Sensor sensor) {
        ThingTypeUID thingTypeUID;
        if (sensor.type.contains("Daylight")) { // Deconz specific: Software simulated daylight sensor
            thingTypeUID = Binding.THING_TYPE_DAYLIGHT_SENSOR;
        } else if (sensor.type.contains("Power")) { // ZHAPower, CLIPPower
            thingTypeUID = Binding.THING_TYPE_POWER_SENSOR;
        } else if (sensor.type.contains("Presence")) { // ZHAPresence, CLIPPrensence
            thingTypeUID = Binding.THING_TYPE_PRESENCE_SENSOR;
        } else if (sensor.type.contains("Switch")) { // ZHASwitch
            thingTypeUID = Binding.THING_TYPE_SWITCH;
        } else if (sensor.type.contains("LightLevel")) { // ZHALightLevel
            thingTypeUID = Binding.THING_TYPE_LIGHT_SENSOR;
        } else if (sensor.type.contains("ZHATemperature")) { // ZHATemperature
            thingTypeUID = Binding.THING_TYPE_TEMPERATURE_SENSOR;
        } else if (sensor.type.contains("ZHAHumidity")) { // ZHAHumidity
            thingTypeUID = Binding.THING_TYPE_HUMIDITY_SENSOR;
        } else if (sensor.type.contains("ZHAOpenClose")) { // ZHAOpenClose
            thingTypeUID = Binding.THING_TYPE_OPENCLOSE_SENSOR;
        } else {
            return;
        }

        ThingUID uid = new ThingUID(thingTypeUID, handler.getThing().getUID(),
                sensor.uniqueid.replaceAll("[^a-z0-9\\[\\]]", ""));

        DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(uid).withBridge(handler.getThing().getUID())
                .withLabel(sensor.name + " (" + sensor.manufacturername + ")").withProperty("id", sensorID)
                .withProperty("uid", sensor.uniqueid).withRepresentationProperty("uid").build();
        thingDiscovered(discoveryResult);
    }

    @Override
    public void setThingHandler(@Nullable ThingHandler handler) {
        this.handler = (DeconzBridgeHandler) handler;
        this.handler.setDiscoveryService(this);
    }

    @Override
    public ThingHandler getThingHandler() {
        return handler;
    }

    @Override
    public void deactivate() {
        removeOlderResults(getTimestampOfLastScan());
        super.deactivate();
    }

    /**
     * Call this method when a full bridge state request has been performed and either the sensors
     * are known or a failure happened.
     *
     * @param sensors The sensors or null.
     */
    public void stateRequestFinished(@Nullable Map<String, Sensor> sensors) {
        stopScan();
        removeOlderResults(getTimestampOfLastScan());
        if (sensors != null) {
            sensors.forEach(this::addDevice);
        }
    }

    @Override
    public void sensorResourceAdded(Sensor sensor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sensorResourceDeleted(Sensor sensor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void lightResourceAdded(Light light) {
        // TODO Auto-generated method stub

    }

    @Override
    public void lightResourceDeleted(Light light) {
        // TODO Auto-generated method stub

    }
}
