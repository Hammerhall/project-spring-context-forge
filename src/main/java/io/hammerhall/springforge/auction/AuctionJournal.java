package io.hammerhall.springforge.auction;

import java.util.List;

/**
 * Журнал торгов: аукционист записывает всё по порядку.
 */
public interface AuctionJournal {

    /** Записать — в конец журнала. */
    void record(JournalEntry entry);

    /** Записи в том порядке, в каком сделаны. */
    List<JournalEntry> entries();
}
