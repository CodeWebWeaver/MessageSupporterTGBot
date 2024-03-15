package org.dakanaka.handlers;

import org.dakanaka.DakaNekaBot;
import org.dakanaka.action.ActionInfo;
import org.dakanaka.action.CommunicationActionInfo;
import org.dakanaka.enums.CommunicationAction;
import org.dakanaka.service.DataService;
import org.dakanaka.service.DataServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.util.List;
import java.util.Random;

public class CommunicationMessageHandler implements MessageHandler {
    private final DataService dataService;
    private final DakaNekaBot bot;
    private final String currentUsername;
    private List<String> dominantFemales;
    private final Random random = new Random();
    private final List<ActionInfo> actions;
    private final List<String> fortuneResponses;
    private final List<String> quotes;
    private static final String DATABASE_IMITATION_FOLDER_NAME = "database";
    private static final String QUOTES_PATH = DATABASE_IMITATION_FOLDER_NAME + File.separator  + "quotes.csv";
    private static final String FORTUNE_RESPONSE_PATH = DATABASE_IMITATION_FOLDER_NAME + File.separator  + "fortuneResponses.csv";
    private final String[] loveResponses = {"Да-да\uD83D\uDE18", "Дяяяяя!❤️",
            "Дя!", "Да!"};
    private final String[] yesNoResponses = {"Да-да\uD83D\uDE18", "Дяяяяя!❤️",
            "Дя!", "Да!", "Нет \uD83E\uDD14", "Нет-Нет\uD83D\uDE43",
            "Нет!\uD83D\uDE07"};
    private final String[] slaveResponses = {"Да-да, госпожа\uD83D\uDE18",
            "Дяяяяя, госпожа!❤️",
            "Дя, госпожа!",
            "Нет, госпожа \uD83E\uDD14",
            "Нет-Нет, госпожа\uD83D\uDE43",
            "Нет, госпожа!\uD83D\uDE07"};
    private final String[] appreciateResponses = {"Спасибо за похвалу! Это приятно слышать!\uD83D\uDE18",
            "Спасибки!❤️", "Хих)", "Ня!"};


    public CommunicationMessageHandler(List<ActionInfo> actions, String currentUsername, DakaNekaBot bot) {
        dataService = new DataServiceImpl();
        this.actions = actions;
        this.bot = bot;
        this.currentUsername = currentUsername;
        quotes = dataService.getAllData(QUOTES_PATH);
        fortuneResponses = dataService.getAllData(FORTUNE_RESPONSE_PATH);
    }

    @Override
    public void handleCommand(ActionInfo receivedDataAction, String receivedCommand) {
        CommunicationActionInfo communicationAction = (CommunicationActionInfo) receivedDataAction;
        if (communicationAction == null) {
            return;
        }

        switch (communicationAction.getCommunicationAction()) {
            case INFO:
                handleDakalkaInfoCommand();
                break;
            case YES_NO:
                if (dominantFemales.stream().anyMatch(i -> i.equals(currentUsername))) {
                    sendRandomResponse(slaveResponses);
                } else {
                    sendRandomResponse(yesNoResponses);
                }
                break;
            case QUOTE:
                sendRandomResponse(quotes);
                break;
            case FUTURE_RESPONSE:
                sendRandomResponse(fortuneResponses);
                break;
            case YES_NO_LOVE:
                sendRandomResponse(loveResponses);
                break;
            default:
        }
    }

    @Override
    public void handleCommand(ActionInfo receivedDataAction, String receivedCommand, Message reply) {

        CommunicationActionInfo communicationAction = (CommunicationActionInfo) receivedDataAction;
        if (communicationAction == null) {
            return;
        }
        if (communicationAction.getCommunicationAction().equals(CommunicationAction.APPRECIATION_RESPONSE)) {
            if (reply != null) {
                sendRandomResponse(appreciateResponses);
            }
        }
    }

    private void sendRandomResponse(String[] responses) {
        bot.sendMessage(responses[random.nextInt(responses.length)]);
    }
    private void handleDakalkaInfoCommand() {
        StringBuilder builder = new StringBuilder("Я могу выполнить следующие действия:\n\n");
        for (ActionInfo actionInfo : actions) {
            builder.append(actionInfo.getAction()).append(" - ").append(actionInfo.getDescription()).append("\n");
        }
        bot.sendMessage(builder.toString());
    }
    private String getRandomValueFromList(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    private void sendRandomResponse(List<String> responses) {
        bot.sendMessage(getRandomValueFromList(responses));
    }
}
