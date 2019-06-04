package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.Nullable;

public class State implements LightState, SensorState, GroupState {
    // --------------------sensor state fields---------------------------
    /** Alarm sensors provide this value. */
    private @Nullable Boolean alarm;
    /** Switches provide this value. */
    private @Nullable Integer buttonevent;
    /** carbonmonoxide sensors provide a boolean value. */
    private @Nullable Boolean carbonmonoxide;
    /** consumption sensors provide a boolean value. */
    private @Nullable Integer consumption;
    /** power? sensors provide a boolean value. */
    private @Nullable Integer current;
    /** Some presence sensors, the daylight sensor and all light sensors provide the "dark" boolean. */
    private @Nullable Boolean dark;
    /** The daylight sensor provides the "daylight" boolean. */
    private @Nullable Boolean daylight;
    /** fire sensors provide a boolean value. */
    private @Nullable Boolean fire;
    /** generic/virtual flag sensors could provide this boolean value. */
    private @Nullable Boolean flag;
    /** Humidity sensors provide a percent value. */
    private @Nullable Float humidity;
    /** deCONZ sends a last update string with every event. */
    private @Nullable String lastupdated;
    /** Light sensors can provide a lightlevel value (candela). */
    private @Nullable Integer lightlevel;
    /** any battery powered sensor could send this to indicate low battery. */
    private @Nullable Boolean lowbattery;
    /** Light sensors provide a lux value. */
    private @Nullable Integer lux;
    /** OpenClose sensors provide a boolean value. */
    private @Nullable Boolean open;
    /** Power sensors provide this value in Watts. */
    private @Nullable Integer power;
    /** Presence sensors provide this boolean. */
    private @Nullable Boolean presence;
    /** Pressure sensors provide a hPa value. */
    private @Nullable Integer pressure;
    /** Light sensors and the daylight sensor provide a status integer that can have various semantics. */
    private @Nullable Integer status;
    /** UNKNOWN sensors provide this boolean. */
    private @Nullable Boolean tampered;
    /** Temperature sensors provide a degrees value. */
    private @Nullable Integer temperature;
    /** vibration sensors provide a boolean value. */
    private @Nullable Boolean vibration;
    /** Power sensors could provide this value in Volt. */
    private @Nullable Integer voltage;
    /** water sensors provide a boolean value. */
    private @Nullable Boolean water;

    // --------------------light state fields----------------------------
    private @Nullable String alert;
    private @Nullable Integer bri;
    private @Nullable String colormode;
    private @Nullable Integer ct;
    private @Nullable String effect;
    private @Nullable Integer hue;
    private @Nullable Boolean on;
    private @Nullable Boolean reachable;
    private @Nullable Integer sat;
    private @Nullable Float[] xy;

    // --------------------group state fields----------------------------
    private @Nullable Boolean any_on;
    private @Nullable Boolean all_on;

    @Override
    public @Nullable Boolean dark() {
        return dark;
    }

    @Override
    public @Nullable Boolean daylight() {
        return daylight;
    }

    @Override
    public @Nullable Integer lux() {
        return lux;
    }

    @Override
    public @Nullable Integer temperature() {
        return temperature;
    }

    @Override
    public @Nullable Float humidity() {
        return humidity;
    }

    @Override
    public @Nullable Boolean open() {
        return open;
    }

    @Override
    public @Nullable Boolean fire() {
        return fire;
    }

    @Override
    public @Nullable Boolean water() {
        return water;
    }

    @Override
    public @Nullable Boolean vibration() {
        return vibration;
    }

    @Override
    public @Nullable Boolean carbonmonoxide() {
        return carbonmonoxide;
    }

    @Override
    public @Nullable Integer pressure() {
        return pressure;
    }

    @Override
    public @Nullable Boolean presence() {
        return presence;
    }

    @Override
    public @Nullable Integer power() {
        return power;
    }

    @Override
    public @Nullable Integer status() {
        return status;
    }

    @Override
    public @Nullable Integer buttonevent() {
        return buttonevent;
    }

    @Override
    public @Nullable String lastupdated() {
        return lastupdated;
    }

    @Override
    public @Nullable String alert() {
        return alert;
    }

    @Override
    public @Nullable Integer bri() {
        return bri;
    }

    @Override
    public @Nullable String colormode() {
        return colormode;
    }

    @Override
    public @Nullable Integer ct() {
        return ct;
    }

    @Override
    public @Nullable String effect() {
        return effect;
    }

    @Override
    public @Nullable Integer hue() {
        return hue;
    }

    @Override
    public @Nullable Boolean on() {
        return on;
    }

    @Override
    public @Nullable Boolean reachable() {
        return reachable;
    }

    @Override
    public @Nullable Integer sat() {
        return sat;
    }

    @Override
    public @Nullable Float[] xy() {
        return xy;
    }

    @Override
    public @Nullable Boolean any_on() {
        return any_on;
    }

    @Override
    public @Nullable Boolean all_on() {
        return all_on;
    }

    @Override
    public @Nullable Boolean alarm() {
        return alarm;
    }

    @Override
    public @Nullable Integer consumption() {
        return consumption;
    }

    @Override
    public @Nullable Integer current() {
        return current;
    }

    @Override
    public @Nullable Boolean flag() {
        return flag;
    }

    @Override
    public @Nullable Integer lightlevel() {
        return lightlevel;
    }

    @Override
    public @Nullable Boolean lowbattery() {
        return lowbattery;
    }

    @Override
    public @Nullable Boolean tampered() {
        return tampered;
    }

    @Override
    public @Nullable Integer voltage() {
        return voltage;
    }
}
