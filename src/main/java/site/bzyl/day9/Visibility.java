package site.bzyl.day9;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.Visibility")
public class Visibility {
    private static boolean run = true;
    public static void main(String[] args) {
        new Thread(() -> {
            while (run) {
                System.out.println("t...");
            }
        }).start();

        mySleep(1);
        run = false;
        log.debug("run = {}", run);
    }

}
