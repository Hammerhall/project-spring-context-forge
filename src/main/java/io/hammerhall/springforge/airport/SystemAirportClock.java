package io.hammerhall.springforge.airport;

import java.time.Instant;

/**
 * Настоящие часы.
 */
public final class SystemAirportClock implements AirportClock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
