package org.dakanaka.action;

import org.dakanaka.enums.DataAction;

import java.util.List;

public class DataActionInfo extends ActionInfo {
    private final DataAction dataAction;
    public DataActionInfo(String action, String description, List<String> patterns, DataAction dataAction) {
        super(action, description, patterns);
        this.dataAction = dataAction;
    }
    // Можно добавить дополнительные методы или поля, специфичные для действий с базой данных

    public DataAction getDataAction() {
        return dataAction;
    }
}