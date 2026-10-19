package io.hammerhall.springforge.context.task.code.level02.medium.part02.anvil.auc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Задание: Регистрация участников.
 * <p>
 * Описание: участник торгов получает номер таблички — с ним он поднимает руку в зале.
 * Регистрируют двое: стол в зале {@link HallDesk} и стол телефонных ставок {@link PhoneDesk}.
 * Номера сквозные: с 1, по порядку, без повторов — кто бы ни регистрировал. Номера выдаёт
 * счётчик {@link PaddleCounter}. Допишите счётчик и оба стола и сделайте все три бинами
 * контекста из {@link Config}.
 * <p>
 * Пример: зал, телефон, зал — таблички 1, 2, 3. Новые торги — новый контекст — счёт с 1.
 */
@DisplayName("Задание: Регистрация участников.")
public class AucCode0011 {

    @Configuration
    public static class Config {
    }

    /** Счётчик номеров табличек. */
    public static class PaddleCounter {

        /** Следующий номер таблички: 1, 2, 3… */
        public synchronized int next() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    /** Стол регистрации в зале. */
    public static class HallDesk {

        /** Зарегистрировать участника — номер его таблички. */
        public int register() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }

        /** Чей счётчик у стола. */
        public PaddleCounter counter() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    /** Стол телефонных ставок. */
    public static class PhoneDesk {

        /** Зарегистрировать участника — номер его таблички. */
        public int register() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }

        /** Чей счётчик у стола. */
        public PaddleCounter counter() {
            throw new UnsupportedOperationException("Реализуйте метод");
        }
    }

    @Test
    void runTask() {
        IO.println("Это вывод, а не проверка. Проверить решение: ./hammerhall submit AucCode0011");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class)) {
            HallDesk hall = context.getBean(HallDesk.class);
            PhoneDesk phone = context.getBean(PhoneDesk.class);
            IO.println("Зал: " + hall.register() + ", телефон: " + phone.register() + ", зал: " + hall.register());
        }
    }
}
