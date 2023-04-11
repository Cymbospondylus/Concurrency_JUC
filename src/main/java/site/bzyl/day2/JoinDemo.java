package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.JoinDemo")
public class JoinDemo {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        method();
    }

    private static void method() throws InterruptedException {
        log.debug("start...");
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
            log.debug("end...");
        }, "t1");
        t1.start();
        t1.join();
        log.debug("result：{}", r);
        log.debug("end...");
    }
}
