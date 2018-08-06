package com.equiron.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Domain class for product.
 * Immutable.
 */
public final class Product {

    /**
     * Product key validation rule constant.
     */
    private static final int KEY_LENGTH = 13;
    /**
     * Key field.
     */
    @NotBlank
    @Size(min = KEY_LENGTH, max = KEY_LENGTH)
    private final String code;
    /**
     * Human readable name field.
     */
    @NotBlank
    private final String name;

    /**
     * Constructor of immutable {@link Product} instance.
     *
     * @param aCode key value.
     * @param aName Product's aName.
     */
    public Product(final String aCode, final String aName) {
        code = aCode;
        name = aName;
    }

    /**
     * {@link #code} field getter.
     *
     * @return Key of the product
     */
    public String getCode() {
        return code;
    }

    /**
     * {@link #name} field getter.
     *
     * @return Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Product factory method. Validates {@link Product}'s fields.
     *
     * @param code value for {@link Product#code} field
     * @param name value for {@link Product#name} field
     * @return created {@link Product#code} instance.
     */
    @JsonCreator
    public static Product of(
            @JsonProperty(value = "code") final String code,
            @JsonProperty(value = "name") final String name
    ) {
        return new Product(code, name);
    }
}
