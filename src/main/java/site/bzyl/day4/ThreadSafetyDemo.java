package site.bzyl.day4;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j(topic = "c.ThreadSafetyDemo")
public class ThreadSafetyDemo {
    public static void main(String[] args) {
        List<Integer> list = new Vector<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                list.add(1);
            });
            thread.start();
        }
    }
}

