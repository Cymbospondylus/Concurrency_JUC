package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.LockInterruptiblyDemo")
public class LockInterruptiblyDemo {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            /*try {*/
                log.debug("尝试获得锁...");
                lock.lock();
            /*} catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁...返回...");
                return;
            }*/
            try {
                log.debug("获得锁...");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        t1.start();

        mySleep(1000);
        log.debug("t1.inInterrupted:{}",t1.isInterrupted());
        log.debug("打断 t1");
        t1.interrupt();
        log.debug("t1.inInterrupted:{}",t1.isInterrupted());
        log.debug("t1.inInterrupted:{}",t1.isInterrupted());
    }
}
