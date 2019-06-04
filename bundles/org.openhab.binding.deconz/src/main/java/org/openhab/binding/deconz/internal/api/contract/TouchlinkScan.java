package org.openhab.binding.deconz.internal.api.contract;

import java.util.Collections;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public class TouchlinkScan {
    public String scanstate = "";
    public String lastscan = "";
    public Map<String, ScanResult> result = Collections.emptyMap();

    public static class ScanResult {
        public boolean factorynew = false;
        public String address = "";
    }
}
