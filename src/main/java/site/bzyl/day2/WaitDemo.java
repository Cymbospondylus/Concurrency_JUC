package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.WaitDemo")
public class WaitDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        Thread.sleep(1);
        log.debug("打断标记：{}", t1.isInterrupted());
    }
}
