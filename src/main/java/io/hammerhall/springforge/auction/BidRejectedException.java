package io.hammerhall.springforge.auction;

/**
 * Ставка не принята: она меньше минимума по правилам.
 */
public class BidRejectedException extends RuntimeException {

    private final Money offered;
    private final Money minimum;

    public BidRejectedException(Money offered, Money minimum) {
        super("Ставка " + offered + " меньше минимума " + minimum);
        this.offered = offered;
        this.minimum = minimum;
    }

    public Money offered() {
        return offered;
    }

    public Money minimum() {
        return minimum;
    }
}
