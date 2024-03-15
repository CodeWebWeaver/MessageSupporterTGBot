package org.dakanaka.action;

import org.dakanaka.enums.CommunicationAction;

import java.util.List;

public class CommunicationActionInfo extends ActionInfo {
    private final CommunicationAction communicationAction;
    public CommunicationActionInfo(String action, String description, List<String> patterns, CommunicationAction communicationAction) {
        super(action, description, patterns);
        this.communicationAction = communicationAction;
    }

    // Можно добавить дополнительные методы или поля, специфичные для действий общения

    public CommunicationAction getCommunicationAction() {
        return communicationAction;
    }
}