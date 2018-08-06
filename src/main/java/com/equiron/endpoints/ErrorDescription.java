package com.equiron.endpoints;

import java.util.Date;

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
    private final Date moment = new Date();

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
     * @return a moment, the error was captured at.
     */
    public Date getMoment() {
        return moment;
    }
}
