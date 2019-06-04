package org.openhab.binding.deconz.internal.api.rest;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.openhab.binding.deconz.internal.api.contract.Light;
import org.openhab.binding.deconz.internal.api.contract.LightState;
import org.openhab.binding.deconz.internal.constants.Web;
import org.openhab.binding.deconz.internal.exception.NotYetImplementedException;

import com.google.gson.Gson;

public class LightAPI {
    private static final String API = "lights";
    private static final Gson gson = new Gson();

    // GET /api/<apikey>/lights
    public static Request getAllLights(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.GET);
    }

    // POST /api/<apikey>/lights
    public static Request searchNewLights(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.POST);
    }

    // GET /api/<apikey>/lights/new
    public static Request getNewLights(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, "new")).method(HttpMethod.GET);
    }

    // GET /api/<apikey>/lights/<id>
    public static Request getLightState(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.GET);
    }

    // GET /api/<apikey>/lights/<id>/data?maxrecords=<maxrecords>&fromtime=<ISO 8601>
    public static Request getLightData(Request req, @Nullable String apikey) throws NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    // PUT, PATCH /api/<apikey>/lights/<id>/state
    public static Request setLightState(Request req, @Nullable String apikey, String id, LightState state) {
        return req.path(Web.createPath(apikey, API, id, "state")).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(state)), "application/json");
    }

    // PUT, PATCH /api/<apikey>/lights/<id>
    public static Request setLightAttributes(Request req, @Nullable String apikey, String id, Light light) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(light)), "application/json");
    }

    // GET /api/<apikey>/lights/<id>/connectivity
    public static Request getConnectivity(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, "connectivity")).method(HttpMethod.GET);
    }

    // DELETE /api/<apikey>/lights/<id>
    public static Request deleteLight(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.DELETE);
    }

    // DELETE /api/<apikey>/lights/<id>/scenes
    public static Request removeAllScenes(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, "scenes")).method(HttpMethod.DELETE);
    }

    // DELETE /api/<apikey>/lights/<id>/groups
    public static Request removeAllGroups(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, "groups")).method(HttpMethod.DELETE);
    }
}
