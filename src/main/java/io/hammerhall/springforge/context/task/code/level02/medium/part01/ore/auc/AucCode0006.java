package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.FixedClock;
import io.hammerhall.springforge.auction.JournalEntry;
import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import java.time.Instant;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Задание: Старые отчёты.
 * <p>
 * Описание: стол открытия лотов {@link OpeningDesk} отмечает, когда открыт лот, — по часам.
 * У него два конструктора. Без параметров — по настоящим часам: так стол создают старые
 * отчёты, и этот конструктор нужен им. С часами торгов {@link AuctionClock} — по тем часам,
 * что дали. Оба конструктора оставь как есть. Сделай так, чтобы контейнер, создавая бин
 * {@code OpeningDesk}, взял часы торгов из окружения.
 * <p>
 * Сейчас {@code runTask()} печатает настоящее время, хотя часы окружения стоят на утре торгов.
 * В контекст {@code OpeningDesk} кладёт проверка — так же, как это сделало бы сканирование
 * компонентов.
 */
@DisplayName("Старые отчёты")
public class AucCode0006 {

    @Component
    public static class OpeningDesk {

        private final AuctionClock clock;

        /** Для старых отчётов: настоящие часы. */
        public OpeningDesk() {
            this.clock = new SystemClock();
        }

        public OpeningDesk(AuctionClock clock) {
            this.clock = Objects.requireNonNull(clock, "clock");
        }

        /** Открыть лот: запись «лот L-101 открыт» со временем по часам стола. */
        public JournalEntry open(Lot lot) {
            return new JournalEntry(clock.now(), "лот " + lot.number() + " открыт");
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0006");

        Instant morning = Instant.parse("2026-10-18T07:00:00Z");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(AuctionClock.class, () -> new FixedClock(morning));   // часы окружения
            context.register(OpeningDesk.class);                                       // как сканирование
            context.refresh();

            Lot watch = SampleLots.today().lots().get(0);
            IO.println("Часы окружения: " + morning + ", стол записал: " + context.getBean(OpeningDesk.class).open(watch));
        }
    }
}
