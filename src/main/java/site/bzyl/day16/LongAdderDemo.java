package site.bzyl.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j(topic = "c.LongAdderDemo")
public class LongAdderDemo {
    private static int myAccumulator;
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {


        demo(
                () -> new LongAdder(),
                (accumulator) -> accumulator.increment(),
                (accumulator) -> log.debug("最终结果:{}", accumulator)
        );

        System.out.println("----------------------------------------------------------------");


        demo(
                () -> new Integer(0),
                (accumulator) -> accumulator++,
                (accumulator) -> log.debug("最终结果:{}", accumulator)
        );

    }

    public static <T> void demo(
            Supplier<T> accumulatorSupplier,
            Consumer<T> incrementConsumer,
            Consumer<T> printConsumer) {
        T accumulator = accumulatorSupplier.get();

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    incrementConsumer.accept(accumulator);
                }
            }));
        }
        long start = System.currentTimeMillis();
        threadList.forEach(Thread::start);
        threadList.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        log.debug("耗时：{} ms", end - start);
        printConsumer.accept(accumulator);
    }
}

