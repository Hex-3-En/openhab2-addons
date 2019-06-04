package org.openhab.binding.deconz.internal.constants;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

public class ThingType {
    // Bridge Type UID
    public static final ThingTypeUID BRIDGE = new ThingTypeUID(Binding.ID, "deconz");
    // Sensor Thing Type UIDs
    public static final ThingTypeUID PRESENCE_SENSOR = new ThingTypeUID(Binding.ID, "presencesensor");
    public static final ThingTypeUID POWER_SENSOR = new ThingTypeUID(Binding.ID, "powersensor");
    public static final ThingTypeUID DAYLIGHT_SENSOR = new ThingTypeUID(Binding.ID, "daylightsensor");
    public static final ThingTypeUID LIGHT_SENSOR = new ThingTypeUID(Binding.ID, "lightsensor");
    public static final ThingTypeUID TEMPERATURE_SENSOR = new ThingTypeUID(Binding.ID, "temperaturesensor");
    public static final ThingTypeUID HUMIDITY_SENSOR = new ThingTypeUID(Binding.ID, "humiditysensor");
    public static final ThingTypeUID OPENCLOSE_SENSOR = new ThingTypeUID(Binding.ID, "openclosesensor");
    // Controller Thing Type UIDs
    public static final ThingTypeUID SWITCH = new ThingTypeUID(Binding.ID, "switch");
    // Light Thing Type UIDs
    // Actuator Thing Type UIDs
}
