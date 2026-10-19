package io.hammerhall.springforge.auction;

import java.time.Instant;
import java.util.Objects;

/**
 * Запись журнала торгов: когда и что.
 *
 * @param at   время по часам торгов
 * @param text что случилось: «лот L-101 открыт», «ставка 18 500 ₽ на лот L-101»
 */
public record JournalEntry(Instant at, String text) {

    public JournalEntry {
        Objects.requireNonNull(at, "at");
        Objects.requireNonNull(text, "text");
    }

    /** Запись о принятой ставке — один вид на все задачи: «ставка 18 500 ₽ на лот L-101». */
    public static JournalEntry bid(Instant at, Lot lot, Money amount) {
        return new JournalEntry(at, "ставка " + amount + " на лот " + lot.number());
    }
}
