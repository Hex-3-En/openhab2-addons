package org.openhab.binding.deconz.internal.api.rest;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.openhab.binding.deconz.internal.api.contract.Message;
import org.openhab.binding.deconz.internal.api.contract.Sensor;
import org.openhab.binding.deconz.internal.api.contract.SensorConfig;
import org.openhab.binding.deconz.internal.api.contract.SensorState;
import org.openhab.binding.deconz.internal.constants.Web;
import org.openhab.binding.deconz.internal.exception.NotYetImplementedException;

import com.google.gson.Gson;

@NonNullByDefault
public class SensorAPI {
    private static final String API = "sensors";
    private static final Gson gson = new Gson();

    // GET /api/<apikey>/sensors
    public static Request getAllSensors(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.GET);
    }

    // GET /api/<apikey>/sensors/new
    public static Request getNewSensors(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, "new")).method(HttpMethod.GET);
    }

    // GET /api/<apikey>/sensors/<id>
    public static Request getSensor(Request req, @Nullable String apikey, @Nullable String id) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.GET);
    }

    // GET /api/<apikey>/sensors/<id>/data?maxrecords=<maxrecords>&fromtime=<ISO 8601>
    public static Request getSensorData(Request req, @Nullable String apikey, @Nullable String id, String... params)
            throws NotYetImplementedException {
        throw new NotYetImplementedException();
        // TODO: implement if necessary
        // return req.path(Web.createPath(apikey, API, id, "data")).method(HttpMethod.GET);
    }

    // POST /api/<apikey>/sensors
    public static Request searchNewSensors(Request req, @Nullable String apikey) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.POST);
    }

    public static Request createSensor(Request req, @Nullable String apikey, Message sensor) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.POST)
                .content(new StringContentProvider(gson.toJson(sensor)), "application/json");
    }

    // PUT, PATCH /api/<apikey>/sensors/<id>
    public static Request updateSensor(Request req, @Nullable String apikey, String id, Sensor sensor) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(sensor)), "application/json");
    }

    // DELETE /api/<apikey>/sensors/<id>
    public static Request deleteSensor(Request req, @Nullable String apikey, String id) {
        return req.path(Web.createPath(apikey, API, id, null)).method(HttpMethod.DELETE);
    }

    // PUT, PATCH /api/<apikey>/sensors/<id>/config
    public static Request changeSensorConfig(Request req, @Nullable String apikey, String id, SensorConfig config) {
        return req.path(Web.createPath(apikey, API, id, "config")).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(config)), "application/json");
    }

    // PUT, PATCH /api/<apikey>/sensors/<id>/state
    public static Request changeSensorState(Request req, @Nullable String apikey, String id, SensorState state) {
        return req.path(Web.createPath(apikey, API, id, "state")).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(state)), "application/json");
    }
}
