package com.github.gwcult.treegen.example;

import com.github.gwcult.treegen.TreeGen;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@TreeGen
public class User {
    public String getUsername() {
        return "username";
    }

    public BigInteger getAge() {
        return BigInteger.valueOf(11);
    }

    public int getSize() {
        return 11;
    }

    public Group getGroup() {
        return new Group();
    }

    public SubUser getSubUser() {
        return new SubUser();
    }

    public List<List<String>> getEmails() {
        return Arrays.asList(Arrays.asList(
                "a",
                "b"
        ), Arrays.asList(
                "c",
                "d"
        ));
    }

    @TreeGen
    public static class SubUser {
        public String getName() {
            return "XD";
        }
    }
}


