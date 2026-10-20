package io.hammerhall.springforge.context.task.code.level02.medium.part02.anvil.auc;

import io.hammerhall.springforge.auction.AuctionJournal;
import io.hammerhall.springforge.auction.BidBoard;
import io.hammerhall.springforge.auction.BidRules;
import io.hammerhall.springforge.auction.FixedStepRules;
import io.hammerhall.springforge.auction.MemoryJournal;
import io.hammerhall.springforge.auction.Money;
import io.hammerhall.springforge.auction.StepSettings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Правила дома.
 * <p>
 * Описание: дом собирается из двух конфигураций. {@link RulesConfig} — отдел торгов: шаг ставки
 * и правила ставок, они уже объявлены. {@link HallConfig} — зал: журнал торгов уже есть, допиши
 * табло ставок {@link BidBoard} — оно показывает, какую ставку примут следующей, по правилам
 * отдела торгов.
 * <p>
 * Проверка поднимает контекст только из {@code HallConfig}. Из него должны подняться все бины
 * обеих конфигураций, и у каждого бина — одно место.
 */
@DisplayName("Правила дома")
public class AucCode0010 {

    /** Отдел торгов. */
    @Configuration
    public static class RulesConfig {

        @Bean
        public StepSettings stepSettings() {
            return new StepSettings(Money.rubles(500));
        }

        @Bean
        public BidRules bidRules(StepSettings settings) {
            return new FixedStepRules(settings.step());
        }
    }

    /** Зал. Контекст поднимается из него. */
    @Configuration
    public static class HallConfig {

        @Bean
        public AuctionJournal hallJournal() {
            return new MemoryJournal();
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0010");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HallConfig.class)) {
            BidBoard board = context.getBean(BidBoard.class);
            IO.println("Табло: " + board.show(Money.rubles(18_000)));
        }
    }
}
