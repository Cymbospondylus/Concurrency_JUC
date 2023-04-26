package site.bzyl.day16;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@Slf4j(topic = "c.AtomicFieldUpdater")
public class AtomicFieldUpdater {
    public static void main(String[] args) {
        Student student = new Student();

        AtomicReferenceFieldUpdater updater
                = AtomicReferenceFieldUpdater
                .newUpdater(Student.class, String.class, "name");

        boolean result = updater.compareAndSet(student, null, "张三");
        System.out.println(result);
        System.out.println(student.name);


    }
}

class Student {
    public volatile String name;


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
