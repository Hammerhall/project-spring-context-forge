package io.hammerhall.springforge.auction;

import java.time.Instant;
import java.util.Objects;

/**
 * Аукционный дом. Открывается при создании — по своим часам.
 */
public final class AuctionHouse {

    private final LotCatalog catalog;
    private final Instant openedAt;

    public AuctionHouse(LotCatalog catalog, AuctionClock clock) {
        this.catalog = Objects.requireNonNull(catalog, "catalog");
        this.openedAt = Objects.requireNonNull(clock, "clock").now();
    }

    public LotCatalog catalog() {
        return catalog;
    }

    /** Когда дом открылся. */
    public Instant openedAt() {
        return openedAt;
    }
}
