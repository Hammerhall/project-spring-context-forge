package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.LotCatalog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Серебряный подстаканник.
 * <p>
 * Описание: утром продавец принёс ещё один лот — «Серебряный подстаканник, 1910-е», номер
 * {@code L-104}, стартовая цена 6 000 ₽. Каталог дня — лоты {@code SampleLots.today()} в том же
 * порядке и новый лот последним. Объяви в {@link Config} бин {@link LotCatalog} с таким каталогом.
 * <p>
 * Лоты — данные, а не бины: служб «лот» в контексте быть не должно.
 */
@DisplayName("Серебряный подстаканник")
public class AucCode0009 {

    @Configuration
    public static class Config {
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0009");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            context.getBean(LotCatalog.class).lots().forEach(IO::println);
            IO.println("Бинов-лотов в контексте: " + context.getBeanNamesForType(Lot.class).length);
        }
    }
}
