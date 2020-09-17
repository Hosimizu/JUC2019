package com.lzy.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Integer id;
    private String userName;
    private int age;
}

/**
 * @create 2019-02-26 22:24
 * <p>
 * 题目：请按照给出数据，找出同时满足
 * 偶数ID且年龄大于24且用户名转为大写且用户名字母倒排序
 * 最后只输出一个用户名字
 */
public class StreamDemo {
    public static void main(String[] args) {
        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 22);
        User u4 = new User(14, "d", 28);
        User u5 = new User(16, "e", 26);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        list.stream().filter(p -> {
            return p.getId() % 2 == 0;
        }).filter(p -> {
            return p.getAge() > 24;
        }).map(f -> {
            return f.getUserName().toUpperCase();
        }).sorted((o1, o2) -> {
            return o2.compareTo(o1);
        }).limit(1).forEach(System.out::println);


        //    R apply(T t);
        Function<String, Integer> function = t -> {
            return t.length();
        };
        System.out.println(function.apply("abc"));

        // boolean test(T t);
        Predicate<String> predicate = t -> {
            return t.startsWith("a");
        };
        System.out.println(predicate.test("a"));

        //void accept(T t);
        Consumer<String> consumer = t -> {
            System.out.println(t);
        };
        consumer.accept("java1018");


        //    T get();
        Supplier<String> supplier = () -> {
            return UUID.randomUUID().toString();
        };
        System.out.println(supplier.get());
        ;

    }
}

