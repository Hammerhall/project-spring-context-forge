package io.hammerhall.springforge.context.task.code.level01.easy.part02.steel.auc;

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
 * Задание: Шаг ставки.
 * <p>
 * Описание: на торгах каждая следующая ставка выше текущей цены хотя бы на шаг. Величину шага
 * задаёт окружение — бин {@link StepSettings}. Допишите компонент {@link StepRules}: правило
 * {@link BidRules#minimumNext(Money)} возвращает текущую цену плюс шаг. Шаг приходит
 * в {@code StepRules} через конструктор.
 * <p>
 * Пример: шаг 500 ₽, текущая цена 18 000 ₽ — следующая ставка не меньше 18 500 ₽.
 */
@DisplayName("Задание: Шаг ставки.")
public class AucCode0005 {

    @Configuration
    @Import(StepRules.class)
    public static class Config {
    }

    @Component
    public static class StepRules implements BidRules {

        @Override
        public Money minimumNext(Money current) {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0005");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(StepSettings.class, () -> new StepSettings(Money.rubles(500)));   // шаг окружения
            context.register(Config.class);
            context.refresh();

            BidRules rules = context.getBean(BidRules.class);
            IO.println("Цена 18 000 ₽ — следующая ставка не меньше " + rules.minimumNext(Money.rubles(18_000)));
        }
    }
}
