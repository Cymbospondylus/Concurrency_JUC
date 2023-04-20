package site.bzyl.day9;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.ParkUnparkDemo")
public class ParkUnparkDemo {
    private static Thread t1;
    private static Thread t2;
    private static Thread t3;
    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(() -> {
            pu.print("a", t2);
        }, "t1");
        t2 = new Thread(() -> {
            pu.print("b", t3);
        }, "t2");
        t3 = new Thread(() -> {
            pu.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();

        mySleep(2);

        LockSupport.unpark(t3);
    }
}

class ParkUnpark {
    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread nextThread) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(nextThread);
        }
    }
}
