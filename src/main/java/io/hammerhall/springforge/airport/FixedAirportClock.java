package io.hammerhall.springforge.airport;

import java.time.Instant;
import java.util.Objects;

/**
 * Часы, которые всегда показывают одно время.
 */
public final class FixedAirportClock implements AirportClock {

    private final Instant instant;

    public FixedAirportClock(Instant instant) {
        this.instant = Objects.requireNonNull(instant, "instant");
    }

    @Override
    public Instant now() {
        return instant;
    }
}
