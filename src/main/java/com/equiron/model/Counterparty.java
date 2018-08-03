package com.equiron.model;

import java.util.Objects;

/**
 * Domain class for customers and sellers.
 * Immutable.
 */
public class Counterparty {

    /**
     * Counterparty's key.
     */
    private final String key;

    /**
     * Human readable counterparty's name.
     */
    private final String name;

    /**
     * Constructor of immutable counterparty instance.
     *
     * @param aKey  Counterparty's aKey.
     * @param aName Counterparty's aName.
     */
    public Counterparty(final String aKey, final String aName) {
        Objects.requireNonNull(aKey, "'aKey' is required");
        Objects.requireNonNull(aName, "'aName' is required");
        this.key = aKey;
        this.name = aName;
    }

    /**
     * {@link #key} field getter.
     *
     * @return key value.
     */
    public final String getKey() {
        return key;
    }

    /**
     * {@link #name} field getter.
     *
     * @return Name of the counterparty
     */
    public final String getName() {
        return name;
    }
}
