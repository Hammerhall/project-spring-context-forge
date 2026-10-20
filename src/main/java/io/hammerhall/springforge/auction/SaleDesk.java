package io.hammerhall.springforge.auction;

import java.util.Objects;

/**
 * Стол торгов: открывает лоты из каталога и пишет об этом в журнал торгов — по часам торгов.
 */
public final class SaleDesk {

    private final LotCatalog catalog;
    private final AuctionClock clock;
    private final AuctionJournal journal;

    public SaleDesk(LotCatalog catalog, AuctionClock clock, AuctionJournal journal) {
        this.catalog = Objects.requireNonNull(catalog, "catalog");
        this.clock = Objects.requireNonNull(clock, "clock");
        this.journal = Objects.requireNonNull(journal, "journal");
    }

    /** Открыть лот по номеру: запись «лот L-101 открыт» в журнал торгов. */
    public void open(String lotNumber) {
        Lot lot = catalog.lots().stream()
                .filter(candidate -> candidate.number().equals(lotNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("В каталоге нет лота " + lotNumber));
        journal.record(new JournalEntry(clock.now(), "лот " + lot.number() + " открыт"));
    }

    public LotCatalog catalog() {
        return catalog;
    }

    public AuctionClock clock() {
        return clock;
    }

    public AuctionJournal journal() {
        return journal;
    }
}
