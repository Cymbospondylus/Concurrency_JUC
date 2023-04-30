package site.bzyl.day19;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.MyRunnable")
public class MyRunnable {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                log.debug("run...");
            }
        };
    }

}
@FunctionalInterface
interface RunnableDemo {
    void run();

    class AnonymousClass {

    }
}

class MyThread {
    RunnableDemo runnable;

    public MyThread(RunnableDemo runnable) {
        this.runnable = runnable;
    }
}


