package io.hammerhall.springforge.auction;

import java.util.Objects;

/**
 * Шаг ставки: на сколько следующая ставка обязана быть выше текущей цены.
 * В задачах его задаёт окружение.
 *
 * @param step величина шага, больше нуля
 */
public record StepSettings(Money step) {

    public StepSettings {
        Objects.requireNonNull(step, "step");
        if (step.kopecks() == 0) {
            throw new IllegalArgumentException("Шаг ставки не бывает нулевым");
        }
    }
}
