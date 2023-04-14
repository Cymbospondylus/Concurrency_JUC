package site.bzyl.day5;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.SleepVsWait")
public class SleepVsWait {
    public static final Object object = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                log.debug("获得锁...");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        mySleep(1);

        synchronized (object) {
            log.debug("获得锁...");
        }
    }
}
