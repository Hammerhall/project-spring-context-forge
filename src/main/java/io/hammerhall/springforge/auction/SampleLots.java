package io.hammerhall.springforge.auction;

import java.util.List;

/**
 * Готовый каталог для задач: лоты сегодняшних торгов.
 *
 * <p>Каждый вызов {@link #today()} — <b>новый</b> объект каталога с теми же лотами. Это нарочно:
 * задачи проверяют, что дом получает тот каталог, что лежит в контейнере, а не второй такой же.
 */
public final class SampleLots {

    private static final List<Lot> TODAY = List.of(
            new Lot("L-101", "Карманные часы, 1912", Money.rubles(18_000)),
            new Lot("L-102", "Чугунный утюг, XIX век", Money.rubles(4_500)),
            new Lot("L-103", "Гравюра «Порт», 1890-е", Money.rubles(32_000)));

    private SampleLots() {
    }

    /** Новый каталог сегодняшних торгов. */
    public static LotCatalog today() {
        return new ListCatalog(TODAY);
    }

    /** Каталог из своих лотов — в том порядке, в каком переданы. */
    public static LotCatalog of(List<Lot> lots) {
        return new ListCatalog(List.copyOf(lots));
    }

    private record ListCatalog(List<Lot> lots) implements LotCatalog {
    }
}
