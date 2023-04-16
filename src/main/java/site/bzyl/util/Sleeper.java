package site.bzyl.util;

import java.util.concurrent.TimeUnit;

public class Sleeper {
    public static void mySleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
