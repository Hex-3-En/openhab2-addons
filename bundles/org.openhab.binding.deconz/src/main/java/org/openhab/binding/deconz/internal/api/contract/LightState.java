package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.Nullable;

public interface LightState {
    public @Nullable String alert();

    public @Nullable Integer bri();

    public @Nullable String colormode();

    public @Nullable Integer ct();

    public @Nullable String effect();

    public @Nullable Integer hue();

    public @Nullable Boolean on();

    public @Nullable Boolean reachable();

    public @Nullable Integer sat();

    public @Nullable Float[] xy();
}
