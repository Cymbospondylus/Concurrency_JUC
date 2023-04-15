package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.MultiLock")
public class MultiLock {
    public static final Object lockA = new Object();
    public static final Object lockB = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lockA) {
                log.debug("lock A...");
                synchronized (lockB) {
                    log.debug("lock B...");
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lockB) {
                log.debug("lock B...");
                synchronized (lockA) {
                    log.debug("lock A...");
                }
            }
        }, "t2").start();
    }
}
