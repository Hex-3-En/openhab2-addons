/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.api;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpMethod;;

/**
 * An asynchronous API for HTTP interactions.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public enum AsyncHttpClient {
    INSTANCE;
    private @Nullable HttpClient client;

    public AsyncHttpClient withClient(HttpClient client) {
        this.client = client;
        return this;
    }

    /**
     * Perform a POST request
     *
     * @param address    The address
     * @param jsonString The message body
     * @param timeout    A timeout
     * @return The result
     * @throws IOException Any IO exception in an error case.
     */
    public CompletableFuture<Result> post(String address, String jsonString, @Nullable Map<String, String> headers) {
        return doNetwork(HttpMethod.POST, address, jsonString, headers);
    }

    /**
     * Perform a PUT request
     *
     * @param address    The address
     * @param jsonString The message body
     * @param timeout    A timeout
     * @return The result
     * @throws IOException Any IO exception in an error case.
     */
    public CompletableFuture<Result> put(String address, String jsonString, @Nullable Map<String, String> headers) {
        return doNetwork(HttpMethod.PUT, address, jsonString, headers);
    }

    /**
     * Perform a GET request
     *
     * @param address The address
     * @param timeout A timeout
     * @return The result
     * @throws IOException Any IO exception in an error case.
     */
    public CompletableFuture<Result> get(String address, @Nullable Map<String, String> headers) {
        return doNetwork(HttpMethod.GET, address, null, headers);
    }

    /**
     * Perform a DELETE request
     *
     * @param address The address
     * @param timeout A timeout
     * @return The result
     * @throws IOException Any IO exception in an error case.
     */
    public CompletableFuture<Result> delete(String address, @Nullable Map<String, String> headers) {
        return doNetwork(HttpMethod.DELETE, address, null, headers);
    }

}
