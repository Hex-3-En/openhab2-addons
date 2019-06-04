package org.openhab.binding.deconz.internal.api.rest;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.openhab.binding.deconz.internal.api.contract.BeeDeviceConfig;
import org.openhab.binding.deconz.internal.constants.Web;
import org.openhab.binding.deconz.internal.exception.MissingParamException;

import com.google.gson.Gson;

@NonNullByDefault
public class ConfigurationAPI {
    private static final String API = "config";
    private static final Gson gson = new Gson();

    // POST /api (username != null && username.length() >= 10 ? (",\"username\":\"" + username + "\"") : "")
    /**
     * Method building request for creating a new user on deconz. This will only work if you allowed adding a new app in
     * phoscon or via another registered software.
     *
     * @param req        the base request to extend with missing components
     * @param deviceType name that shows up in whitelist. Mainly used by deCONZ to determine HueApps connecting to
     *                       deconz.
     * @param username   optional name to replace random apikey. If null or length < 10 you will receive a random api
     *                       key.
     * @return extended request to use for execution
     */
    public static Request createUser(Request req, String deviceType, @Nullable String username) {
        return req.path(Web.createPath(null, null, null, null)).method(HttpMethod.POST)
                .content(new StringContentProvider(gson.toJson("{\"devicetype\":\"" + deviceType + "\" "
                        + (username != null && username.length() >= 10 ? (",\"username\":\"" + username + "\"") : "")
                        + "}")), "application/json");
    }

    // GET /api/challenge
    public static Request getChallenge(Request req) {
        return req.path(Web.createPath(null, null, null, "challenge")).method(HttpMethod.GET);
    }

    // GET /api/config
    public static Request getBasicConfig(Request req) {
        return req.path(Web.createPath(null, API, null, null)).method(HttpMethod.GET);
    }

    // DELETE /api/config/password
    public static Request deletePassword(Request req) {
        return req.path(Web.createPath(null, API, null, "password")).method(HttpMethod.DELETE);
    }

    // GET /api/<apikey>/config
    public static Request getConfig(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.GET);
    }

    // GET api/<apikey>/config/wifi
    public static Request getWifiState(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "wifi")).method(HttpMethod.GET);
    }

    // PUT /api/<apikey>/config/wifi
    public static Request configureWifi(Request req, String apikey, BeeDeviceConfig config) {
        return req.path(Web.createPath(apikey, API, null, "wifi")).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(config)), "application/json");
    }

    // PUT /api/<apikey>/config/wifi/restore
    public static Request restoreWifiConfig(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "wifi/restore")).method(HttpMethod.PUT);
    }

    // PUT /api/<apikey>/config/homebridge/reset
    public static Request resetHomebridge(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "homebridge/reset")).method(HttpMethod.PUT);
    }

    // PUT, PATCH /api/<apikey>/config
    public static Request modifyConfig(Request req, String apikey, BeeDeviceConfig config) {
        return req.path(Web.createPath(apikey, API, null, null)).method(HttpMethod.PUT)
                .content(new StringContentProvider(gson.toJson(config)), "application/json");

    }

    // DELETE /api/<apikey>/config/whitelist/<username2>
    public static Request deleteUser(Request req, String apikey, String user) {
        return req.path(Web.createPath(apikey, API, null, "whitelist/" + user)).method(HttpMethod.DELETE);
    }

    // POST /api/<apikey>/config/update
    public static Request updateSoftware(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "update")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/restart
    public static Request restartGateway(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "restart")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/restartapp
    public static Request restartApp(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "restartapp")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/shutdown
    public static Request shutdownGateway(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "shutdown")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/updatefirmware
    public static Request updateFirmware(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "updatefirmware")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/export
    public static Request exportConfig(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "export")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/import
    public static Request importConfig(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "import")).method(HttpMethod.POST);
    }

    // POST /api/<apikey>/config/reset
    public static Request resetConfig(Request req, String apikey, BeeDeviceConfig config) throws MissingParamException {
        if (config.resetGW == null || config.deleteDB == null) {
            throw new MissingParamException();
        }
        return req.path(Web.createPath(apikey, API, null, "reset")).method(HttpMethod.POST)
                .content(new StringContentProvider(gson.toJson(config)), "application/json");
    }

    // POST /api/<apikey>/config/wifiscan
    public static Request scanWifiNetworks(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "wifiscan")).method(HttpMethod.POST);
    }

    // PUT /api/<apikey>/config/password
    public static Request changePassword(Request req, String apikey) {
        return req.path(Web.createPath(apikey, API, null, "password")).method(HttpMethod.PUT);
    }
}
