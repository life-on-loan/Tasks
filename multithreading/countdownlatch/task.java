import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// 1. Отработает ли CountDownLatch нужное количество раз?
public class Increment {
    private static int counter1 = 0;
    private static int counter2 = 0;
    Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 100_000;
        CountDownLatch latch = new CountDownLatch(tasksCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < tasksCount; i++) {
            executor.submit(() -> {
                counter1++;
                counter2++;
                latch.countDown();
            });
        }

        latch.await();
        // 2. Сколько будет выведено?
        System.out.println(counter1);
        System.out.println(counter2);
        System.exit(0);
    }
}
