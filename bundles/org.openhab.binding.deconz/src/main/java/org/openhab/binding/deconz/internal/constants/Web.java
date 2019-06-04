package org.openhab.binding.deconz.internal.constants;

import java.lang.reflect.Type;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.deconz.internal.api.contract.Message;

import com.google.gson.reflect.TypeToken;

@NonNullByDefault
public class Web {
    public static final String HEADER_ETAG = "If-None-Match";

    public static final String DEFAULT_DEVICE_TYPE = "OpenHab-deCONZ-Binding";

    public static final int REST_TIMEOUT_MILLIS = 2000;
    public static final int REST_POLLING_REGULAR_MILLIS = 5000;
    public static final int REST_POLLING_QUICK_MILLIS = 500;
    /**
     * Cause 31536000315360 milliseconds means exactly 1000 years so we can tell jetty to go f* off and keep the
     * websocket running!
     */
    public static final long WEBSOCKET_TIMEOUT_MILLIS = 31536000315360l;

    public static Type MESSAGE_MAP_TYPE = new TypeToken<Map<String, Message>>() {
    }.getType();

    public static String url(String host, @Nullable String apikey, @Nullable String api, @Nullable String id,
            @Nullable String detail) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://");
        urlBuilder.append(host);
        urlBuilder.append("/api");
        if (apikey != null) {
            urlBuilder.append("/");
            urlBuilder.append(apikey);
        }
        if (api != null) {
            urlBuilder.append("/");
            urlBuilder.append(api);
        }
        if (id != null) {
            urlBuilder.append("/");
            urlBuilder.append(id);
        }
        if (detail != null) {
            urlBuilder.append("/");
            urlBuilder.append(detail);
        }
        return urlBuilder.toString();
    }

    public static String createPath(@Nullable String apikey, @Nullable String api, @Nullable String id,
            @Nullable String detail) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/api");
        if (apikey != null) {
            pathBuilder.append("/");
            pathBuilder.append(apikey);
        }
        if (api != null) {
            pathBuilder.append("/");
            pathBuilder.append(api);
        }
        if (id != null) {
            pathBuilder.append("/");
            pathBuilder.append(id);
        }
        if (detail != null) {
            pathBuilder.append("/");
            pathBuilder.append(detail);
        }
        return pathBuilder.toString();
    }
}
