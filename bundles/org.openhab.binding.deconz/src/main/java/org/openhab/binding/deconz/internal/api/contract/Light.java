package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public interface Light extends Device {
    @Deprecated
    /**
     * Kept as long as contained in API
     */
    public @Nullable Boolean hascolor();

    public @Nullable String manufacturer();

    public @Nullable Integer ctmin();

    public @Nullable Integer ctmax();

    public @Nullable Integer powerup();

    public @Nullable Integer poweronlevel();

    public @Nullable Integer poweronct();

    public @Nullable Integer levelmin();

    public @Nullable Integer configid();

    /**
     * Currently unused, future placeholder
     * from http://dresden-elektronik.github.io/deconz-rest-doc/lights/#getstate
     * but actually deprecated on hue, so we will see...
     */
    public @Nullable Object pointsymbol();

    public @Nullable default LightState lightState() {
        return state();
    }
}
