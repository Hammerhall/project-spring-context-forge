package io.hammerhall.springforge.airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Журнал смены в памяти — готовый.
 *
 * <p>В задачах его часто даёт окружение: проверка кладёт свой журнал и читает, что в него
 * записала служба ученика. {@link #entries()} — снимок, а не живой список.
 */
public final class MemoryDutyLog implements DutyLog {

    private final List<LogEntry> entries = new ArrayList<>();

    @Override
    public synchronized void record(LogEntry entry) {
        entries.add(Objects.requireNonNull(entry, "entry"));
    }

    @Override
    public synchronized List<LogEntry> entries() {
        return List.copyOf(entries);
    }
}
