package io.hammerhall.springforge.airport;

import java.util.List;

/**
 * Флот: какие модели самолётов есть у авиакомпании.
 */
public interface Fleet {

    /** Модели в порядке флота. */
    List<Airplane> airplanes();

    /** Модель по коду; нет такой — {@link IllegalArgumentException}. */
    default Airplane byCode(String code) {
        return airplanes().stream()
                .filter(a -> a.code().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("во флоте нет модели " + code));
    }
}
