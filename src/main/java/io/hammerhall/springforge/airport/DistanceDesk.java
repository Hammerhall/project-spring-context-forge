package io.hammerhall.springforge.airport;

import java.util.Objects;

/**
 * Справочная расстояний: сколько километров между аэропортами — по большому кругу, как считает
 * демобаза, и по аэропортам того справочника, который ей дали.
 */
public final class DistanceDesk {

    private final AirportDirectory directory;

    public DistanceDesk(AirportDirectory directory) {
        this.directory = Objects.requireNonNull(directory, "directory");
    }

    /** По какому справочнику отвечает справочная. */
    public AirportDirectory directory() {
        return directory;
    }

    /**
     * Расстояние, км, до целых: Шереметьево — Пулково ({@code SVO}, {@code LED}) — 599.
     * Кода нет в справочнике — {@link IllegalArgumentException}.
     */
    public long km(String fromCode, String toCode) {
        return Math.round(GreatCircle.km(directory.byCode(fromCode), directory.byCode(toCode)));
    }

    /** Ответ справочной: {@code Шереметьево — Пулково: 599 км}. */
    public String answer(String fromCode, String toCode) {
        return directory.byCode(fromCode).name() + " — " + directory.byCode(toCode).name()
                + ": " + km(fromCode, toCode) + " км";
    }
}
