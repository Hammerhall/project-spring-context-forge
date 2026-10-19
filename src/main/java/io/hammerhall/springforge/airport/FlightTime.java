package io.hammerhall.springforge.airport;

import java.time.Duration;

/**
 * Расчётное время в пути — по правилу генератора демобазы.
 *
 * <p>Самолёт не летит точно по большому кругу — путь длиннее на десятую часть. Время полёта
 * на крейсерской скорости округляется до 5 минут, плюс 30 минут на руление и прочее.
 * Шереметьево — Пулково на A320neo: 599 км × 1,1 / 830 км/ч ≈ 48 мин → 50 мин + 30 = 1 ч 20 мин.
 */
public final class FlightTime {

    private FlightTime() {
    }

    /** Расчётное время в пути от {@code from} до {@code to} на модели {@code airplane}. */
    public static Duration estimate(Airport from, Airport to, Airplane airplane) {
        double km = GreatCircle.km(from, to) * 1.1;
        // round() демобазы над double — к ближайшему, половина к чётному: как Math.rint
        long fiveMinutes = (long) Math.rint(60.0 / 5 * km / airplane.speedKmh());
        return Duration.ofMinutes(5 * fiveMinutes + 30);
    }
}
