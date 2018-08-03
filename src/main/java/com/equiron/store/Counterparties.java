package com.equiron.store;

import com.equiron.model.Counterparty;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repository of counterparties.
 */
public final class Counterparties {

    /**
     * Simple in-memory STORE of {@link Counterparty} instances,
     * known to the service.
     * In real scenarios should be looked up from a persistent storage.
     * This map is read-only and so, there is no need to use
     * concurrent version.
     */
    private static final Map<String, Counterparty> STORE = Stream.of(
            new Counterparty("123534251", "Equiron"),
            new Counterparty("648563524", "PTDC")
    )
            .collect(Collectors.toMap(Counterparty::getKey,
                    Function.identity()));

    /**
     * Utility class hidden constructor.
     */
    private Counterparties() {
    }

    /**
     * Checks a {@link Counterparty} existence.
     *
     * @param key a key of counterparty.
     * @return true if a counterparty exists and false otherwise.
     */
    public static boolean exists(final String key) {
        return STORE.containsKey(key);
    }

    /**
     * Looks up a {@link Counterparty} instance by a key.
     *
     * @param key a key of counterparty.
     * @return {@link Counterparty} instance if it is found and
     * null otherwise.
     */
    public static Optional<Counterparty> find(final String key) {
        return Optional.ofNullable(STORE.get(key));
    }
}
