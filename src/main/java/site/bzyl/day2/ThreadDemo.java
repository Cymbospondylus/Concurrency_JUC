package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.nio.CharBuffer;

@Slf4j(topic = "c.ThreadDemo")
public class ThreadDemo {
    public static void main(String[] args) {
      Thread t1 = new Thread("t1") {
          @Override
          public void run() {
              try {
                  Thread.sleep(5000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              log.debug("running...");
          }
      };

        System.out.println(t1.getState());

        t1.start();

        System.out.println(t1.getState());

        log.debug("do other thing");
    }
}
