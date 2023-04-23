package site.bzyl.day13;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "OptionalDemo")
public class OptionalDemo {
    public static void main(String[] args) {
        Author author = getAuthor();
        System.out.println(author.getName());
    }

    public static Author getAuthor() {
        Author author = new Author(1L, "杯椎鱼龙", 21, "你来到了没有知识的荒原", new ArrayList<>());
        return author;
    }
}
@Data
@AllArgsConstructor
@ToString
class Author {
    private Long id;
    private String name;
    private Integer age;
    private String intro;
    private List<String> book;
}
