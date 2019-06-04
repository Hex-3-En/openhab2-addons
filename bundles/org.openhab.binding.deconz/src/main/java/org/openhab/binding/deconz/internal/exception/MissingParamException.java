package org.openhab.binding.deconz.internal.exception;

public class MissingParamException extends Exception {
    private static final long serialVersionUID = -7059526506861252241L;

    public MissingParamException() {
        super("You missed handing over required NonNull parameters. Lookup JavaDoc for more information.");
    }

}
