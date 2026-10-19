package io.hammerhall.springforge.airport;

import java.time.Instant;
import java.util.Objects;

/**
 * Запись журнала смены: когда и что.
 *
 * @param at   время по часам авиакомпании
 * @param text что случилось: «справочник открыт», «место 1A выдано»
 */
public record LogEntry(Instant at, String text) {

    public LogEntry {
        Objects.requireNonNull(at, "at");
        Objects.requireNonNull(text, "text");
    }
}
