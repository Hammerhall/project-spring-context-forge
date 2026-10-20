package io.hammerhall.springforge.context.task.mind.level04.collapse.part01.ore.auc;

import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.LotCatalog;
import io.hammerhall.springforge.auction.SampleLots;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Счётчик в каталоге.
 * <p>
 * Описание: ниже — конфигурация {@link Config} с каталогом {@link CountingCatalog}, который
 * считает, сколько раз вызывался его конструктор. Сценарий торгового дня:
 * <ol>
 *   <li>поднять контекст из {@code Config};</li>
 *   <li>трижды попросить у него каталог — {@code getBean(LotCatalog.class)};</li>
 *   <li>поднять второй контекст из {@code Config};</li>
 *   <li>дважды попросить каталог у второго.</li>
 * </ol>
 * Ответь, <b>не запуская</b> сценарий, — запустить и списать можно, но тогда задача ничему
 * не научит. Верни в {@link Answer}:
 * <ul>
 *   <li>{@code births} — сколько раз за сценарий родился каталог;</li>
 *   <li>{@code bornBeforeFirstRequest} — родился ли каталог первого контекста раньше, чем его
 *       попросили впервые;</li>
 *   <li>{@code birthsWithoutRequests} — сколько раз родится каталог, если поднять и закрыть
 *       два контекста, ни разу не попросив каталог.</li>
 * </ul>
 */
@DisplayName("Счётчик в каталоге")
public class AucMind0001 {

    /** Дано: каталог, который считает свои рождения. */
    public static class CountingCatalog implements LotCatalog {

        private static int births;

        private final List<Lot> lots = SampleLots.today().lots();

        public CountingCatalog() {
            births++;
        }

        @Override
        public List<Lot> lots() {
            return lots;
        }
    }

    /** Дано: конфигурация из сценария. */
    @Configuration
    public static class Config {

        @Bean
        public CountingCatalog lotCatalog() {
            return new CountingCatalog();
        }
    }

    /** Ответ. */
    public record Answer(int births, boolean bornBeforeFirstRequest, int birthsWithoutRequests) {
    }

    /** Твой ответ — данными, без запуска сценария. */
    public static Answer task() {
        throw new UnsupportedOperationException("Реализуйте метод");
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucMind0001");

        IO.println("Твой ответ: " + task());
    }
}
