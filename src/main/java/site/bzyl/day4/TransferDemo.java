package site.bzyl.day4;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "c.TransferDemo")
public class TransferDemo {
    public static Random random = new Random();
    public static int randomAmount() {
        return random.nextInt(100) + 1;
    }
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("total:{}", a.getMoney() + b.getMoney());
    }
}

class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(Account target, int account) {
        synchronized (Account.class) {
            if (this.money >= account) {
                this.setMoney(this.getMoney() - account);
                target.setMoney(target.getMoney() + account);
            }
        }
    }

}
