package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.ParkUnparkDemo")
public class ParkUnparkDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
           log.debug("start...");
           mySleep(2);
           log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();


        mySleep(1);
        log.debug("t1.state:{}", t1.getState());
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
