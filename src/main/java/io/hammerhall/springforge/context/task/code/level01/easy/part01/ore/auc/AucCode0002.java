package io.hammerhall.springforge.context.task.code.level01.easy.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionHouse;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Дом открывается.
 * <p>
 * Описание: утром аукционный дом открывается. В {@link Config} уже есть каталог лотов.
 * Объявите рядом бин дома {@link AuctionHouse}: он собирается из каталога и часов торгов
 * {@link AuctionClock} и открывается в тот момент, который показывают часы.
 * <p>
 * Часы даёт окружение: на торгах — настоящие, на проверке — свои. Сами часы не создавайте.
 */
@DisplayName("Задание: Дом открывается.")
public class AucCode0002 {

    @Configuration
    public static class Config {

        @Bean
        public LotCatalog lotCatalog() {
            return SampleLots.today();
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0002");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AuctionClock.class, SystemClock::new);   // часы окружения
            context.register(Config.class);
            context.refresh();

            AuctionHouse house = context.getBean(AuctionHouse.class);
            IO.println("Дом открыт в " + house.openedAt() + ", лотов в каталоге: " + house.catalog().lots().size());
        }
    }
}
