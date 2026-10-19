package io.hammerhall.springforge.auction;

import java.util.List;
import java.util.Objects;

/**
 * Печатный каталог для зала: по строке на лот.
 */
public final class CatalogPrint {

    private final LotCatalog catalog;

    public CatalogPrint(LotCatalog catalog) {
        this.catalog = Objects.requireNonNull(catalog, "catalog");
    }

    /** С какого каталога напечатан. */
    public LotCatalog catalog() {
        return catalog;
    }

    /** Строки печатного каталога: {@code L-101 · Карманные часы, 1912 · от 18 000 ₽}. */
    public List<String> lines() {
        return catalog.lots().stream().map(Lot::toString).toList();
    }
}
