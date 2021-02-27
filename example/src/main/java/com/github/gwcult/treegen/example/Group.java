package com.github.gwcult.treegen.example;

import com.github.gwcult.treegen.TreeGen;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@TreeGen
@Data
public class Group {
    private String name = "GroupName";
    @Getter(lazy = true)
    private final List<User> members = Arrays.asList(new User(), new User());
}
