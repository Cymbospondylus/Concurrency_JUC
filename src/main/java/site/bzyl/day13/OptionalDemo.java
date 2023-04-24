package site.bzyl.day13;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j(topic = "OptionalDemo")
public class OptionalDemo {
    public static void main(String[] args) {
        Optional<Author> authorOptional = getAuthorOptional();
        System.out.println(authorOptional.orElseThrow(() -> new  RuntimeException("空！")));
        /*authorOptional.ifPresent(author -> System.out.println(author.getName()));*/

    }

    public static Optional<Author> getAuthorOptional() {
        Author author = new Author(1L, "杯椎鱼龙", 21, "你来到了没有知识的荒原", new ArrayList<>());
        return Optional.ofNullable(author);
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Author {
    private Long id;
    private String name;
    private Integer age;
    private String intro;
    private List<String> book;
}
