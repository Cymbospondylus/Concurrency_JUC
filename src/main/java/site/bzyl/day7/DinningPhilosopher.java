package site.bzyl.day7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.DinningPhilosopher")
public class DinningPhilosopher {
    public static void main(String[] args) {
        Chopstick chopstick1 = new Chopstick("筷子1");
        Chopstick chopstick2 = new Chopstick("筷子2");
        Chopstick chopstick3 = new Chopstick("筷子3");
        Chopstick chopstick4 = new Chopstick("筷子4");
        Chopstick chopstick5 = new Chopstick("筷子5");


        new Thread(new Philosopher("张三", chopstick1, chopstick2), "张三").start();
        new Thread(new Philosopher("李四", chopstick2, chopstick3), "李四").start();
        new Thread(new Philosopher("王五", chopstick3, chopstick4), "王五").start();
        new Thread(new Philosopher("赵六", chopstick4, chopstick5), "赵六").start();
        new Thread(new Philosopher("钱七", chopstick1, chopstick5), "钱七").start();
    }
}
@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {
    private String name;
    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }
    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {

                try {
                    if (right.tryLock()) {

                        try {
                            eat();
                        } finally {
                            right.unlock();
                        }

                    }
                } finally {
                    left.unlock();  // 释放左手筷子
                }

            }
        }
    }

    public void eat() {
        log.debug("{} 正在用 {} 和 {} 吃饭...", name, left.getName(), right.getName());
        mySleep(300);
    }
}

class Chopstick extends ReentrantLock {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}