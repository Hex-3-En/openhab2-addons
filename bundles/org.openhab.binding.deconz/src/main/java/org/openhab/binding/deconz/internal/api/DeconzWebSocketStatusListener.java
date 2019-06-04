package org.openhab.binding.deconz.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public interface DeconzWebSocketStatusListener {
    public void wsConnectionError(@Nullable Throwable e);

    public void wsConnectionEstablished();

    public void wsConnectionLost(String reason);
}
