package org.openhab.binding.deconz.internal.api;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.openhab.binding.deconz.internal.api.contract.BeeDeviceConfig;
import org.openhab.binding.deconz.internal.api.contract.Light;
import org.openhab.binding.deconz.internal.api.contract.Sensor;

@NonNullByDefault
public interface DeconzRestClientListener {
    void setStatus(ThingStatus status, ThingStatusDetail statusDetail, String description);

    void updateSensors(Map<String, Sensor> sensors);

    void updateSensor(String sensorId, Sensor sensor);

    void updateLights(Map<String, Light> Lights);

    void updateLight(String lightId, Light light);

    void updateConfig(BeeDeviceConfig config);

    void setApiKey(String apiKey);

    void notifyUser(String message);
}
