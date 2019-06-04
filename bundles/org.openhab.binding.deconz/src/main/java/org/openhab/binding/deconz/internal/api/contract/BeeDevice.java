/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.api.contract;

import java.util.Collections;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * http://dresden-elektronik.github.io/deconz-rest-doc/configuration/
 * # Get full state
 * GET /api/<apikey>
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public class BeeDevice {
    public BeeDeviceConfig config = new BeeDeviceConfig();

    public Map<String, Sensor> sensors = Collections.emptyMap();
    public Map<String, Light> lights = Collections.emptyMap();
    public Map<String, Group> groups = Collections.emptyMap();
    // public Map<String, Sensor> schedules = Collections.emptyMap();
    // public Map<String, Sensor> rules = Collections.emptyMap();
}
