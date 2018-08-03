package com.equiron.exceptions;

/**
 * Exception thrown when a value in a bad format detected.
 *
 * @author mg
 */
public class BadValueFormatException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 4213334535729220804L;

    /**
     * The exception constructor.
     *
     * @param valuePath path of the value in a document.
     * @param format    ethalon format hint.
     */
    public BadValueFormatException(final String valuePath,
                                   final String format) {
        super(String.format("Bad value format detected at (%s). It should"
                + " be formatted as: %s", valuePath, format));
    }

}
