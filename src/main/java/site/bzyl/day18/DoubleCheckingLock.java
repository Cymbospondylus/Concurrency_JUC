package site.bzyl.day18;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.DoubleCheckingLock")
public class DoubleCheckingLock {
    public static void main(String[] args) {

    }
}

class Singleton {
    private static volatile Singleton INSTANCE;
    private Singleton() {

    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
