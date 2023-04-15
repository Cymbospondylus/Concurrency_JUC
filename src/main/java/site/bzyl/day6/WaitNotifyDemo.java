package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.WaitNotifyDemo")
public class WaitNotifyDemo {
    public final static Object obj = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    obj.wait(5000);
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
                    obj.wait(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        }, "t2").start();

        mySleep(1);
        log.debug("唤醒 obj 关联的 Monitor对象 的WaitSet中的其他线程");
        synchronized (obj) {
//            obj.notify();
            obj.notifyAll();
        }
    }
}
