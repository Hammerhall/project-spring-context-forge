package io.hammerhall.springforge.context.task.code.level01.easy.part02.steel.auc;

import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.JournalEntry;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Журнал торгов.
 * <p>
 * Описание: аукционист ведёт журнал торгов — записи по порядку: «лот L-101 открыт»,
 * «ставка 18 500 ₽ на лот L-101». Допишите класс {@link HallJournal}: он хранит записи
 * и отдаёт их в том порядке, в каком они сделаны.
 * <p>
 * Журнал должен стать бином — но без {@code @Bean}-метода: пометьте класс {@code @Component}
 * и сделайте так, чтобы контекст, поднятый из {@link Config}, о нём узнал.
 */
@DisplayName("Задание: Журнал торгов.")
public class AucCode0004 {

    @Configuration
    public static class Config {
    }

    public static class HallJournal implements AuctionJournal {

        @Override
        public void record(JournalEntry entry) {
            throw new UnsupportedOperationException("Реализуйте метод");
        }

        @Override
        public List<JournalEntry> entries() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0004");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            AuctionJournal journal = context.getBean(AuctionJournal.class);
            journal.record(new JournalEntry(Instant.now(), "лот L-101 открыт"));
            journal.record(new JournalEntry(Instant.now(), "ставка 18 500 ₽ на лот L-101"));
            journal.entries().forEach(IO::println);
        }
    }
}
