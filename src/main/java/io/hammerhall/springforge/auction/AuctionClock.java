package io.hammerhall.springforge.auction;

import java.time.Instant;

/**
 * Часы торгов. Во многих задачах их даёт окружение: проверка — с заданным временем.
 */
public interface AuctionClock {

    Instant now();
}
