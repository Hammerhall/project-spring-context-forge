package io.hammerhall.springforge.airport;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Справочная поясов: который час в аэропорту — по часам авиакомпании и поясу аэропорта из того
 * справочника, который ей дали.
 */
public final class ZoneDesk {

    private final AirportDirectory directory;
    private final AirportClock clock;

    public ZoneDesk(AirportDirectory directory, AirportClock clock) {
        this.directory = Objects.requireNonNull(directory, "directory");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    /** По какому справочнику отвечает справочная. */
    public AirportDirectory directory() {
        return directory;
    }

    /** По каким часам. */
    public AirportClock clock() {
        return clock;
    }

    /**
     * Местное время аэропорта сейчас, до минут: в 06:00 UTC в Катманду ({@code KTM}) — 11:45.
     * Кода нет в справочнике — {@link IllegalArgumentException}.
     */
    public LocalTime localTime(String code) {
        return clock.now().atZone(directory.byCode(code).zone()).toLocalTime().truncatedTo(ChronoUnit.MINUTES);
    }

    /** Ответ справочной: {@code Катманду — 11:45}. */
    public String answer(String code) {
        return directory.byCode(code).city() + " — " + localTime(code);
    }
}
