import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Анализ кода:
 *
 * 1. Отработает ли CountDownLatch нужное количество раз?
 *    ОТВЕТ: ДА, отработает корректно.
 *    - Счётчик инициализируется значением 100_000
 *    - Каждая из 100_000 задач вызывает countDown() один раз
 *    - latch.await() дождётся обнуления счётчика
 *
 * 2. Сколько будет выведено?
 *    ОТВЕТ: Значения будут МЕНЬШЕ 100_000 и НЕПРЕДСКАЗУЕМЫ.
 *    - Код НЕ потокобезопасен (гонка данных)
 *    - counter1++ и counter2++ не атомарны
 *    - Потеря обновлений из-за одновременного доступа потоков
 *    - counter1 и counter2 могут отличаться друг от друга
 */
public class Increment {
    private static int counter1 = 0;
    private static int counter2 = 0;
    // Lock объявлен, но НЕ ИСПОЛЬЗУЕТСЯ в коде
    // Lock lock = new ReentrantLock(); // <- эта строка не скомпилируется без импорта

    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 100_000;
        CountDownLatch latch = new CountDownLatch(tasksCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < tasksCount; i++) {
            executor.submit(() -> {
                // Эти операции НЕ потокобезопасны!
                counter1++; // read -> increment -> write (не атомарно)
                counter2++; // read -> increment -> write (не атомарно)
                latch.countDown(); // всегда отработает правильно
            });
        }

        latch.await(); // ждём завершения всех задач

        // РЕЗУЛЬТАТ: почти гарантированно меньше 100_000
        // Например: 98765 и 98765, или 99998 и 99997, или другие значения
        System.out.println(counter1);
        System.out.println(counter2);

        executor.shutdown();
        System.exit(0);
    }
}