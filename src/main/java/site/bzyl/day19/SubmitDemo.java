package site.bzyl.day19;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j(topic = "c.SubmitDemo")
public class SubmitDemo {
    public static void main(String[] args)  {
        method1();
    }

    private static void method1() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> submit = pool.submit(() -> {
            log.debug("calling..");
            return "ok";
        });

        try {
            log.debug("{}", submit.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
