package io.hammerhall.springforge.auction;

import java.util.Objects;

/**
 * Правила шага с готовой величиной: следующая ставка — не меньше текущей цены плюс шаг.
 *
 * <p>В задачах их часто даёт окружение: проверка кладёт свои правила со своим шагом
 * и смотрит, по ним ли считает служба ученика.
 */
public final class FixedStepRules implements BidRules {

    private final Money step;

    public FixedStepRules(Money step) {
        this.step = Objects.requireNonNull(step, "step");
        if (step.kopecks() == 0) {
            throw new IllegalArgumentException("Шаг ставки не бывает нулевым");
        }
    }

    /** Величина шага. */
    public Money step() {
        return step;
    }

    @Override
    public Money minimumNext(Money current) {
        return current.plus(step);
    }
}
