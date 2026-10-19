package io.hammerhall.springforge.auction;

import java.util.Objects;

/**
 * Лот — то, что выставлено на торги.
 *
 * @param number     номер лота в каталоге: {@code L-101}
 * @param title      что это: «Карманные часы, 1912»
 * @param startPrice стартовая цена — с неё начинаются торги
 */
public record Lot(String number, String title, Money startPrice) {

    public Lot {
        Objects.requireNonNull(number, "number");
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(startPrice, "startPrice");
    }

    /** Строка печатного каталога: {@code L-101 · Карманные часы, 1912 · от 18 000 ₽}. */
    @Override
    public String toString() {
        return number + " · " + title + " · от " + startPrice;
    }
}
