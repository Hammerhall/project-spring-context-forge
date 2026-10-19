package io.hammerhall.springforge.context.task.code.level01.easy.part02.steel.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionHouse;
import io.hammerhall.springforge.auction.CatalogPrint;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Каталог для зала.
 * <p>
 * Описание: каталог лотов нужен двоим — дому {@link AuctionHouse} и печатному каталогу
 * для зала {@link CatalogPrint}. Объявите в {@link Config} три бина — каталог из
 * {@code SampleLots.today()}, дом и печатный каталог — так, чтобы дом и печатный каталог
 * получили <b>один и тот же</b> каталог: напечатано должно быть ровно то, что продаёт дом.
 * <p>
 * Часы для дома даёт окружение.
 */
@DisplayName("Задание: Каталог для зала.")
public class AucCode0003 {

    @Configuration
    public static class Config {
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0003");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AuctionClock.class, SystemClock::new);   // часы окружения
            context.register(Config.class);
            context.refresh();

            AuctionHouse house = context.getBean(AuctionHouse.class);
            CatalogPrint print = context.getBean(CatalogPrint.class);
            print.lines().forEach(IO::println);
            IO.println("Каталог у дома и у печати — один объект: " + (house.catalog() == print.catalog()));
        }
    }
}
