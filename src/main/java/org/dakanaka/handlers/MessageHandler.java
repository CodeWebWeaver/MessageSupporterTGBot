package org.dakanaka.handlers;

import org.dakanaka.action.ActionInfo;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    void handleCommand(ActionInfo receivedAction, String receivedCommand);

    void handleCommand(ActionInfo receivedAction, String receivedCommand, Message reply);
}
