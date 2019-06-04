package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public class Config implements SensorConfig {
    private @Nullable Boolean on;
    private @Nullable Boolean reachable;
    private @Nullable Integer battery;
    private @Nullable Integer temperature;

    @Override
    public @Nullable Boolean on() {
        return on;
    }

    @Override
    public @Nullable Boolean reachable() {
        return reachable;
    }

    @Override
    public @Nullable Integer battery() {
        return battery;
    }

    @Override
    public @Nullable Integer temperature() {
        return temperature;
    }
}
