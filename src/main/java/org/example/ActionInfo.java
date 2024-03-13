package org.example;

public class ActionInfo {
    private final String action;
    private final String description;

    public ActionInfo(String action, String description) {
        this.action = action;
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }
}
