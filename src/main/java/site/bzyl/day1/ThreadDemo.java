package site.bzyl.day1;

import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "c.ThreadDemo")
public class ThreadDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> log.debug("running"), "t1");
        t.start();
        log.debug("running");
    }
}
