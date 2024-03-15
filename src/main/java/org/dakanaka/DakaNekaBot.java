package org.dakanaka;

import io.github.cdimascio.dotenv.Dotenv;
import org.dakanaka.action.ActionInfo;
import org.dakanaka.action.CommunicationActionInfo;
import org.dakanaka.action.DataActionInfo;
import org.dakanaka.enums.CommunicationAction;
import org.dakanaka.enums.DataAction;
import org.dakanaka.handlers.CommunicationMessageHandler;
import org.dakanaka.handlers.DataMessageHandlerImpl;
import org.dakanaka.handlers.MessageHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DakaNekaBot extends TelegramLongPollingBot {

    private static String BOT_TOKEN;
    private final List<ChatMember> admins;

    public DakaNekaBot() {
        admins = getChatAdministrators();
        BOT_TOKEN = Dotenv.configure().load().get("BOT_TOKEN");
        initializeActions();
    }

    private void initializeActions() {
        actions.add(new DataActionInfo("Дакалка удали @<username>",
                "Удаляет пользователя из списка подписчиков",
                List.of("^Дакалка удали @\\S+$"),
                DataAction.REMOVE
        ));
        actions.add(new DataActionInfo("Дакалка добавь/запомни @<username>",
                "Добавляет пользователя в список подписчиков",
                List.of("^Дакалка добавь @\\S+$", "^Дакалка запомни @\\S+$"),
                DataAction.ADD
        ));
        actions.add(new DataActionInfo("Дакалка кого ты...",
                "Выводит список запомненных подписчиков",
                List.of("^Дакалка кого ты.*"),
                DataAction.GET_ALL
        ));

        actions.add(new CommunicationActionInfo("Дакалка как...",
                "Выводит список на что откликается",
                List.of("^Дакалка как.*", "^Дакалка инф.*"),
                CommunicationAction.INFO
        ));
        actions.add(new CommunicationActionInfo("Дакалка <вопрос>?",
                "Отвечает на вопрос абсолютно точно",
                List.of("^Дакалка .+\\?$"),
                CommunicationAction.FUTURE_RESPONSE
        ));
        actions.add(new CommunicationActionInfo(
                "Да или нет",
                "Поддакивает или неожидано протестует",
                List.of("^Дакалка .+\\?$"),
                CommunicationAction.YES_NO
        ));
        actions.add(new CommunicationActionInfo("Дакалка фразочку",
                "Выдает базу)",
                List.of("\\b[Дд]а\\s*(или)?\\s*[Нн]ет\\?\\b", "\\bНет\\?\\b", "\\bДа\\?\\b"),
                CommunicationAction.QUOTE
        ));

        actions.add(new CommunicationActionInfo("Любит ли кто-то кого-то",
                "Расказывает правду",
                List.of(".*\\b(любит сашу|обожает сашу|обожнює сашу)\\b.*"),
                CommunicationAction.YES_NO_LOVE
        ));

        actions.add(new CommunicationActionInfo("молодец или красава в ответ",
                "Отвечает на похвалу",
                List.of(".*\\b(молодец|красав)\\b.*"),
                CommunicationAction.APPRECIATION_RESPONSE
        ));
    }

    private String chatId;
    private String messageToSend;
    private final List<ActionInfo> actions = new ArrayList<>();

    private boolean isSilent = false;
    //private int silentCounter = 0;


    @Override
    public void onUpdateReceived(Update update) {
        // Обработка полученного обновления
        chatId = update.getMessage().getChatId().toString();

        String currentUsername = update.getMessage().getFrom().getUserName();
        String messageText = update.getMessage().getText();

        if (messageText == null || isSilent()) return;

        //CHAT LOG
        System.out.println(currentUsername + ":" + messageText);

        String receivedCommand = lowerCaseWithUsername(messageText);

        if (receivedCommand.contains("молчи")) {
            isSilent = true;
        }

        // Data handler
        Optional<ActionInfo> anyDataAction = actions.stream()
                .filter(action -> action.matchesPattern(receivedCommand) && action instanceof DataActionInfo)
                .findAny();
        if(anyDataAction.isPresent()) {
            MessageHandler dataMessageHandler = new DataMessageHandlerImpl(admins, currentUsername, this);
            dataMessageHandler.handleCommand(anyDataAction.get(), receivedCommand);
        }

        Message replyToMessage = update.getMessage().getReplyToMessage();
        // Communication handler
        Optional<ActionInfo> anyCommunicationAction = actions.stream()
                .filter(action -> action.matchesPattern(receivedCommand) && action instanceof CommunicationActionInfo)
                .findAny();
        if(anyCommunicationAction.isPresent()) {
            MessageHandler communicationMessageHandler = new CommunicationMessageHandler(actions, currentUsername, this);
            if (replyToMessage != null) {
                communicationMessageHandler.handleCommand(anyCommunicationAction.get(), receivedCommand, replyToMessage);
            } else {
                communicationMessageHandler.handleCommand(anyCommunicationAction.get(), receivedCommand);
            }
        }
    }

    public String lowerCaseWithUsername(String messageText) {
        int atIndex = messageText.indexOf('@');
        if (atIndex != -1) {
            String beforeAt = messageText.substring(0, atIndex); // Сохраняем текст до символа '@'
            String afterAt = messageText.substring(atIndex); // Сохраняем текст после символа '@' без изменений
            return beforeAt.toLowerCase() + afterAt;
        } else {
            return messageText.toLowerCase(); // Если символ '@' не найден, преобразовываем всю строку в нижний регистр
        }
    }

    private boolean isSilent() {
        if (isSilent) {
            isSilent = false;
            return true;
        }
        return false;
    }

    public void sendMessage(String text) {
        System.out.println("Message to send: " + messageToSend);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            this.execute(sendMessage);
            messageToSend = null;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ChatMember> getChatAdministrators() {
        List<ChatMember> administrators = null;
        try {
            GetChatAdministrators getChatAdministrators = new GetChatAdministrators();
            getChatAdministrators.setChatId(chatId);
            administrators = execute(getChatAdministrators);
        } catch (TelegramApiException e) {
            System.out.println("Error during gathering admins");
        }
        return administrators;
    }

    @Override
    public String getBotUsername() {
        // Уникальное имя вашего бота
        return "DakaNekaBot";
    }

    @Override
    public String getBotToken() {
        // Токен вашего бота
        return BOT_TOKEN;
    }
}
