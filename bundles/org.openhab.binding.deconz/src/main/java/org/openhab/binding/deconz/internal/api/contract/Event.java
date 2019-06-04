package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public interface Event {

    public static final String TYPE_ADDED = "added";
    public static final String TYPE_CHANGED = "changed";
    public static final String TYPE_DELETED = "deleted";
    public static final String TYPE_SCENE_CALLED = "scene-called";
    public static final String MESSAGE_TYPE_EVENT = "event";
    public static final String RESOURCE_SENSORS = "sensors";
    public static final String RESOURCE_LIGHTS = "lights";
    public static final String RESOURCE_GROUPS = "groups";
    public static final String RESOURCE_SCENES = "scenes";

    /**
     * event type
     * <li>added</li>
     * <li>changed</li>
     * <li>deleted</li>
     * <li>scene-called</li>
     */
    public String e();

    /**
     * resource type
     * <li>sensors</li>
     * <li>lights</li>
     * <li>groups</li>
     * <li>scenes</li>
     */
    public String r();

    /**
     * message type
     *
     * nothing but event, as only events are broadcastet via websocket
     * (scource code of deCONZ-rest-plugin v.2.05.55 / 14.1.2019)
     */
    public String t();

    /**
     * resource id within its resource type
     */
    public String id();

    /**
     * group id for scene-called event
     */
    public String gid();

    /**
     * scene id for scene-called event
     */
    public String scid();

    public boolean hasState();

    public State getState();

    public boolean hasConfig();

    public Config getConfig();

    // public @Nullable default LightState getLightState() {
    // return this.r.equals("lights") && this.state != null ? this.state : null;
    // }
}
