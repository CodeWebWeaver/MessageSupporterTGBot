package org.dakanaka.action;

import java.util.List;
import java.util.regex.Pattern;

public abstract class ActionInfo {
    private final String action;
    private final String description;
    private final List<String> patterns;

    public ActionInfo(String action, String description, List<String> patterns) {
        this.action = action;
        this.description = description;
        this.patterns = patterns;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    // Абстрактный метод для проверки подходит ли строка под паттерн действия
    public boolean matchesPattern(String input) {
        return patterns.stream()
                .anyMatch(i -> Pattern.compile(i, Pattern.CASE_INSENSITIVE)
                        .matcher(input)
                        .matches());
    }
}
