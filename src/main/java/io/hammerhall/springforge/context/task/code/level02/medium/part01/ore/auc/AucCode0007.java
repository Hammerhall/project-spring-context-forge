package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.BidRejectedException;
import io.hammerhall.springforge.auction.BidRules;
import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.MemoryJournal;
import io.hammerhall.springforge.auction.Money;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.StepSettings;
import io.hammerhall.springforge.auction.SystemClock;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Задание: Служба ставок.
 * <p>
 * Описание: служба ставок {@link BidService} принимает ставку на лот. Ставка не ниже минимума
 * по правилам шага — принята: служба пишет в журнал торгов запись
 * {@code JournalEntry.bid(время, лот, ставка)}, время — по часам торгов. Ставка ниже минимума —
 * {@link BidRejectedException} с этой ставкой и минимумом, журнал не трогается.
 * <p>
 * Правила шага {@link StepRules} уже готовы. Журнал торгов, часы и шаг даёт окружение — свои
 * не создавайте. Допишите службу и сделайте её бином контекста из {@link Config}.
 */
@DisplayName("Задание: Служба ставок.")
public class AucCode0007 {

    @Configuration
    @Import(StepRules.class)
    public static class Config {
    }

    /** Дано: правила шага из задачи «Шаг ставки». */
    @Component
    public static class StepRules implements BidRules {

        private final StepSettings settings;

        public StepRules(StepSettings settings) {
            this.settings = Objects.requireNonNull(settings, "settings");
        }

        @Override
        public Money minimumNext(Money current) {
            return current.plus(settings.step());
        }
    }

    @Component
    public static class BidService {

        /**
         * Принять ставку на лот.
         *
         * @param lot     лот, за который торгуются
         * @param current текущая цена лота
         * @param amount  ставка
         * @throws BidRejectedException ставка меньше минимума по правилам шага
         */
        public void accept(Lot lot, Money current, Money amount) {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0007");

        MemoryJournal journal = new MemoryJournal();
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.registerBean(StepSettings.class, () -> new StepSettings(Money.rubles(500)));   // окружение:
            context.registerBean(AuctionJournal.class, () -> journal);                             // шаг, журнал
            context.registerBean(AuctionClock.class, SystemClock::new);                            // и часы
            context.register(Config.class);
            context.refresh();

            BidService bids = context.getBean(BidService.class);
            Lot watch = SampleLots.today().lots().get(0);
            bids.accept(watch, watch.startPrice(), Money.rubles(18_500));
            try {
                bids.accept(watch, Money.rubles(18_500), Money.rubles(18_600));
            } catch (BidRejectedException e) {
                IO.println("Отказ: " + e.getMessage());
            }
            journal.entries().forEach(IO::println);
        }
    }
}
