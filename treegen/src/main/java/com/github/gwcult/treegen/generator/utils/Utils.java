package com.github.gwcult.treegen.generator.utils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Utils {
    public static Pattern getterPattern = Pattern.compile("^get([A-Z])(.*)");

    @SafeVarargs
    public static <X> X firstNonNull(Supplier<X>... suppliers) {
        Stream<Supplier<X>> supplierStream = Stream.of(suppliers);
        return supplierStream.map(Supplier::get).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static String getterToFieldName(String getter) {
        Matcher matcher = getterPattern.matcher(getter);
        matcher.matches();
        return matcher.group(1).toLowerCase() + matcher.group(2);
    }

    public static boolean isGetter(ExecutableElement element) {
        String name = element.getSimpleName().toString();
        return getterPattern.asPredicate().test(name) &&
                element.getParameters().size() == 0 &&
                element.getModifiers().contains(Modifier.PUBLIC);
    }

    public static String importOrQualified(List<String> imports, String element) {
        String shortcut = lastImportToken(element);
        Optional<String> duplicate = imports.stream().filter(i -> i.endsWith("." + shortcut)).findAny();
        if (duplicate.isPresent()) {
            if (duplicate.get().equals(element)) {
                return shortcut;
            }
            return element;
        }
        imports.add(element);
        return shortcut;
    }

    public static String lastImportToken(String element) {
        String[] tokens = element.split("\\.");
        return tokens[tokens.length - 1];
    }

}
