package org.openhab.binding.deconz.internal.exception;

public class NotYetImplementedException extends Exception {
    private static final long serialVersionUID = -7059526506861252241L;

    public NotYetImplementedException() {
        super("The method you have called is temporarily not available... ");
    }

}
