package io.hammerhall.springforge.airport;

import java.time.ZoneId;
import java.util.Objects;

/**
 * Аэропорт — строка справочника демобазы.
 *
 * @param code        код IATA: {@code SVO}
 * @param name        название: «Шереметьево»
 * @param city        город, который аэропорт обслуживает: «Москва». У одного города
 *                    бывает несколько аэропортов — у Москвы их три
 * @param countryCode код страны: {@code RU}
 * @param country     страна: «Россия»
 * @param longitude   долгота, градусы
 * @param latitude    широта, градусы
 * @param zone        часовой пояс аэропорта: {@code Europe/Moscow}
 */
public record Airport(String code, String name, String city, String countryCode, String country,
                      double longitude, double latitude, ZoneId zone) {

    public Airport {
        Objects.requireNonNull(code, "code");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(city, "city");
        Objects.requireNonNull(countryCode, "countryCode");
        Objects.requireNonNull(country, "country");
        Objects.requireNonNull(zone, "zone");
    }

    /** Строка справочника: {@code SVO · Шереметьево, Москва}; {@code KZN · Казань}. */
    @Override
    public String toString() {
        return code + " · " + (name.equals(city) ? name : name + ", " + city);
    }
}
