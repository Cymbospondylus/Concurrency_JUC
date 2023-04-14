package site.bzyl.day5;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.GuardedSuspension")
public class GuardedSuspension {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        Thread t1 = new Thread(() -> {
            log.debug("waiting result....");
            Object response = guardedObject.get(2000);
            log.debug("response: {}", response);
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("download...");
            mySleep(1);
            guardedObject.complete(null);
            log.debug("download complete...");
        }, "t2");

        t1.start();
        t2.start();
    }
}

class GuardedObject {
    private Object response;

    public synchronized Object get(long timeout) {
        // 记录等待的开始时间
        long beginTime = System.currentTimeMillis();
        long passedTime = 0;
        while (response == null) {
            long waitTime = timeout - passedTime;
            if (waitTime <= 0) break;
            try {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            passedTime = System.currentTimeMillis() - beginTime;
        }
        return response;
    }

    public synchronized void complete(Object response) {
        this.response = response;
        this.notifyAll();
    }
}
