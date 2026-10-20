package io.hammerhall.springforge.context.task.code.level02.medium.part03.steel.auc;

import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.SaleDesk;
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
 * Заполни обе конфигурации. Каталог — {@code SampleLots.today()}, часы — {@code SystemClock},
 * журнал — {@code MemoryJournal}. Стол торгов {@link SaleDesk} — готовый, из ядра: ему нужны
 * каталог, часы и журнал.
 */
@DisplayName("Вторые торги")
public class AucCode0012 {

    /** Родительский контекст: общее на все торги. */
    @Configuration
    public static class HouseConfig {
    }

    /** Дочерний контекст: своё у каждых торгов. */
    @Configuration
    public static class SaleConfig {
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
