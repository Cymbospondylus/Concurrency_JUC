package site.bzyl.day3;

import lombok.extern.slf4j.Slf4j;

public class TwoPhraseTermination {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();
        Thread.sleep(3500);
        monitor.stop();
    }
}

@Slf4j(topic = "c.Monitor")
class Monitor {
    private Thread monitor;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                // 线程被打断
                // inInterrupted()不会清除打断标记, interrupted()会清除打断标记
                if (currentThread.isInterrupted()) {
                    log.debug("料理后事...");
                    break;
                }
                try {
                    Thread.sleep(1000);     // 情况1 -> 被打断会清除打断标记
                    log.debug("执行监控记录...");   // 情况2 -> 被打断不会清除打断标记
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    currentThread.interrupt();
                    log.debug("重新设置打断标记...");
                }
            }
        });
        monitor.start();
    }
    // 停止监控线程
    public void stop() {
        monitor.interrupt();
    }
}