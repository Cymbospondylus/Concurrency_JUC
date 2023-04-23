package site.bzyl.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Slf4j(topic = "c.AtomicIntegerDemo")
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);

       /* System.out.println(integer.getAndDecrement());
        System.out.println(integer.decrementAndGet());

        System.out.println(integer.getAndAdd(2));
        System.out.println(integer.addAndGet(5));*/

        integer.updateAndGet(value -> 5 * 10);
        log.debug("integer:{}", integer.get());
    }
}
