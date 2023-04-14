package site.bzyl.day5;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;


@Slf4j(topic = "c.NotifyAllDemo")
public class NotifyAllDemo {
    private static final Object obj = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    obj.wait(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        }, "t2").start();

        mySleep(2);
        log.debug("唤醒obj上其他线程");
        synchronized (obj) {
            obj.notify();
        }
    }
}
