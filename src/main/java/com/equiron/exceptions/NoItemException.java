package com.equiron.exceptions;

/**
 * Exception thrown when nothing found for single item request.
 *
 * @author mg
 */
public class NoItemException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3161414535729544804L;

    /**
     * The exception constructor.
     *
     * @param itemKey an item key.
     */
    public NoItemException(final String itemKey) {
        super(String.format("Item '%s' not found", itemKey));
    }

}
