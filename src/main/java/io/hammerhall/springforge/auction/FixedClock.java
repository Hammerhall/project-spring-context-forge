package io.hammerhall.springforge.auction;

import java.time.Instant;
import java.util.Objects;

/**
 * Часы, которые всегда показывают одно время.
 */
public final class FixedClock implements AuctionClock {

    private final Instant instant;

    public FixedClock(Instant instant) {
        this.instant = Objects.requireNonNull(instant, "instant");
    }

    @Override
    public Instant now() {
        return instant;
    }
}
