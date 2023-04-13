package site.bzyl.day3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.ParkDemo")
public class ParkDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
           log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态: {}", Thread.interrupted());

            LockSupport.park();
            log.debug("unpark....");
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();

    }
}
