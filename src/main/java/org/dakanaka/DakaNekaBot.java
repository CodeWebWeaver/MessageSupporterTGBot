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

    public DakaNekaBot() {
        BOT_TOKEN = Dotenv.configure().load().get("BOT_TOKEN");
        initializeActions();
    }

    private void initializeActions() {
        actions.add(new DataActionInfo("Дакалка удали @<username>",
                "Удаляет пользователя из списка подписчиков",
                List.of("^Дакалка удали @\\S+$",
                        "^Дакалка удали @.*",
                        "^Дакалка забудь @.*",
                "^Дакалка выкинь @.*"),
                DataAction.REMOVE
        ));
        actions.add(new DataActionInfo("Дакалка добавь/запомни @<username>",
                "Добавляет пользователя в список подписчиков",
                List.of("^Дакалка добавь @\\S+$",
                        "^Дакалка запомни @\\S+$",
                        "^Дакалка запомни @.*"
                        ),
                DataAction.ADD
        ));
        actions.add(new DataActionInfo("Дакалка кого ты...",
                "Выводит список запомненных подписчиков",
                List.of("^Дакалка кого ты.*"),
                DataAction.GET_ALL
        ));
        actions.add(new CommunicationActionInfo("Дакалка инфо",
                "Выводит список на что откликается",
                List.of("^Дакалка инф.*"),
                CommunicationAction.INFO
        ));
        actions.add(new CommunicationActionInfo("Дакалка <вопрос>?",
                "Отвечает на вопрос абсолютно точно",
                List.of("^Дакалка .+\\?$", "^Дакалка .*\\?$"),
                CommunicationAction.FUTURE_RESPONSE
        ));
        actions.add(new CommunicationActionInfo(
                "Да или нет",
                "Поддакивает или неожидано протестует",
                List.of("\\b[Дд]а\\s*(или)?\\s*[Нн]ет\\?\\b",
                        "\\bНет\\?\\b",
                        "\\bДа\\?\\b",
                        "^Да\\?$",
                        "^Нет\\?$",
                        "\\b(?i)Нет\\?\\b",
                        "\\b(?i)Да\\?\\b",
                        "(?i)(да|нет)\\?(?!\\.)",
                        "(?i)(?<=\\W|^)(да\\s+или\\s+нет)\\?(?=\\W|$)",
                        "\\b(?i)[Дд]а\\s*(или)?\\s*[Нн]ет\\?\\b",
                        "(?i)^да\\s+или\\s+нет\\?$",
                        "^(да|нет)\\??(?<!\\.)(?=\\.|\\?|$)|(дяя)(\\.|\\?|$)"),

                CommunicationAction.YES_NO
        ));
        actions.add(new CommunicationActionInfo("Дакалка фразочку",
                "Выдает базу)",
                List.of("^Дакалка фразочку.*",
                        "^Дакалка фраз.*",
                        "^Дакалка выдай базу.*",
                        "^Дакалка базу.*"),
                CommunicationAction.QUOTE
        ));

        actions.add(new CommunicationActionInfo("Любит ли кто-то кого-то",
                "Расказывает правду",
                List.of(".*\\b(любит сашу|обожает сашу|обожнює сашу)\\b.*",
                        ".*\\b(любит Сашу|обожает Сашу|обожнює Сашу)\\b.*"),
                CommunicationAction.YES_NO_LOVE
        ));

        actions.add(new CommunicationActionInfo("молодец или красава в ответ",
                "Отвечает на похвалу",
                List.of("(?i).*\\b(молодец|красав.*)\\b.*",
                        "(?i).*\\b(Молодец|Красав.*)\\b.*"),
                CommunicationAction.APPRECIATION_RESPONSE
        ));

        actions.add(new DataActionInfo("Дакалка припоминай доминаторшу",
                "Запоминает доминаторшу",
                List.of("^Дакалка припоминай доминаторшу @.*",
                        "^Дакалка добавь доминаторшу @.*",
                        "^Дакалка запомни доминаторшу @.*"),
                DataAction.ADD_DOMINANT_FEMALE));
        actions.add(new DataActionInfo("Дакалка забудь доминаторшу",
                "Забывет доминаторшу",
                List.of("^Дакалка удали доминаторшу @.*",
                "^Дакалка забудь доминаторшу @.*"),
                DataAction.REMOVE_DOMINANT_FEMALES));
        actions.add(new DataActionInfo("Дакалка покажи доминаторш",
                "Выводит доминаторш",
                List.of("^Дакалка покажи доминаторш.*"),
                DataAction.GET_ALL_DOMINANT_FEMALES));
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
        List<ChatMember> admins = getChatAdministrators();

        String currentUsername = update.getMessage().getFrom().getUserName();
        String messageText = update.getMessage().getText();

        if (messageText == null || isSilent()) return;

        //CHAT LOG
        System.out.println(currentUsername + ":" + messageText);

        if (messageText.contains("молчи")) {
            isSilent = true;
        }

        // Data handler
        Optional<ActionInfo> anyDataAction = actions.stream()
                .filter(action -> action.matchesPattern(messageText) && action instanceof DataActionInfo)
                .findAny();
        if(anyDataAction.isPresent()) {
            MessageHandler dataMessageHandler = new DataMessageHandlerImpl(admins, currentUsername, this);
            dataMessageHandler.handleCommand(anyDataAction.get(), messageText);
        } else  {
            Message replyToMessage = update.getMessage().getReplyToMessage();
            // Communication handler
            Optional<ActionInfo> anyCommunicationAction = actions.stream()
                    .filter(action -> action.matchesPattern(messageText) && action instanceof CommunicationActionInfo)
                    .findAny();
            if(anyCommunicationAction.isPresent()) {
                MessageHandler communicationMessageHandler = new CommunicationMessageHandler(actions, currentUsername, this);
                if (replyToMessage != null) {
                    communicationMessageHandler.handleCommand(anyCommunicationAction.get(), messageText, replyToMessage);
                } else {
                    communicationMessageHandler.handleCommand(anyCommunicationAction.get(), messageText);
                }
            }
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
