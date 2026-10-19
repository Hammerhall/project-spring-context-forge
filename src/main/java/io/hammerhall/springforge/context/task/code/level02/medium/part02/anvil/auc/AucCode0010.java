package io.hammerhall.springforge.context.task.code.level02.medium.part02.anvil.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionHouse;
import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.CatalogPrint;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.MemoryJournal;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Отдел каталогов.
 * <p>
 * Описание: дом собирается из двух конфигураций. {@link CatalogConfig} — отдел каталогов:
 * каталог лотов и печатный каталог для зала, они уже объявлены. {@link HallConfig} — зал:
 * журнал торгов уже есть, допишите дом {@link AuctionHouse}. Проверка поднимает контекст
 * только из {@code HallConfig} — из него должны подняться все бины, а у дома и печатного
 * каталога должен быть один каталог.
 * <p>
 * Часы для дома даёт окружение.
 */
@DisplayName("Задание: Отдел каталогов.")
public class AucCode0010 {

    /** Отдел каталогов. */
    @Configuration
    public static class CatalogConfig {

        @Bean
        public LotCatalog lotCatalog() {
            return SampleLots.today();
        }

        @Bean
        public CatalogPrint catalogPrint(LotCatalog lotCatalog) {
            return new CatalogPrint(lotCatalog);
        }
    }

    /** Зал. Контекст поднимается из него. */
    @Configuration
    public static class HallConfig {

        @Bean
        public AuctionJournal auctionJournal() {
            return new MemoryJournal();
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0010");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AuctionClock.class, SystemClock::new);   // часы окружения
            context.register(HallConfig.class);                           // только зал
            context.refresh();

            AuctionHouse house = context.getBean(AuctionHouse.class);
            CatalogPrint print = context.getBean(CatalogPrint.class);
            print.lines().forEach(IO::println);
            IO.println("Каталог у дома и у печати — один объект: " + (house.catalog() == print.catalog()));
        }
    }
}
