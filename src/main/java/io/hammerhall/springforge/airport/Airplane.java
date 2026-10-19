package io.hammerhall.springforge.airport;

import java.util.Objects;

/**
 * Модель самолёта во флоте — строка демобазы.
 *
 * @param code      код модели IATA: {@code 32N}
 * @param model     модель: «Аэробус A320neo»
 * @param rangeKm   наибольшая дальность полёта, км
 * @param speedKmh  крейсерская скорость, км/ч
 * @param inService в работе ли: грузовые модели демобазы пассажиров не возят и в работе нет
 */
public record Airplane(String code, String model, int rangeKm, int speedKmh, boolean inService) {

    public Airplane {
        Objects.requireNonNull(code, "code");
        Objects.requireNonNull(model, "model");
        if (rangeKm <= 0 || speedKmh <= 0) {
            throw new IllegalArgumentException("дальность и скорость — больше нуля: " + code);
        }
    }

    /** Строка флота: {@code 32N · Аэробус A320neo}. */
    @Override
    public String toString() {
        return code + " · " + model;
    }
}
