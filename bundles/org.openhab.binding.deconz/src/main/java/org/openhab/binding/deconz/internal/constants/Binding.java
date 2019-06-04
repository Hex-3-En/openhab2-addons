/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.deconz.internal.constants;

/**
 * The {@link Binding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Patrick Willnow - Initial contribution
 */
public class Binding {

    public static final String ID = "deconz";

    // General texts
    public static final String EXCEPTION_VALUE_NOT_PRESENT = "Method called where it schould NOT be done, as NOT meeting api specs!";

    public static final String NOTIFICATION_UNLOCK_BRIDGE = "Please unlock the bridge via e.g. Phoscon! You can press the scan button in openhab to trigger a retry.";

}
