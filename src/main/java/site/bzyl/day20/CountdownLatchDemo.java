package site.bzyl.day20;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        String[] players = new String[10];
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            service.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    players[finalI] = j + "%";
                    System.out.print("\r" + Arrays.toString(players));
                }
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("\nlink start...");
        service.shutdown();
    }
}
