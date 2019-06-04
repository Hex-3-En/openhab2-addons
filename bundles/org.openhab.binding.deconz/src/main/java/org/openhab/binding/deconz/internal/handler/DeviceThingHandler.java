/**
 *
 */
package org.openhab.binding.deconz.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.openhab.binding.deconz.internal.api.DeconzWebSocket;
import org.openhab.binding.deconz.internal.api.DeconzWebSocketMessageListener;
import org.openhab.binding.deconz.internal.api.contract.Config;
import org.openhab.binding.deconz.internal.api.contract.Device;
import org.openhab.binding.deconz.internal.api.contract.Event;
import org.openhab.binding.deconz.internal.api.contract.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @author admin
 *
 */
@NonNullByDefault
public abstract class DeviceThingHandler extends BaseThingHandler implements DeconzWebSocketMessageListener {
    protected final Logger logger = LoggerFactory.getLogger(DeviceThingHandler.class);
    protected DeviceThingConfig config = new DeviceThingConfig();
    protected final Gson gson = new Gson();
    private final String name;
    private @Nullable DeconzWebSocket socket;

    /**
     * @param thing
     */
    public DeviceThingHandler(Thing thing) {
        super(thing);
        this.name = thing.getUID().getAsString();
    }

    @Override
    public void wsEvent(Event event) {
        if (!event.id().equals(config.id) || !validEventDirection(event)) {
            logger.error("The event has been directed to the wrong handler and this should never happen!");
            return;
        }
        if (event.hasConfig()) {
            handleConfigEvent(event.getConfig());
        } else if (event.hasState()) {

        }
    }

    @Override
    public String getName() {
        return name;
    }

    private boolean validEventDirection(Event event) {
        if (!event.e().equals(Event.TYPE_CHANGED)) {
            return false;
        } else if (event.r().equals(Event.RESOURCE_GROUPS) || event.r().equals(Event.RESOURCE_SCENES)) {
            return false;
        } else if (((this instanceof LightThingHandler) || (this instanceof ActuatorThingHandler))
                && !(event.r().equals(Event.RESOURCE_LIGHTS))) {
            return false;
        } else if (((this instanceof SensorThingHandler) || (this instanceof ControllerThingHandler))
                && !(event.r().equals(Event.RESOURCE_SENSORS))) {
            return false;
        }
        return true;
    }

    protected abstract void handleConfigEvent(Config config);

    protected abstract void handleStateEvent(State state);

    protected abstract void handlePropertyEvent(Device device);

}
