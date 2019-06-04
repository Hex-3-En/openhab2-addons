package org.openhab.binding.deconz.internal.api.contract;

import java.util.Arrays;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.deconz.internal.constants.Binding;

@NonNullByDefault
public class Message implements Event, Sensor, Light {
    // -------------------Event Fields
    /**
     * event type
     * <li>added</li>
     * <li>changed</li>
     * <li>deleted</li>
     * <li>scene-called</li>
     */
    private @Nullable String e;
    /**
     * resource type
     * <li>sensors</li>
     * <li>lights</li>
     * <li>groups</li>
     * <li>scenes</li>
     */
    private @Nullable String r;
    /**
     * message type
     *
     * nothing but event, as only events are broadcastet via websocket
     * (scource code of deCONZ-rest-plugin v.2.05.55 / 14.1.2019)
     */
    private @Nullable String t;
    /**
     * resource id within its resource type
     */
    private @Nullable String id;
    /**
     * group id for scene-called event
     */

    private @Nullable String gid;
    /**
     * scene id for scene-called event
     */
    private @Nullable String scid;

    // -------------------Device Fields
    private @Nullable String etag;
    private @Nullable String modelid;
    private @Nullable String name;
    private @Nullable String swversion;
    private @Nullable String type;
    private @Nullable String uniqueid; // "00:0b:57:ff:fe:94:6b:dd-01-1000"

    private @Nullable State state;
    private @Nullable Config config;
    // -------------------Light Fields
    @Deprecated
    /**
     * Kept as long as contained in API
     */
    private @Nullable Boolean hascolor;
    private @Nullable String manufacturer;
    private @Nullable Integer ctmin;
    private @Nullable Integer ctmax;
    private @Nullable Integer powerup;
    private @Nullable Integer poweronlevel;
    private @Nullable Integer poweronct;
    private @Nullable Integer levelmin;
    private @Nullable Integer configid;
    /**
     * Currently unused, future placeholder
     * from http://dresden-elektronik.github.io/deconz-rest-doc/lights/#getstate
     * but actually deprecated on hue, so we will see...
     */
    private @Nullable Object pointsymbol;
    // -------------------Sensor Fields
    private @Nullable String manufacturername;
    /** the Zigbee endpoint (cluster) **/
    private @Nullable String ep;

    // -------------------Group Fields
    // Nothing here, so thats what to do :D
    // -------------------Method Area
    @Override
    public String etag() {
        if (etag != null) {
            return etag;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public @Nullable String modelid() {
        return modelid;
    }

    @Override
    public String name() {
        if (name != null) {
            return name;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public @Nullable String swversion() {
        return swversion;
    }

    @Override
    public String type() {
        if (type != null) {
            return type;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String uniqueid() {
        if (uniqueid != null) {
            return uniqueid;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public @Nullable Config config() {
        return config;
    }

    @Override
    public @Nullable State state() {
        return state;
    }

    @Override
    @Deprecated
    public @Nullable Boolean hascolor() {
        return hascolor;
    }

    @Override
    public @Nullable String manufacturer() {
        return manufacturer;
    }

    @Override
    public @Nullable Object pointsymbol() {
        return pointsymbol;
    }

    @Override
    public @Nullable String manufacturername() {
        return manufacturername;
    }

    @Override
    public String ep() {
        if (ep != null) {
            return ep;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String e() {
        if (e != null) {
            return e;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String r() {
        if (r != null) {
            return r;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String t() {
        if (t != null) {
            return t;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String id() {
        if (id != null) {
            return id;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String gid() {
        if (gid != null) {
            return gid;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public String scid() {
        if (scid != null) {
            return scid;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public @Nullable Integer ctmin() {
        return ctmin;
    }

    @Override
    public @Nullable Integer ctmax() {
        return ctmax;
    }

    @Override
    public @Nullable Integer powerup() {
        return powerup;
    }

    @Override
    public @Nullable Integer poweronlevel() {
        return poweronlevel;
    }

    @Override
    public @Nullable Integer poweronct() {
        return poweronct;
    }

    @Override
    public @Nullable Integer levelmin() {
        return levelmin;
    }

    @Override
    public @Nullable Integer configid() {
        return configid;
    }

    @Override
    /**
     * only needed in case of some exceptions, so we can get the current state
     */
    public String toString() {
        StringBuilder builder = new StringBuilder("Instance ");
        builder.append(super.toString());
        builder.append(" of class Message:\n");
        Arrays.stream(this.getClass().getFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                builder.append(f.getName()).append(": ").append(f.get(this)).append("\t");
                f.setAccessible(false);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // this should actually be impossible
                e.printStackTrace();
            }
        });
        return builder.toString();
    }

    @Override
    public boolean hasState() {
        return state != null;
    }

    @Override
    public State getState() {
        if (state != null) {
            return state;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }

    @Override
    public boolean hasConfig() {
        return config != null;
    }

    @Override
    public Config getConfig() {
        if (config != null) {
            return config;
        } else {
            throw new NullPointerException(Binding.EXCEPTION_VALUE_NOT_PRESENT);
        }
    }
}
