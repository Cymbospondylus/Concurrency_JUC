package site.bzyl.day3;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.Practice")
public class Practice1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶...");
            mySleep(1);
            log.debug("烧开水...");
            mySleep(5);
        }, "老王");
        t1.start();

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶...");
            mySleep(1);
            log.debug("洗茶杯...");
            mySleep(2);
            log.debug("拿茶叶...");
            mySleep(1);

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶...");
        }, "小李");
        t2.start();
    }
}
