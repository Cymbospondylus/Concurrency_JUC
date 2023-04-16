package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.ReentrantLock")
public class ReentrantLockDemo {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();
        try {
            log.debug("enter main...");
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1() {
        lock.lock();
        try {
            log.debug("enter m1");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        lock.lock();
        try {
            log.debug("enter m2");
        } finally {
            lock.unlock();
        }
    }
}
