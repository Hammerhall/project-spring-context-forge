package io.hammerhall.springforge.context.task.code.level01.easy.part01.ore.auc;

import io.hammerhall.springforge.auction.LotCatalog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Каталог открывается.
 * <p>
 * Описание: аукционный дом готовит сегодняшние торги. Объяви в {@link Config} бин каталога
 * лотов {@link LotCatalog}: сам каталог даёт {@code SampleLots.today()}. Каталог — это список
 * лотов: номер, название, стартовая цена.
 */
@DisplayName("Каталог открывается")
public class AucCode0001 {

    @Configuration
    public static class Config {
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0001");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            context.getBean(LotCatalog.class).lots().forEach(IO::println);
        }
    }
}
