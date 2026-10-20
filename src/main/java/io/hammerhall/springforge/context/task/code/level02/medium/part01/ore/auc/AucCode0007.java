package io.hammerhall.springforge.context.task.code.level02.medium.part01.ore.auc;

import io.hammerhall.springforge.auction.AuctionClock;
import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.BidRejectedException;
import io.hammerhall.springforge.auction.BidRules;
import io.hammerhall.springforge.auction.FixedStepRules;
import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.MemoryJournal;
import io.hammerhall.springforge.auction.Money;
import io.hammerhall.springforge.auction.SampleLots;
import io.hammerhall.springforge.auction.SystemClock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Задание: Служба ставок.
 * <p>
 * Описание: служба ставок {@link BidService} принимает ставку на лот. Ставка не ниже минимума
 * по правилам ставок {@link BidRules} — принята: служба пишет в журнал торгов запись
 * {@code JournalEntry.bid(время, лот, ставка)}, время — по часам торгов. Ставка ниже минимума —
 * {@link BidRejectedException} с этой ставкой и минимумом, журнал не трогается.
 * <p>
 * Правила ставок, журнал торгов и часы даёт окружение — свои не создавай. Допиши службу.
 * В контекст её кладёт проверка — так же, как это сделало бы сканирование компонентов.
 */
@DisplayName("Служба ставок")
public class AucCode0007 {

    @Component
    public static class BidService {

        /**
         * Принять ставку на лот.
         *
         * @param lot     лот, за который торгуются
         * @param current текущая цена лота
         * @param amount  ставка
         * @throws BidRejectedException ставка меньше минимума по правилам ставок
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
            context.registerBean(BidRules.class, () -> new FixedStepRules(Money.rubles(500)));   // окружение:
            context.registerBean(AuctionJournal.class, () -> journal);                           // правила, журнал
            context.registerBean(AuctionClock.class, SystemClock::new);                          // и часы
            context.register(BidService.class);                                                  // как сканирование
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
