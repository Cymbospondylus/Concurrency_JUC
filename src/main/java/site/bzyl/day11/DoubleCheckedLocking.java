package site.bzyl.day11;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.DoubleCheckedLocking")
public class DoubleCheckedLocking {
    public static void main(String[] args) {
        Singleton instance1 = Singleton.getINSTANCE();
        Singleton instance2 = Singleton.getINSTANCE();
        Singleton instance3 = Singleton.getINSTANCE();
        Singleton instance4 = Singleton.getINSTANCE();
        log.debug(instance1.toString());
        log.debug(instance2.toString());
        log.debug(instance3.toString());
        log.debug(instance4.toString());
    }
}

final class Singleton{
    private Singleton(){}

    private static volatile  Singleton INSTANCE = null;

    public static Singleton getINSTANCE() {
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
