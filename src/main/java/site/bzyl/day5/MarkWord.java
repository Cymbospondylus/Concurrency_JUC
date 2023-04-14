package site.bzyl.day5;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "c.MarkWord")
public class MarkWord {
    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.println(dog.hashCode());
    }
}

class Dog {

}
