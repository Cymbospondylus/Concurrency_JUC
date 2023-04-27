package site.bzyl.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.CompareAndSet")
public class CompareAndSet {
    public static void main(String[] args) {
        log.debug("AccountWithLock...");
        Account account = new AccountWithLock(10000);
        Account.test(account);
        log.debug("AccountWithCAS...");
        Account account1 = new AccountWithCAS(10000);
        Account.test(account1);
    }
}

@Slf4j(topic = "c.AccountWithCAS")
class AccountWithCAS implements Account {
    private AtomicInteger balance;

    public AccountWithCAS(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(int amount) {
        /*while (true) {
            int prev = balance.get();
            int next = prev - amount;
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }*/
        balance.getAndAdd(-1 * amount);
    }

}

@Slf4j(topic = "c.AccountWithLock")
class AccountWithLock implements Account{
    private Integer balance;
    private ReentrantLock lock = new ReentrantLock();

    AccountWithLock(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(int amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

}