package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.CatalogPrint;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.SampleLots;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Старый отчёт ищет печать.
 * <p>
 * Описание: печатный каталог для зала {@link CatalogPrint} ищут в контексте по имени: отдел
 * каталогов — по имени {@code hallPrint}, старый отчёт — по имени {@code printedCatalog}.
 * Объявите в {@link Config} печатный каталог так, чтобы он находился по обоим именам. Каталог
 * лотов в {@code Config} уже есть — печатный каталог печатается с него.
 */
@DisplayName("Задание: Старый отчёт ищет печать.")
public class AucCode0008 {

    @Configuration
    public static class Config {

        @Bean
        public LotCatalog lotCatalog() {
            return SampleLots.today();
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0008");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            for (String name : context.getBeanNamesForType(CatalogPrint.class)) {
                IO.println("Бин «" + name + "», ещё зовётся " + Arrays.toString(context.getAliases(name)));
            }
            CatalogPrint print = context.getBean("hallPrint", CatalogPrint.class);
            print.lines().forEach(IO::println);
        }
    }
}
