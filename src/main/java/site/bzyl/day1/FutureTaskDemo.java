package site.bzyl.day1;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
@Slf4j(topic = "c.FutureTaskDemo")
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running");
            Thread.sleep(2000);
            return 1000;
        });

        Thread t1 = new Thread(task, "t1");

        t1.start();

        log.debug("{} = {}", "result",task.get());

    }
}
