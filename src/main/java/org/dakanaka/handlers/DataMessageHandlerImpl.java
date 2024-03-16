package org.dakanaka.handlers;


import org.dakanaka.DakaNekaBot;
import org.dakanaka.action.ActionInfo;
import org.dakanaka.action.DataActionInfo;
import org.dakanaka.service.DataService;
import org.dakanaka.service.DataServiceImpl;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class DataMessageHandlerImpl implements MessageHandler {
    private final DataService dataService;

    /** Private data START**/
    private static final String USERNAMES_CSV = "C:/Users/Alex/Documents/usernames.csv";
    private static final String DOMINANT_FEMALES_USERNAMES_CSV = "C:/Users/Alex/Documents/dominant_fem_usernames.csv";
    /** Private data END**/
    private final List<ChatMember> admins;
    private final String currentUser;
    private final DakaNekaBot bot;

    public DataMessageHandlerImpl(List<ChatMember> admins, String currentUser, DakaNekaBot bot) {
        dataService = new DataServiceImpl();
        this.admins = admins;
        this.currentUser = currentUser;
        this.bot = bot;
    }

    @Override
    public void handleCommand(ActionInfo receivedDataAction, String receivedCommand) {
        DataActionInfo dataAction = (DataActionInfo) receivedDataAction;
        if (dataAction == null) {
            return;
        }

        String usernameInMessage = extractUsername(receivedCommand);
        switch (dataAction.getDataAction()) {
            case ADD :
                handleDataAddition(USERNAMES_CSV, usernameInMessage);
                break;
            case ADD_DOMINANT_FEMALE :
                handleDataAddition(DOMINANT_FEMALES_USERNAMES_CSV, usernameInMessage);
                break;
            case GET_ALL:
                handleGetAllDataCommand(USERNAMES_CSV);
                break;
            case GET_ALL_DOMINANT_FEMALES:
                handleGetAllDataCommand(DOMINANT_FEMALES_USERNAMES_CSV);
                break;
            case REMOVE:
                handleDataDelete(USERNAMES_CSV, usernameInMessage);
                break;
            case REMOVE_DOMINANT_FEMALES:
                handleDataDelete(DOMINANT_FEMALES_USERNAMES_CSV, usernameInMessage);
                break;
            case REMOVE_ALL:
                handleDataClearCommand(USERNAMES_CSV);
                break;
            default:
        }
    }

    @Override
    public void handleCommand(ActionInfo receivedDataAction, String receivedCommand, Message reply) {

    }

    public void handleDataAddition(String path, String usernameToSave) {
        if (!isUserHaveRights(currentUser)) {
            bot.sendMessage("Прости, но я тебя не знаю");
            return;
        }
        if (checkDataEmpty(usernameToSave)) return;
        boolean userAlreadyPresent = dataService.containsData(path, usernameToSave);

        if (usernameToSave.isEmpty()) {
            bot.sendMessage("Неверный юзернейм!");
        } else if (userAlreadyPresent) {
            bot.sendMessage("Такой уже есть \uD83D\uDE0A!");
        } else {
            if (dataService.saveData(path, usernameToSave)) {
                bot.sendMessage("Удачно сохранил! \n Будем знакомы ❤️");
            } else {
                bot.sendMessage("Что-то пошло не так, зовите админа!!");
            }
        }
    }

    private void handleDataDelete(String path, String usernameToDelete) {
        if (!isUserHaveRights(currentUser)) return;
        if (checkDataEmpty(usernameToDelete)) return;

        if (dataService.containsData(path, usernameToDelete)) {
            if (dataService.deleteData(path, usernameToDelete)) {
                bot.sendMessage("Удачно удалил из списка!");
            } else {
                bot.sendMessage("Что-то пошло не так, зовите админа!!");
            }
        } else {
            bot.sendMessage("Такого пользователя нет!");
        }
    }

    private void handleDataClearCommand(String path) {
        if (!isUserHaveRights(currentUser)) return;

        if (dataService.deleteAllData(path)) {
            bot.sendMessage("Удачно удалил всех из списка!");
        } else {
            bot.sendMessage("Что-то пошло не так, зовите админа!!");
        }
    }

    private void handleGetAllDataCommand(String path) {
        List<String> lines = dataService.getAllData(path);

        if (lines.isEmpty()) {
            bot.sendMessage("Никого!");
            return;
        }

        StringBuilder builder = new StringBuilder("Собственно я запомнил :\n\n");
        for (String username : lines) {
            builder.append(username).append("\n");
        }
        builder.append("\n" + "Только им и буду дакать ❤️" + "\n");
        bot.sendMessage(builder.toString());
    }

    public boolean isUserHaveRights(String username) {
        boolean userInList = dataService.containsData(USERNAMES_CSV, username);
        if (admins != null) {
            boolean isAdmin = admins.stream()
                    .anyMatch(i -> i.getUser().getUserName() != null && i.getUser().getUserName().equals(username));
            return userInList || isAdmin;
        } else {
            return userInList;
        }
    }

    private boolean checkDataEmpty(String usernameToDelete) {
        if (usernameToDelete == null || usernameToDelete.isEmpty()) {
            bot.sendMessage("Пустое имя пользователя!");
            return true;
        }
        return false;
    }
    private String extractUsername(String receivedCommand) {
        String[] parts = receivedCommand.split("@");
        if (parts.length >= 2) {
            return parts[1].trim();
        } else {
            return null;
        }
    }

}
