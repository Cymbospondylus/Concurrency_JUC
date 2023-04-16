package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.OverTimeLock")
public class OverTimeLock {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁...");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取锁失败...");
                    // void run() 的return
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取锁失败...");
                return;
            }
            try {
                log.debug("获取锁成功...");
            } finally {
                lock.unlock();
            }
        }, "t1");

        log.debug("获取锁");
        lock.lock();

        t1.start();

        mySleep(1000);
        log.debug("释放锁");
        lock.unlock();
    }
}
