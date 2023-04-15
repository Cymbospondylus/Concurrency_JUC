package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

import static site.bzyl.util.Sleeper.mySleep;

@Slf4j(topic = "c.MessageQueueDemo")
public class MessageQueueDemo {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() ->{
                queue.put(new Message(id, "值" + id));
            }, "生产者" + i).start();
        }


        new Thread(() -> {
            while (true) {
                mySleep(1);
                Message message = queue.take();
            }
        }, "消费者").start();
    }
}

// 消息队列类, Java线程之间通信, id很重要
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    // 消息的队列集合 LinkedList是Java的双向链表实现
    private LinkedList<Message> list = new LinkedList<>();
    // 队列容量
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    // 存入消息
    public void put(Message message) {
        synchronized (list) {
            while (list.size() == capacity) {
                try {
                    log.debug("队列已满, 生产者线程等待...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            log.debug("已生产消息:{}", message);
            list.notifyAll();   // 多用notifyAll防止虚假唤醒
        }
    }

    // 获取消息
    public Message take() {
        // 检查队列是否为空
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空, 消费者线程只能等待...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            log.debug("已消费消息：{}", message);
            list.notifyAll();
            return message;
        }

    }
}

// 不可继承、没有setter方法, 保证线程安全
final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}