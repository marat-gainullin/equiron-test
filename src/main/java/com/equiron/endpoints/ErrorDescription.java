package com.equiron.endpoints;

import java.time.Instant;

/**
 * Exception information DTO.
 */
public final class ErrorDescription {

    /**
     * Http code of an error.
     */
    private final int code;
    /**
     * Description of an error.
     */
    private final String description;
    /**
     * Moment of error generation.
     */
    private final Instant moment = Instant.now();

    /**
     * Error description constructor.
     *
     * @param aCode        Code of an error.
     * @param aDescription Description of an error.
     */
    public ErrorDescription(final int aCode, final String aDescription) {
        code = aCode;
        description = aDescription;
    }

    /**
     * Retuns Description of an error.
     *
     * @return Description of an error.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns Description of an error.
     *
     * @return Description of an error.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a moment, the error was captured at.
     *
     * @return a moment, the error was captured at.
     */
    public Instant getMoment() {
        return moment;
    }
}
