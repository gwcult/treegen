package com.github.gwcult.treegen;

import com.github.gwcult.treegen.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Utils {
    private static final Predicate<String> isInteger = Pattern.compile("\\d+").asPredicate();

    public static List<Node> getAncestors(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<Node> ancestors = getAncestors(node.getParent());
        ancestors.add(node);
        return ancestors;
    }

    public static String getPath(Node node) {
        return getAncestors(node)
                .stream()
                .map(Node::getName)
                .reduce("", Utils::joinPathElements);
    }

    private static String joinPathElements(String prev, String curr) {
        if (isInteger.test(curr)) {
            return prev + "[" + curr + "]";
        } else if (!prev.isEmpty()) {
            prev += ".";
        }
        return prev + curr;
    }
}
