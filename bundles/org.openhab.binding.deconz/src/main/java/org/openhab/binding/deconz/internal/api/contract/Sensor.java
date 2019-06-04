/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The REST interface and websocket connection are using the same fields.
 * The REST data contains more descriptive info like the manufacturer and name.
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public interface Sensor extends Device {
    public @Nullable String manufacturername();

    /** the API endpoint **/
    public String ep();

    public @Nullable default SensorConfig sensorConfig() {
        return config();
    }

    public @Nullable default SensorState sensorState() {
        return state();
    }
}
