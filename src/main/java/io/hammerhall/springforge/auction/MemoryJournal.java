package io.hammerhall.springforge.auction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Журнал торгов в памяти — готовый.
 *
 * <p>В задачах его часто даёт окружение: проверка кладёт свой журнал и читает, что в него
 * записала служба ученика. Записи — в порядке записи; {@link #entries()} — снимок, а не
 * живой список.
 */
public final class MemoryJournal implements AuctionJournal {

    private final List<JournalEntry> entries = new ArrayList<>();

    @Override
    public synchronized void record(JournalEntry entry) {
        entries.add(Objects.requireNonNull(entry, "entry"));
    }

    @Override
    public synchronized List<JournalEntry> entries() {
        return List.copyOf(entries);
    }
}
