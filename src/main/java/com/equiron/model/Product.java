package com.equiron.model;

import com.equiron.exceptions.BadValueFormatException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Domain class for product.
 * Immutable.
 */
public class Product {

    /**
     * Product key validation rule constant.
     */
    public static final int PRODUCT_KEY_LENGTH = 13;
    /**
     * Key field.
     */
    private final String code;
    /**
     * Human readable name field.
     */
    private final String name;

    /**
     * Constructor of immutable {@link Product} instance.
     *
     * @param aCode key value.
     * @param aName Product's aName.
     */
    public Product(final String aCode, final String aName) {
        Objects.requireNonNull(aCode, "'aCode' is required");
        Objects.requireNonNull(aName, "'aName' is required");
        this.code = aCode;
        this.name = aName;
    }

    /**
     * {@link #code} field getter.
     *
     * @return Key of the product
     */
    public final String getCode() {
        return code;
    }

    /**
     * {@link #name} field getter.
     *
     * @return Name of the product
     */
    public final String getName() {
        return name;
    }

    /**
     * Product factory method. Validates {@link Product}'s fields.
     * @param code value for {@link Product#code} field
     * @param name value for {@link Product#name} field
     * @return created {@link Product#code} instance.
     * @throws BadValueFormatException if validation fails.
     */
    @JsonCreator
    public static Product of(
            @JsonProperty(value = "code", required = true) final String code,
            @JsonProperty(value = "name", required = true) final String name
    ) throws BadValueFormatException {
        if (code.length() == PRODUCT_KEY_LENGTH) {
            return new Product(code, name);
        } else {
            throw new BadValueFormatException("product[].code",
                    "'product[].code' field should be "
                            + PRODUCT_KEY_LENGTH + " characters length");
        }
    }
}
