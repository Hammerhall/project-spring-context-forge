package io.hammerhall.springforge.context.task.mind.level04.collapse.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.MemoryJournal;
import io.hammerhall.springforge.auction.SaleDesk;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Итоги вечера.
 * <p>
 * Описание: вечерние торги ведут два стола {@link SaleDesk} — в зале и на телефоне. Оба пишут
 * в один журнал торгов: по нему потом подводят итоги вечера. Конфигурацию пометили
 * {@code @Configuration(proxyBeanMethods = false)} — так она стартует быстрее. И сразу беда:
 * зал и телефон пишут каждый в свой журнал, а журнал торгов пуст. Почини {@link Config},
 * оставив {@code proxyBeanMethods = false}.
 * <p>
 * Каталог лотов и часы торгов даёт окружение.
 */
@DisplayName("Итоги вечера")
public class AucMind0002 {

    @Configuration(proxyBeanMethods = false)
    public static class Config {

        @Bean
        public AuctionJournal eveningJournal() {
            return new MemoryJournal();
        }

        @Bean
        public SaleDesk hallDesk(LotCatalog catalog, AuctionClock clock) {
            return new SaleDesk(catalog, clock, eveningJournal());
        }

        @Bean
        public SaleDesk phoneDesk(LotCatalog catalog, AuctionClock clock) {
            return new SaleDesk(catalog, clock, eveningJournal());
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucMind0002");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(LotCatalog.class, SampleLots::today);    // окружение:
            context.registerBean(AuctionClock.class, SystemClock::new);   // каталог и часы
            context.register(Config.class);
            context.refresh();

            SaleDesk hall = context.getBean("hallDesk", SaleDesk.class);
            SaleDesk phone = context.getBean("phoneDesk", SaleDesk.class);
            hall.open("L-101");
            phone.open("L-103");
            IO.println("Журнал торгов: " + context.getBean(AuctionJournal.class).entries());
            IO.println("У зала и телефона один журнал: " + (hall.journal() == phone.journal()));
        }
    }
}
