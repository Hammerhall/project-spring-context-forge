package io.hammerhall.springforge.airport;

/**
 * Схемы салонов флота.
 */
public interface SeatMaps {

    /**
     * Схема салона модели. У грузовой модели — пустая схема; модели нет во флоте —
     * {@link IllegalArgumentException}.
     */
    SeatMap of(String airplaneCode);
}
