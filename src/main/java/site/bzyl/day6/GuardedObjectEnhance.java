package site.bzyl.day6;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import static site.bzyl.util.Sleeper.mySleep;

public class GuardedObjectEnhance {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        mySleep(1);
        for (Integer id : MailBoxes.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }
}
@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id：{}， 内容:{}", guardedObject.getId(), mail);
    }
}
@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int mailId;
    private String mailContent;

    public Postman(int mailId, String mailContent) {
        this.mailId = mailId;
        this.mailContent = mailContent;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(mailId);
        log.debug("开始送信 id：{}, 内容:{}", mailId, mailContent);
        guardedObject.complete(mailContent);
    }
}

class MailBoxes {
    private static Map<Integer, GuardedObject> mailBoxes = new Hashtable<>();

    private static int id = 1;
    // 产生唯一id
    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject getGuardedObject(int id) {
        // map.remove 比 map.get多了一个删除的效果
        return mailBoxes.remove(id);
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject guardedObject = new GuardedObject(generateId());
        mailBoxes.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }
    // HashTable线程安全, 所以方法不需要synchronized
    public static Set<Integer> getIds() {
        return mailBoxes.keySet();
    }
}

class GuardedObject {
    private int id;
    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 参照join()方法的源码
    public synchronized Object get(long timeout) {
        // 记录等待的开始时间
        long beginTime = System.currentTimeMillis();
        long passedTime = 0;
        while (response == null) {
            long waitTime = timeout - passedTime;
            if (waitTime <= 0) break;
            try {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            passedTime = System.currentTimeMillis() - beginTime;
        }
        return response;
    }

    public synchronized void complete(Object response) {
        this.response = response;
        this.notifyAll();
    }
}
