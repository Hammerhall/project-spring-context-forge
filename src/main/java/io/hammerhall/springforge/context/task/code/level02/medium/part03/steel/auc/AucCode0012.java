package io.hammerhall.springforge.context.task.code.level02.medium.part03.steel.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.JournalEntry;
import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.LotCatalog;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Вторые торги.
 * <p>
 * Описание: у дома есть общее на все торги — каталог лотов и часы торгов. У каждых торгов —
 * своё: журнал и стол торгов {@link SaleDesk}. Дом поднимает родительский контекст из
 * {@link HouseConfig}, а каждые торги — дочерний контекст из {@link SaleConfig}.
 * <p>
 * Заполните обе конфигурации. Каталог — {@code SampleLots.today()}, часы — {@code SystemClock},
 * журнал — {@code MemoryJournal}. Стол торгов уже готов: ему нужны каталог, часы и журнал.
 */
@DisplayName("Задание: Вторые торги.")
public class AucCode0012 {

    /** Родительский контекст: общее на все торги. */
    @Configuration
    public static class HouseConfig {
    }

    /** Дочерний контекст: своё у каждых торгов. */
    @Configuration
    public static class SaleConfig {
    }

    /** Дано: стол торгов — открывает лоты и пишет об этом в журнал своих торгов. */
    public static class SaleDesk {

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

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0012");

        try (AnnotationConfigApplicationContext house = new AnnotationConfigApplicationContext(HouseConfig.class)) {
            for (String lot : new String[] {"L-101", "L-103"}) {
                try (AnnotationConfigApplicationContext sale = new AnnotationConfigApplicationContext()) {
                    sale.setParent(house);
                    sale.register(SaleConfig.class);
                    sale.refresh();

                    SaleDesk desk = sale.getBean(SaleDesk.class);
                    desk.open(lot);
                    IO.println("Торги: каталог дома — " + (desk.catalog() == house.getBean(LotCatalog.class))
                            + ", журнал — " + desk.journal().entries());
                }
            }
        }
    }
}
