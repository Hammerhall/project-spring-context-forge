package io.hammerhall.springforge.airport;

import java.util.List;
import java.util.Objects;

/**
 * Схема салона модели: места в порядке демобазы — по рядам, в ряду по буквам.
 *
 * <p>У грузовых моделей мест нет: схема пустая.
 *
 * @param airplaneCode код модели: {@code 32N}
 * @param seats        места, по порядку
 */
public record SeatMap(String airplaneCode, List<Seat> seats) {

    public SeatMap {
        Objects.requireNonNull(airplaneCode, "airplaneCode");
        seats = List.copyOf(seats);
    }

    /** Сколько мест этого класса. */
    public long count(Fare fare) {
        return seats.stream().filter(s -> s.fare() == fare).count();
    }

    /** Пустая ли схема — у грузовых моделей. */
    public boolean isEmpty() {
        return seats.isEmpty();
    }
}
