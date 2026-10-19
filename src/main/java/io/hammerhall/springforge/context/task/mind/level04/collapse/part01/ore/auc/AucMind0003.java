package io.hammerhall.springforge.context.task.mind.level04.collapse.part01.ore.auc;

import io.hammerhall.springforge.auction.Lot;
import io.hammerhall.springforge.auction.SampleLots;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Задание: Служба не нашлась.
 * <p>
 * Описание: каждый лот, принятый на комиссию, проходит экспертизу. Стол экспертизы
 * {@link ExpertiseDesk} помечен {@code @Component}, приём лотов {@link LotIntake} получает его
 * через конструктор. Но контекст из {@link Config} не поднимается: Spring не находит бина
 * {@code ExpertiseDesk}. Почините — не убирая {@code @Component} со стола экспертизы и не
 * превращая его в {@code @Bean}-метод.
 */
@DisplayName("Задание: Служба не нашлась.")
public class AucMind0003 {

    @Configuration
    @Import(LotIntake.class)
    public static class Config {
    }

    /** Стол экспертизы: проверяет лот перед торгами. */
    @Component
    public static class ExpertiseDesk {

        public String verdict(Lot lot) {
            return "лот " + lot.number() + " осмотрен";
        }
    }

    /** Приём лотов на комиссию: каждый лот — через экспертизу. */
    @Component
    public static class LotIntake {

        private final ExpertiseDesk expertise;

        public LotIntake(ExpertiseDesk expertise) {
            this.expertise = Objects.requireNonNull(expertise, "expertise");
        }

        public String accept(Lot lot) {
            return expertise.verdict(lot);
        }

        public ExpertiseDesk expertise() {
            return expertise;
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucMind0003");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            LotIntake intake = context.getBean(LotIntake.class);
            SampleLots.today().lots().forEach(lot -> IO.println(intake.accept(lot)));
        }
    }
}
