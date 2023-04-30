package site.bzyl.day19;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "c.ScheduledThreadPool")
public class ScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        method1(pool);

    }

    private static void method1(ScheduledExecutorService pool) throws ExecutionException, InterruptedException {
        ScheduledFuture<Boolean> schedule = pool.schedule(() -> {

            log.debug("task1...");
            int i = 1 / 0;
            return true;
        }, 1, TimeUnit.SECONDS);


        schedule.get();

        pool.schedule(() -> {
            log.debug("task2...");
        }, 1, TimeUnit.SECONDS);
    }
}
