package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public class Error {
    private InnerError error = new InnerError();

    public static class InnerError {
        public int type = -1;
        public String address = "";
        public String description = "";
    }

    public int getType() {
        return error.type;
    }

    public String getAdress() {
        return error.address;
    }

    public String getDescription() {
        return error.description;
    }

}
