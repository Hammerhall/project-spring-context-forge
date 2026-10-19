package io.hammerhall.springforge.airport;

import java.util.List;

/**
 * Справочник аэропортов: какие аэропорты знает авиакомпания.
 */
public interface AirportDirectory {

    /** Аэропорты в порядке справочника. */
    List<Airport> airports();

    /** Аэропорт по коду IATA; нет такого — {@link IllegalArgumentException}. */
    default Airport byCode(String code) {
        return airports().stream()
                .filter(a -> a.code().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("в справочнике нет аэропорта " + code));
    }
}
