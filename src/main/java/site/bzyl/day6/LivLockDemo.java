package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.LiveLockDemo")
public class LivLockDemo {
    public static volatile int count = 10;
    public static final Object lock = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 0) {
                mySleep(200);
                count--;
                log.debug("count:{}", count);
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                mySleep(200);
                count++;
                log.debug("count:{}", count);
            }
        }, "t2").start();
    }
}
