package org.openhab.binding.deconz.internal.api;

import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.openhab.binding.deconz.internal.api.contract.ApiKey;
import org.openhab.binding.deconz.internal.api.contract.Error;
import org.openhab.binding.deconz.internal.api.rest.ConfigurationAPI;
import org.openhab.binding.deconz.internal.api.rest.LightAPI;
import org.openhab.binding.deconz.internal.api.rest.SensorAPI;
import org.openhab.binding.deconz.internal.constants.Binding;
import org.openhab.binding.deconz.internal.constants.Web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@NonNullByDefault
public class DeconzRestClient {
    private final Logger logger = LoggerFactory.getLogger(DeconzRestClient.class);
    private final Gson gson = new Gson();
    private HttpClient client;
    private DeconzRestClientListener bridge;
    private final List<CompletableFuture<?>> pendingFutures;
    private final Map<String, String> sensorTags = Collections.emptyMap();
    private final Map<String, String> lightTags = Collections.emptyMap();
    private @Nullable String configTag;
    private @Nullable String allSensorsTag;
    private @Nullable String allLightsTag;

    /**
     * Having NonNull values here, makes things easier.
     * As having openhab and deCONZ running on one device seems pretty common,
     * localhost and most common port 80 are chosen as defaults.
     */
    private String host = "localhost";

    /**
     * see host
     */
    private Integer port = 80;
    private @Nullable String apikey;

    public DeconzRestClient(HttpClient client, DeconzRestClientListener bridge) {
        this.client = client;
        this.bridge = bridge;
        this.pendingFutures = new LinkedList<>();
    }

    public void initialize(String host, int port, @Nullable String apikey) {
        this.apikey = apikey;
        this.host = host;
        this.port = port;
        initialize();
    }

    private void initialize() {
        if (apikey == null) {
            authMissing();
        }
    }

    private void stopPendingFutures() {
        for (CompletableFuture<?> future : pendingFutures) {
            future.cancel(true);
        }
        pendingFutures.clear();
    }

    public void pollRegular() {
        CompletableFuture<?> f = execute(LightAPI.getAllLights(newAllLightsRequest(), apikey)).thenAccept(result -> {
            if (Status.NOT_MODIFIED.equals(result.getResponseCode())) {
                return;
            } else if (Status.FORBIDDEN.equals(result.getResponseCode())) {
                authMissing();
            } else {
                bridge.updateLights(gson.fromJson(result.body, Web.MESSAGE_MAP_TYPE));

            }
        });
        pendingFutures.add(f);
        f.whenComplete((value, error) -> {
            if (value != null) {

            }
            pendingFutures.remove(f);
        });
    }

    public void pollSensors() {
        CompletableFuture<?> f = execute(SensorAPI.getAllSensors(newAllSensorsRequest(), apikey)).thenAccept(result -> {
            if (Status.NOT_MODIFIED.equals(result.getResponseCode())) {
                return;
            } else if (Status.FORBIDDEN.equals(result.getResponseCode())) {
                authMissing();
            } else {
                bridge.updateSensors(gson.fromJson(result.body, Web.MESSAGE_MAP_TYPE));
            }
        });
        pendingFutures.add(f);
        f.whenComplete((value, error) -> {
            if (value != null) {

            }
            pendingFutures.remove(f);
        });
    }

    private void authMissing() {
        CompletableFuture<?> f = requestApiKey();
        f.whenComplete((value, error) -> {
            if (value != null) {

            }
            pendingFutures.remove(f);
        });
    }

    public void dispose() {
        stopPendingFutures();
    }

    public final Request newSensorRequest(String id) {
        Request req = client.newRequest(host, port);
        return sensorTags.containsKey(id) ? req.header(Web.HEADER_ETAG, sensorTags.get(id)) : req;
    }

    public final Request newAllSensorsRequest() {
        Request req = client.newRequest(host, port);
        return allSensorsTag != null ? req.header(Web.HEADER_ETAG, allSensorsTag) : req;
    }

    public final Request newLightRequest(String id) {
        Request req = client.newRequest(host, port);
        return lightTags.containsKey(id) ? req.header(Web.HEADER_ETAG, lightTags.get(id)) : req;
    }

    public final Request newAllLightsRequest() {
        Request req = client.newRequest(host, port);
        return allLightsTag != null ? req.header(Web.HEADER_ETAG, allLightsTag) : req;
    }

    public final Request newConfigRequest() {
        Request req = client.newRequest(host, port);
        return configTag != null ? req.header(Web.HEADER_ETAG, configTag) : req;
    }

