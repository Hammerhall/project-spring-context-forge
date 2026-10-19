package io.hammerhall.springforge.auction;

import java.util.List;

/**
 * Каталог лотов дома — что продаётся на торгах.
 */
public interface LotCatalog {

    /** Лоты в порядке каталога. */
    List<Lot> lots();
}
