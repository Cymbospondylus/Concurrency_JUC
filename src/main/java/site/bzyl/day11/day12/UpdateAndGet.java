package site.bzyl.day11.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Slf4j(topic = "c.UpdateAndGet")
public class UpdateAndGet {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(5);

        System.out.println(updateAndGet(i, p -> p / 2));

        log.debug("i:{}", i.get());
    }

    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev, next)){
                return next;
            }
        }
    }
}
