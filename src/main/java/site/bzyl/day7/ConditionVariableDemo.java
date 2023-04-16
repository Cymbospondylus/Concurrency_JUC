package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.ConditionVariableDemo")
public class ConditionVariableDemo {
    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;
    private static final Condition waitCigaretteSet = lock.newCondition();
    private static final Condition waitTakeoutSet = lock.newCondition();
    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("有烟没？:{}", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟, 开始摸鱼...");
                    waitCigaretteSet.await();
                }
                log.debug("有烟没？:{}", hasCigarette);
                if (hasCigarette) {
                    log.debug("开始干活儿...");
                } else {
                    log.debug("不干了...");
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("有外卖没？:{}", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖, 开始摸鱼...");
                    waitTakeoutSet.await();
                }
                log.debug("有外卖没？:{}", hasTakeout);
                if (hasTakeout) {
                    log.debug("开始干活儿...");
                } else {
                    log.debug("不干了...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "小女").start();

        mySleep(1000);

        new Thread(() -> {
            lock.lock();
            try {
                hasTakeout = true;
                waitTakeoutSet.signalAll();
            } finally {
                lock.unlock();
            }
        }, "送外卖的").start();

        mySleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                hasCigarette = true;
                waitCigaretteSet.signalAll();
            } finally {
                lock.unlock();
            }
        }, "送烟的").start();
    }
}
