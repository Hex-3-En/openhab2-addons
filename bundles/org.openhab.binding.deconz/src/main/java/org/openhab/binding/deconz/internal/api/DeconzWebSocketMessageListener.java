package org.openhab.binding.deconz.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.deconz.internal.api.contract.Event;

@NonNullByDefault
public interface DeconzWebSocketMessageListener {
    public void wsEvent(Event event);

    public String getName();
}
