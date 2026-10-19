package io.hammerhall.springforge.airport;

/**
 * Класс обслуживания — как в демобазе: Business, Comfort, Economy.
 */
public enum Fare {

    BUSINESS("Бизнес"),
    COMFORT("Комфорт"),
    ECONOMY("Эконом");

    private final String title;

    Fare(String title) {
        this.title = title;
    }

    /** Класс по слову демобазы: {@code Business} → {@link #BUSINESS}. */
    public static Fare of(String demodb) {
        return switch (demodb) {
            case "Business" -> BUSINESS;
            case "Comfort" -> COMFORT;
            case "Economy" -> ECONOMY;
            default -> throw new IllegalArgumentException("нет класса обслуживания " + demodb);
        };
    }

    @Override
    public String toString() {
        return title;
    }
}
