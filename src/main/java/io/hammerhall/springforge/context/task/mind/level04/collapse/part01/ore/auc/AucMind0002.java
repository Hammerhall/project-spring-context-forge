package io.hammerhall.springforge.context.task.mind.level04.collapse.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionHouse;
import io.hammerhall.springforge.auction.CatalogPrint;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Зал печатает не то.
 * <p>
 * Описание: конфигурацию дома пометили {@code @Configuration(proxyBeanMethods = false)} — так
 * она стартует быстрее. И сразу беда: дом и печатный каталог для зала получают <b>разные</b>
 * каталоги — в зале печатают не то, что продаёт дом. Почините {@link Config}, оставив
 * {@code proxyBeanMethods = false}.
 * <p>
 * Часы для дома даёт окружение.
 */
@DisplayName("Задание: Зал печатает не то.")
public class AucMind0002 {

    @Configuration(proxyBeanMethods = false)
    public static class Config {

        @Bean
        public LotCatalog lotCatalog() {
            return SampleLots.today();
        }

        @Bean
        public AuctionHouse auctionHouse(AuctionClock clock) {
            return new AuctionHouse(lotCatalog(), clock);
        }

        @Bean
        public CatalogPrint catalogPrint() {
            return new CatalogPrint(lotCatalog());
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucMind0002");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AuctionClock.class, SystemClock::new);   // часы окружения
            context.register(Config.class);
            context.refresh();

            AuctionHouse house = context.getBean(AuctionHouse.class);
            CatalogPrint print = context.getBean(CatalogPrint.class);
            IO.println("Каталог у дома и у печати — один объект: " + (house.catalog() == print.catalog()));
        }
    }
}
