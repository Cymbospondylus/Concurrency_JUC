package site.bzyl.day12;

import lombok.extern.slf4j.Slf4j;

import java.util.function.IntConsumer;

@Slf4j(topic = "c.LambdaExpression")
public class LambdaExpression {
    public static void main(String[] args) {

        foreachArr((int i)-> System.out.println(i));
    }

    public static void foreachArr(IntConsumer consumer) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            consumer.accept(i);
        }
    }
}
