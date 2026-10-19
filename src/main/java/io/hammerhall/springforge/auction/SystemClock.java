package io.hammerhall.springforge.auction;

import java.time.Instant;

/**
 * Настоящие часы.
 */
public final class SystemClock implements AuctionClock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
