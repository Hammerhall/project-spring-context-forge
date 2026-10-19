package io.hammerhall.springforge.auction;

/**
 * Правила ставок: какой может быть следующая ставка.
 */
public interface BidRules {

    /** Самая маленькая ставка, которую примут при текущей цене. */
    Money minimumNext(Money current);
}
