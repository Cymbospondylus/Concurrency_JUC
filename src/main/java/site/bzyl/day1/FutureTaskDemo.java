package site.bzyl.day1;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
@Slf4j(topic = "c.FutureTaskDemo")
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            for (int i = 0; i < 100000; i++) {
                log.debug("running...");
                Thread.sleep(1000);
            }
            return 0;
        });

        Thread t1 = new Thread(task, "t1");

        t1.start();

        for (int i = 0; i < 100000; i++) {
            log.debug("running...");
            Thread.sleep(1000);
        }

        log.debug("{} = {}", "result",task.get());

    }
}
