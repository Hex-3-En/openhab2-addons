package org.openhab.binding.deconz.internal.api;

import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.openhab.binding.deconz.internal.api.AsyncHttpClient.Result;
import org.openhab.binding.deconz.internal.api.contract.ApiKey;
import org.openhab.binding.deconz.internal.api.contract.BeeDevice;
import org.openhab.binding.deconz.internal.constants.Binding;

import com.google.gson.Gson;

public final class DeconzResponseParser {
    private final Gson gson = new Gson();

    /**
     * Parses the response message to the REST API for retrieving the full bridge state with all sensors and switches
     * and configuration.
     *
     * @param r The response
     */
    @Nullable
    BeeDevice parseBridgeFullStateResponse(AsyncHttpClient.Result r) {
        if (r.getResponseCode() == 403) {
            return null;
        } else if (r.getResponseCode() == 200) {
            return gson.fromJson(r.getBody(), BeeDevice.class);
        } else {
            throw new IllegalStateException("Unknown status code for full state request");
        }
    }

    /**
     * Parses the response message to the API key generation REST API.
     *
     * @param r The response
     */
    private void parseAPIKeyResponse(AsyncHttpClient.Result r) {
        if (r.getResponseCode() == 403) {

            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_PENDING,
                    "Allow authentification for 3rd party apps. Trying again in " + String.valueOf(POLL_FREQUENCY_SEC)
                            + " seconds");
            scheduledFuture = scheduler.schedule(() -> requestApiKey(), POLL_FREQUENCY_SEC, TimeUnit.SECONDS);
        } else if (r.getResponseCode() == 200) {
            ApiKey[] response = gson.fromJson(r.getBody(), ApiKey[].class);
            if (response.length == 0) {
                throw new IllegalStateException("Authorisation request response is empty");
            }
            config.apikey = response[0].success.username;
            Configuration configuration = editConfiguration();
            configuration.put(Binding.CONFIG_APIKEY, config.apikey);
            updateConfiguration(configuration);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_PENDING, "Waiting for configuration");
            requestFullState();
        } else {
            throw new IllegalStateException("Unknown status code for authorisation request");
        }
    }
}
