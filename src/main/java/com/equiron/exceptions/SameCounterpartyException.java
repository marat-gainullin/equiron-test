package com.equiron.exceptions;

/**
 * Exception thrown when a deal has thee same seller and customer.
 *
 * @author mg
 */
public class SameCounterpartyException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 5561114535729550804L;

    /**
     * The exception constructor.
     *
     * @param counterpartyKey a counterpartyKey key.
     */
    public SameCounterpartyException(final String counterpartyKey) {
        super(String.format("A counterpartyKey (%s) can't sell"
                + " to himself", counterpartyKey));
    }

}
