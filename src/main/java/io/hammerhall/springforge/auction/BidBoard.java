package io.hammerhall.springforge.auction;

import java.util.Objects;

/**
 * Табло ставок в зале: показывает текущую цену и какую ставку примут следующей —
 * по правилам, которые ему дали.
 */
public final class BidBoard {

    private final BidRules rules;

    public BidBoard(BidRules rules) {
        this.rules = Objects.requireNonNull(rules, "rules");
    }

    /** По каким правилам считает табло. */
    public BidRules rules() {
        return rules;
    }

    /** Какую ставку примут следующей при текущей цене. */
    public Money nextMinimum(Money current) {
        return rules.minimumNext(current);
    }

    /** Строка табло: {@code сейчас 18 000 ₽ · следующая от 18 500 ₽}. */
    public String show(Money current) {
        return "сейчас " + current + " · следующая от " + nextMinimum(current);
    }
}
