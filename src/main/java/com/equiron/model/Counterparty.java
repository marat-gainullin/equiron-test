package com.equiron.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Domain class for customers and sellers.
 * Immutable.
 */
public final class Counterparty {

    /**
     * {@link Counterparty#key} length.
     */
    private static final int KEY_LENGTH = 9;

    /**
     * Counterparty's key.
     */
    @NotNull
    @NotBlank
    @Size(min = KEY_LENGTH, max = KEY_LENGTH)
    private final String key;

    /**
     * Human readable counterparty's name.
     */
    @NotBlank
    private final String name;

    /**
     * Constructor of immutable counterparty instance.
     *
     * @param aKey  Counterparty's aKey.
     * @param aName Counterparty's aName.
     */
    public Counterparty(final String aKey, final String aName) {
        key = aKey;
        name = aName;
    }

    /**
     * {@link #key} field getter.
     *
     * @return key value.
     */
    public String getKey() {
        return key;
    }

    /**
     * {@link #name} field getter.
     *
     * @return Name of the counterparty
     */
    public String getName() {
        return name;
    }
}
