package com.equiron.model;

import com.equiron.store.Counterparties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

/**
 * Domain class for sell / buy deals.
 * Immutable.
 */
public final class Deal {

    /**
     * Seller field.
     */
    @NotNull
    @Valid
    private final Counterparty seller;

    /**
     * Customer field.
     */
    @NotNull
    @Valid
    private final Counterparty customer;

    /**
     * Products collection field.
     */
    @NotNull
    @NotEmpty
    @Valid
    private final Collection<Product> products;

    /**
     * @param aSeller   {@link Counterparty} instance to set as
     *                  aSeller in the deal.
     * @param aCustomer {@link Counterparty} instance to set as
     *                  aCustomer in the deal.
     * @param aProducts {@link Collection<Product>} instance to set as
     *                  aProducts of the deal.
     */
    public Deal(final Counterparty aSeller, final Counterparty aCustomer,
                final Collection<Product> aProducts) {
        seller = aSeller;
        customer = aCustomer;
        products = aProducts;
    }

    /**
     * {@link #seller} getter.
     *
     * @return {@link Counterparty} instance used as seller in the deal.
     */
    public Counterparty getSeller() {
        return seller;
    }

    /**
     * {@link #customer} getter.
     *
     * @return {@link Counterparty} instance used as customer in the deal.
     */
    public Counterparty getCustomer() {
        return customer;
    }

    /**
     * Returns products collection of the deal.
     *
     * @return {@link Collection<Product>} instance with products of the deal.
     */
    public Collection<Product> getProducts() {
        return products;
    }

    /**
     * Factory method used while Json deserialization. Resolves known
     * counterparties.
     *
     * @param sellerKey   key of known seller.
     * @param customerKey key of known customer.
     * @param products    products collection of the deal.
     * @return {@link Deal} instance with resolved counterparties.
     */
    @JsonCreator
    public static Deal of(
            @JsonProperty(value = "seller") final
            String sellerKey,
            @JsonProperty(value = "customer") final
            String customerKey,
            @JsonProperty(value = "products") final
            Collection<Product> products
    ) {
        Counterparty seller = Counterparties.find(sellerKey)
                .orElse(new Counterparty(sellerKey, "Unknown seller "
                        + sellerKey));
        Counterparty customer = Counterparties.find(customerKey)
                .orElse(new Counterparty(customerKey, "Unknown customer "
                        + customerKey));
        if (!Objects.equals(seller.getKey(), customer.getKey())) {
            return new Deal(seller, customer, products);
        } else {
            throw new IllegalStateException("Same seller / customer detected ("
                    + sellerKey
                    + ")");
        }
    }

}
