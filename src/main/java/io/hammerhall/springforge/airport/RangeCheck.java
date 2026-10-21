package io.hammerhall.springforge.airport;

import java.util.Objects;

/**
 * Проверка дальности: долетит ли модель от аэропорта до аэропорта без посадки — расстояние
 * по большому кругу не больше дальности модели. Аэропорты — из своего справочника, модели —
 * из своего флота.
 */
public final class RangeCheck {

    private final AirportDirectory directory;
    private final Fleet fleet;

    public RangeCheck(AirportDirectory directory, Fleet fleet) {
        this.directory = Objects.requireNonNull(directory, "directory");
        this.fleet = Objects.requireNonNull(fleet, "fleet");
    }

    /** По какому справочнику считает проверка. */
    public AirportDirectory directory() {
        return directory;
    }

    /** По какому флоту. */
    public Fleet fleet() {
        return fleet;
    }

    /**
     * Долетит ли модель без посадки: Бомбардье CRJ700 ({@code CR7}, дальность 3 100 км)
     * от Шереметьева до Емельянова (3 326 км) — нет. Кода нет в справочнике или во флоте —
     * {@link IllegalArgumentException}.
     */
    public boolean reaches(String airplaneCode, String fromCode, String toCode) {
        double km = GreatCircle.km(directory.byCode(fromCode), directory.byCode(toCode));
        return km <= fleet.byCode(airplaneCode).rangeKm();
    }

    /** Ответ проверки: {@code CR7 · Бомбардье CRJ700: Шереметьево — Емельяново — нет}. */
    public String answer(String airplaneCode, String fromCode, String toCode) {
        return fleet.byCode(airplaneCode) + ": " + directory.byCode(fromCode).name() + " — "
                + directory.byCode(toCode).name() + " — " + (reaches(airplaneCode, fromCode, toCode) ? "да" : "нет");
    }
}
