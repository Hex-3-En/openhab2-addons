package org.openhab.binding.deconz.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.deconz.internal.api.contract.Light;
import org.openhab.binding.deconz.internal.api.contract.Sensor;

@NonNullByDefault
public interface DeconzWebSocketDiscoveryListener {
    public void sensorResourceAdded(Sensor sensor);

    public void sensorResourceDeleted(Sensor sensor);

    public void lightResourceAdded(Light light);

    public void lightResourceDeleted(Light light);
}
