package site.bzyl.day10;

import lombok.extern.slf4j.Slf4j;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.TwoPhraseTermination")
public class TwoPhraseTermination {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        monitor.start();
        monitor.start();
        monitor.start();

        mySleep(2500);
        log.debug("打断监控...");
        monitor.stop();
    }
}
@Slf4j(topic = "c.Monitor")
class Monitor {
    private Thread monitorThread;
    private volatile boolean stop;

    private boolean started;

    public void start() {
        // 犹豫模式Balking
        synchronized(this) {
            if (started) return;
            started = true;
        }
        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("terminate...");
                    break;
                }
                log.debug("monitoring...");
                mySleep(1000);
            }
        }, "monitor");
        monitorThread.start();
    }

    public void stop() {
        stop = true;
    }
}