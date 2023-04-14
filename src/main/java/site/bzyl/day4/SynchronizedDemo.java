package site.bzyl.day4;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.SynchronizedDemo")
public class SynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");


        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("counter = {}", room.getCounter());
    }
}

class Room {
    private int counter = 0;
    public synchronized void increment() {
        counter++;
    }

    public synchronized void decrement() {
        counter--;
    }

    public synchronized int getCounter() {
        return counter;
    }
}
