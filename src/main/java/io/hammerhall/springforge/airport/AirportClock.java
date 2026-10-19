package io.hammerhall.springforge.airport;

import java.time.Instant;

/**
 * Часы авиакомпании — одно время на всех; местное время аэропорта — по его поясу.
 * Во многих задачах их даёт окружение: проверка — с заданным временем.
 */
public interface AirportClock {

    Instant now();
}
