package com.github.gwcult.treegen.example;

import com.github.gwcult.treegen.TreeGen;
import lombok.Data;
import lombok.Getter;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@TreeGen
@Data
public class User {
    private String username = "Username";
    private BigInteger age = BigInteger.valueOf(11);
    private int size = 11;
    private Group group = new Group();
    private SubUser subUser = new SubUser();
    @Getter(lazy = true)
    private final List<User> friends = Arrays.asList(new User(), new User());
    private List<List<String>> tasks = Arrays.asList(Arrays.asList(
            "task11",
            "task12"
    ), Arrays.asList(
            "task21",
            "task22"
    ));

    @TreeGen
    @Data
    public static class SubUser {
        private String name = "SubUserName";
    }
}


