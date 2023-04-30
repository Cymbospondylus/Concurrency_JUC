package site.bzyl.day19;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimeScheduleTask {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        LocalDateTime now = LocalDateTime.now();
    }
}
