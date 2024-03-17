package org.dakanaka;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class REgex {
    public static void main(String[] args) {
        List<String> patterns = Arrays.asList(
                "\\b[Дд][аА][?!](\\?|!)",
                "\\b[Дд][аА][?!].*[!?]$",
                "\\b[Нн][еЕ][тТ](\\?|!)",
                "\\b[Нн][еЕ][тТ].*[!?]$",
                "Дяя*$"
        );

        String input = "откуда?";

        boolean anyMatch = patterns.stream()
                .anyMatch(pattern -> {
                    Pattern compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                    return compile.matcher(input).find();
                });

        System.out.println(anyMatch);  // Должно вывести true
    }
}
