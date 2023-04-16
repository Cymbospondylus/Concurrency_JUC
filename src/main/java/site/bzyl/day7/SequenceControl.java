package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.SequenceControl")
public class SequenceControl {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean t2Finished = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            // 不需要t2Finished的判断了, park的特点就是unpark后不会再被阻塞
            LockSupport.park();
            log.debug("1...");
        }, "t1");

        t1.start();

        new Thread(() -> {
            log.debug("2...");
            t2Finished = true;
            LockSupport.unpark(t1);
        }, "t2").start();
    }
}
