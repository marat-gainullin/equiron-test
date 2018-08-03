package com.equiron.store;

import com.equiron.model.Deal;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository of deals.
 */
@Repository
public class Deals {

    /**
     * Thread safe counter. used for keys generation.
     */
    private final AtomicLong sequence = new AtomicLong();

    /**
     * Simple in memory store of {@link Deal} by {@link Long} key.
     */
    private final Map<Long, Deal> store = new ConcurrentHashMap<>();


    /**
     * Finds a {@link Deal} by key.
     *
     * @param key Key of a deal.
     * @return {@link Optional<Deal>} instance with found {@link Deal} or
     * {@link Optional#empty()} if none found.
     */
    public final Optional<Deal> find(final long key) {
        return Optional.ofNullable(store.get(key));
    }

    /**
     * Adds a deal to storage.
     * @param deal {@link Deal} instance to add.
     * @return generated key for added deal.
     */
    public final long add(final Deal deal) {
        long key = sequence.incrementAndGet();
        store.put(key, deal);
        return key;
    }
}
