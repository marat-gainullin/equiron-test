package com.equiron.model;

import com.equiron.exceptions.BadValueFormatException;
import com.equiron.exceptions.NoItemException;
import com.equiron.exceptions.SameCounterpartyException;
import com.equiron.store.Counterparties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Objects;

/**
 * Domain class for sell / buy deals.
 * Immutable.
 */
public class Deal {

    /**
     * Format validation constant.
     */
    private static final int COUNTERPARTY_KEY_LENGTH = 9;
    /**
     * Seller field.
     */
    private final Counterparty seller;

    /**
     * Customer field.
     */
    private final Counterparty customer;

    /**
     * Products collection field.
     */
    private final Collection<Product> products;

    /**
     * @param aSeller   {@link Counterparty} instance to set as
     *                 aSeller in the deal.
     * @param aCustomer {@link Counterparty} instance to set as
     *                 aCustomer in the deal.
     * @param aProducts {@link Collection<Product>} instance to set as
     *                 aProducts of the deal.
     */
    public Deal(final Counterparty aSeller, final Counterparty aCustomer,
                final Collection<Product> aProducts) {
        Objects.requireNonNull(aSeller, "aSeller is required");
        Objects.requireNonNull(aCustomer, "aCustomer is required");
        Objects.requireNonNull(aProducts, "aProducts is required,"
                + " even if it is empty");
        this.seller = aSeller;
        this.customer = aCustomer;
        this.products = aProducts;
    }

    /**
     * {@link #seller} getter.
     *
     * @return {@link Counterparty} instance used as seller in the deal.
     */
    public final Counterparty getSeller() {
        return seller;
    }

    /**
     * {@link #customer} getter.
     *
     * @return {@link Counterparty} instance used as customer in the deal.
     */
    public final Counterparty getCustomer() {
        return customer;
    }

    /**
     * Returns products collection of the deal.
     *
     * @return {@link Collection<Product>} instance with products of the deal.
     */
    public final Collection<Product> getProducts() {
        return products;
    }

    /**
     * Factory method used while Json deserialization. Resolves known
     * counterparties and checks format of keys.
     *
     * @param sellerKey   key of known seller.
     * @param customerKey key of known customer.
     * @param products    products collection of the deal.
     * @return {@link Deal} instance with resolved counterparties.
     * @throws NoItemException           if no counterparty with given
     *                                   key found.
     * @throws BadValueFormatException   if key format violation detected
     * @throws SameCounterpartyException if customer and seller
     *                                   counterparties are the same.
     */
    @JsonCreator
    public static Deal of(
            @JsonProperty(value = "seller", required = true) final
            String sellerKey,
            @JsonProperty(value = "customer", required = true) final
            String customerKey,
            @JsonProperty(value = "products", required = true) final
            Collection<Product> products
    ) throws NoItemException,
            BadValueFormatException,
            SameCounterpartyException {
        if (sellerKey.length() == COUNTERPARTY_KEY_LENGTH) {
            if (customerKey.length() == COUNTERPARTY_KEY_LENGTH) {
                Counterparty seller = Counterparties.find(sellerKey)
                        .orElseThrow(() -> new NoItemException(sellerKey));
                Counterparty customer = Counterparties.find(customerKey)
                        .orElseThrow(() -> new NoItemException(customerKey));
                if (seller != customer) {
                    return new Deal(seller, customer, products);
                } else {
                    throw new SameCounterpartyException(sellerKey);
                }
            } else {
                throw new BadValueFormatException("customer",
                        "'customer' field "
                                + "should be " + COUNTERPARTY_KEY_LENGTH
                                + " characters length");
            }
        } else {
            throw new BadValueFormatException("seller", "'seller' field "
                    + "should be " + COUNTERPARTY_KEY_LENGTH
                    + " characters length");
        }
    }

}
