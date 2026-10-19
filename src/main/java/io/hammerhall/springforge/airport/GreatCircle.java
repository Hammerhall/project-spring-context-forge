package io.hammerhall.springforge.airport;

/**
 * Расстояние между аэропортами по большому кругу — так же, как считает демобаза.
 *
 * <p>Генератор демобазы берёт оператор {@code <@>} расширения {@code earthdistance}: формула
 * гаверсинусов на шаре радиусом 3958,747716 уставной мили, мили — в километры умножением
 * на 1,609344. Здесь — та же формула, поэтому числа совпадают с демобазой: Шереметьево —
 * Пулково 599 км, Шереметьево — Окленд 16 219 км.
 */
public final class GreatCircle {

    /** Радиус Земли в {@code earthdistance}, уставные мили. */
    private static final double EARTH_RADIUS_MILES = 3958.747716;

    /** Километров в уставной миле. */
    private static final double KM_PER_MILE = 1.609344;

    private GreatCircle() {
    }

    /** Расстояние, км — дробное, как в демобазе; округляет тот, кто показывает. */
    public static double km(Airport from, Airport to) {
        double lon1 = Math.toRadians(from.longitude());
        double lat1 = Math.toRadians(from.latitude());
        double lon2 = Math.toRadians(to.longitude());
        double lat2 = Math.toRadians(to.latitude());
        double lonDiff = Math.abs(lon1 - lon2);
        if (lonDiff > Math.PI) {
            lonDiff = 2 * Math.PI - lonDiff;
        }
        double halfLat = Math.sin(Math.abs(lat1 - lat2) / 2);
        double halfLon = Math.sin(lonDiff / 2);
        double sino = Math.sqrt(halfLat * halfLat + Math.cos(lat1) * Math.cos(lat2) * halfLon * halfLon);
        return 2 * EARTH_RADIUS_MILES * Math.asin(Math.min(sino, 1.0)) * KM_PER_MILE;
    }
}
