package com.equiron.exceptions;

/**
 * Exception thrown when a missing value detected.
 *
 * @author mg
 */
public class ValueMissingException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 2463334535729770804L;

    /**
     * The exception constructor.
     *
     * @param valuePath path of the value in a document.
     */
    public ValueMissingException(final String valuePath) {
        super(String.format("Missing a value at (%s)", valuePath));
    }

}