    public CompletableFuture<Result> execute(Request request) {
        final CompletableFuture<Result> f = new CompletableFuture<Result>();
        request.timeout(Web.REST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).send(new BufferingResponseListener() {
            @NonNullByDefault({})
            @Override
            public void onComplete(org.eclipse.jetty.client.api.Result result) {
                final HttpResponse response = (HttpResponse) result.getResponse();
                if (result.isFailed()) {
                    f.completeExceptionally(result.getFailure());
                    return;
                }
                f.complete(new Result(
                        response.getHeaders().stream().collect(Collectors.toMap(h -> h.getName(), h -> h.getValue())),
                        getContentAsString(), response.getStatus()));
            }
        });
        return f;
    }

    public static class Result {
        private final Map<String, String> headers;
        private final String body;
        private final int responseCode;

        public Result(Map<String, String> headers, String body, int responseCode) {
            this.headers = headers;
            this.body = body;
            this.responseCode = responseCode;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getBody() {
            return body;
        }

        public int getResponseCode() {
            return responseCode;
        }
    }

    /**
     * Perform a request to the REST API for generating an API key.
     *
     * @param r The response
     * @return
     */
    private CompletableFuture<String> requestApiKey() {
        bridge.setStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_PENDING, "Requesting API Key");
        CompletableFuture<String> f = execute(
                ConfigurationAPI.createUser(newConfigRequest(), Web.DEFAULT_DEVICE_TYPE, null)).thenApply(result -> {
                    if (Status.FORBIDDEN.equals(result.getResponseCode())) {
                        bridge.notifyUser(Binding.NOTIFICATION_UNLOCK_BRIDGE);
                        logger.warn(Binding.NOTIFICATION_UNLOCK_BRIDGE);
                        bridge.setStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                                Binding.NOTIFICATION_UNLOCK_BRIDGE);
                        return null;
                    } else if (Status.BAD_REQUEST.equals(result.getResponseCode())) {
                        logger.error(gson.fromJson(result.body, Error[].class)[0].getDescription());
                        bridge.setStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                                "Someone can not implement requests properly.");
                        return null;
                    } else {
                        String apiKey = gson.fromJson(result.body, ApiKey.class).getSuccess().username;
                        bridge.setApiKey(apiKey);
                        return apiKey;
                    }
                }).exceptionally(e -> {
                    bridge.setStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
                    logger.warn("Authorisation failed", e);
                    return null;
                });
        pendingFutures.add(f);
        return f;
    }

    /**
     * Perform a request to the REST API for retrieving the full bridge state with all sensors and switches
     * and configuration.
     */
    public void requestFullState() {
        if (config.apikey == null) {
            return;
        }
        String url = Binding.url(config.host, config.apikey, null, null);

        http.get(url, config.timeout).thenApply(this::parseBridgeFullStateResponse).exceptionally(e -> {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            if (!(e instanceof SocketTimeoutException)) {
                logger.warn("Get full state failed", e);
            }
            return null;
        }).whenComplete((value, error) -> {
            if (thingDiscoveryService != null) {
                // Hand over sensors to discovery service
                thingDiscoveryService.stateRequestFinished(value != null ? value.sensors : null);
            }
        }).thenAccept(fullState -> {
            // Auth failed
            if (fullState == null) {
                requestApiKey();
                return;
            }
            if (fullState.config.name.isEmpty()) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE,
                        "You are connected to a HUE bridge, not a deCONZ software!");
                return;
            }
            if (fullState.config.websocketport == 0) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE,
                        "deCONZ software too old. No websocket support!");
                return;
            }

            // Add some information about the bridge
            Map<String, String> editProperties = editProperties();
            editProperties.put("apiversion", fullState.config.apiversion);
            editProperties.put("swversion", fullState.config.swversion);
            editProperties.put("fwversion", fullState.config.fwversion);
            editProperties.put("uuid", fullState.config.uuid);
            editProperties.put("zigbeechannel", String.valueOf(fullState.config.zigbeechannel));
            editProperties.put("ipaddress", fullState.config.ipaddress);
            ignoreConfigurationUpdate = true;
            updateProperties(editProperties);
            ignoreConfigurationUpdate = false;

            websocketport = fullState.config.websocketport;
            startWebsocket();
        }).exceptionally(e -> {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE, e.getMessage());
            logger.warn("Full state parsing failed", e);
            return null;
        });
    }
}
