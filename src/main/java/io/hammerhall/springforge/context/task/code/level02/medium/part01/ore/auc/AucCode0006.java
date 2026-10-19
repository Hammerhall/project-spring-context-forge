package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.BidRules;
import io.hammerhall.springforge.auction.Money;
import io.hammerhall.springforge.auction.StepSettings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Задание: Старые отчёты.
 * <p>
 * Описание: у правил шага {@link StepRules} два конструктора. Без параметров — шаг 100 ₽: так
 * считают старые отчёты, и этот конструктор нужен им. Со {@link StepSettings} — шаг из окружения.
 * Оба конструктора оставьте как есть. Сделайте так, чтобы контейнер, создавая бин
 * {@code StepRules}, взял шаг из окружения.
 * <p>
 * Сейчас {@code runTask()} печатает минимум по шагу 100 ₽, хотя окружение даёт 500 ₽.
 */
@DisplayName("Задание: Старые отчёты.")
public class AucCode0006 {

    @Configuration
    @Import(StepRules.class)
    public static class Config {
    }

    @Component
    public static class StepRules implements BidRules {

        private static final Money LEGACY_STEP = Money.rubles(100);

        private final Money step;

        /** Для старых отчётов: шаг 100 ₽. */
        public StepRules() {
            this.step = LEGACY_STEP;
        }

        public StepRules(StepSettings settings) {
            this.step = settings.step();
        }

        @Override
        public Money minimumNext(Money current) {
            return current.plus(step);
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0006");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(StepSettings.class, () -> new StepSettings(Money.rubles(500)));   // шаг окружения
            context.register(Config.class);
            context.refresh();

            BidRules rules = context.getBean(BidRules.class);
            IO.println("Шаг окружения 500 ₽, цена 18 000 ₽ — следующая ставка не меньше "
                    + rules.minimumNext(Money.rubles(18_000)));
        }
    }
}
