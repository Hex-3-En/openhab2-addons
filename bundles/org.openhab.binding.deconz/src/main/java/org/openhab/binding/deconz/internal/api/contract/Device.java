package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public abstract interface Device {
    public String etag();

    public @Nullable String modelid();

    public String name();

    public @Nullable String swversion();

    public String type();

    public String uniqueid(); // "00:0b:57:ff:fe:94:6b:dd-01-1000"

    public @Nullable Config config();

    public @Nullable State state();

}
