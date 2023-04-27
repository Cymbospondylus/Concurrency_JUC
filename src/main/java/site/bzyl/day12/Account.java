package site.bzyl.day12;

import java.util.ArrayList;
import java.util.List;

public interface Account {
    public abstract Integer getBalance();

    void withdraw(int amount);

    static void test(Account account) {
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long passedTime = end - start;
        System.out.printf("balance:%d, time:%d ms\n", account.getBalance(), passedTime);
    }
}
