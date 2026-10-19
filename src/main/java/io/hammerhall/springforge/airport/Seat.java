package io.hammerhall.springforge.airport;

import java.util.Objects;

/**
 * Место в салоне.
 *
 * @param number номер места: {@code 1A}
 * @param fare   класс обслуживания
 */
public record Seat(String number, Fare fare) {

    public Seat {
        Objects.requireNonNull(number, "number");
        Objects.requireNonNull(fare, "fare");
    }

    /** {@code 1A · Бизнес}. */
    @Override
    public String toString() {
        return number + " · " + fare;
    }
}
