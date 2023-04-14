package site.bzyl.day5;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.WaitDemo")
public class WaitDemo {
    public static final Object lock = new Object();
    public static void main(String[] args) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
