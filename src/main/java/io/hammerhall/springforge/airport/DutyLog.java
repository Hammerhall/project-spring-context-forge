package io.hammerhall.springforge.airport;

import java.util.List;

/**
 * Журнал дежурной смены: всё по порядку.
 */
public interface DutyLog {

    /** Записать — в конец журнала. */
    void record(LogEntry entry);

    /** Записи в том порядке, в каком сделаны. */
    List<LogEntry> entries();
}
