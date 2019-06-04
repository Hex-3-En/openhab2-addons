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

/**
 * The success message for an API key request
 *
 * @author David Graeff - Initial contribution
 */
@NonNullByDefault
public class ApiKey {
    private Success success = new Success();

    public Success getSuccess() {
        return this.success;
    }

    public static class Success {
        public String username = "";
    }
}
