package io.hammerhall.springforge.auction;

import java.util.Locale;

/**
 * Деньги — в копейках, целым числом: дробных копеек в торгах не бывает.
 *
 * @param kopecks сумма в копейках, не меньше нуля
 */
public record Money(long kopecks) implements Comparable<Money> {

    public Money {
        if (kopecks < 0) {
            throw new IllegalArgumentException("Деньги не бывают отрицательными: " + kopecks);
        }
    }

    /** Сумма в рублях: {@code Money.rubles(18_000)} — 18 000 ₽. */
    public static Money rubles(long rubles) {
        return new Money(Math.multiplyExact(rubles, 100L));
    }

    /** Эта сумма плюс другая. */
    public Money plus(Money other) {
        return new Money(Math.addExact(kopecks, other.kopecks));
    }

    /** Меньше ли эта сумма другой. */
    public boolean isLessThan(Money other) {
        return kopecks < other.kopecks;
    }

    @Override
    public int compareTo(Money other) {
        return Long.compare(kopecks, other.kopecks);
    }

    /** Как в каталоге: {@code 18 500 ₽}, с копейками — {@code 18 500,50 ₽}. */
    @Override
    public String toString() {
        // Locale.ROOT: разделитель разрядов — запятая на любой машине; у русской локали
        // он неразрывный пробел, и строки журнала разошлись бы с проверкой.
        String rubles = String.format(Locale.ROOT, "%,d", kopecks / 100).replace(',', ' ');
        long rest = kopecks % 100;
        return rest == 0 ? rubles + " ₽" : String.format(Locale.ROOT, "%s,%02d ₽", rubles, rest);
    }
}
