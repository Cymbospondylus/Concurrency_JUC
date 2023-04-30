package site.bzyl.day20;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        DataContainer container = new DataContainer();

        new Thread(() ->{
            container.write(10);
        }, "t1").start();

        new Thread(() -> {
            container.write(100);
        }, "t2").start();
    }
}
@Slf4j(topic = "c.DataContainer")
class DataContainer {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private int data;

    public int read() {
        log.debug("获取readLock...");
        readLock.lock();

        try {
            log.debug("read...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        } finally {
            log.debug("释放readLock...");
            readLock.unlock();
        }
    }

    public void write(int data) {
        log.debug("获取writeLock...");
        writeLock.lock();

        try {
            log.debug("write...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            log.debug("释放writeLock...");
            writeLock.unlock();
        }
    }
}
