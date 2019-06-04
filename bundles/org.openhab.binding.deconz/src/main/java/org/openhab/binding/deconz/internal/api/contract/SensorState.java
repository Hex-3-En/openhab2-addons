/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.api.contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.LoggerFactory;

/**
 * The {@link SensorState} is send by the websocket connection as well as the Rest API.
 * It is part of a {@link Sensor}.
 *
 * This should be in sync with the supported sensors from
 * https://github.com/dresden-elektronik/deconz-rest-plugin/wiki/Supported-Devices.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public interface SensorState {
    static SimpleDateFormat deSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public @Nullable Boolean alarm();

    public @Nullable Integer buttonevent();

    public @Nullable Boolean carbonmonoxide();

    public @Nullable Integer consumption();

    public @Nullable Integer current();

    public @Nullable Boolean dark();

    public @Nullable Boolean daylight();

    public @Nullable Boolean fire();

    public @Nullable Boolean flag();

    public @Nullable Float humidity();

    public @Nullable String lastupdated();

    public @Nullable Integer lightlevel();

    public @Nullable Boolean lowbattery();

    public @Nullable Integer lux();

    public @Nullable Boolean open();

    public @Nullable Integer power();

    public @Nullable Boolean presence();

    public @Nullable Integer pressure();

    public @Nullable Integer status();

    public @Nullable Boolean tampered();

    public @Nullable Integer temperature();

    public @Nullable Boolean vibration();

    public @Nullable Integer voltage();

    public @Nullable Boolean water();

    public default @Nullable Date lastUpdatedAsDate() {
        Date lastUpdated;
        try {
            lastUpdated = deSDF.parse(this.lastupdated());
        } catch (ParseException e) {
            LoggerFactory.getLogger(SensorState.class).trace(e.toString());
            lastUpdated = null;
        }
        return lastUpdated;
    }
}
