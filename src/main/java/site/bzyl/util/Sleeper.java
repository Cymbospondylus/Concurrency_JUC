package site.bzyl.util;

import java.util.concurrent.TimeUnit;

public class Sleeper {
    public static void mySleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
