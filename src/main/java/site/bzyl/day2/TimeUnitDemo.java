package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TimeUnitDemo")
public class TimeUnitDemo {
    public static void main(String[] args) {
        log.debug("sleep..");
        try {
            TimeUnit.NANOSECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("wake up...");
    }
}
