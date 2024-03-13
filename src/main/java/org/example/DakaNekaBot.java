package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.*;

public class DakaNekaBot extends TelegramLongPollingBot {

    private static final String DATABASE_IMITATION_FOLDER_NAME = "database";
    private static final String QOTES_PATH = DATABASE_IMITATION_FOLDER_NAME + File.separator  + "quotes.csv";
    private static final String FORTUNE_RESPONSE_PATH = DATABASE_IMITATION_FOLDER_NAME + File.separator  + "fortuneResponses.csv";
    // Private data
    private static final String USERNAMES_CSV = "C:/Users/Alex/Documents/usernames.csv";
    private static String BOT_TOKEN;

    private final Random random = new Random();
    Dotenv dotenv = Dotenv.configure().load();
    public DakaNekaBot() {
        BOT_TOKEN = dotenv.get("BOT_TOKEN");
        usernames = new HashSet<>(CSVFileWorker.readLines(USERNAMES_CSV));
        quotes = CSVFileWorker.readLines(QOTES_PATH);
        fortuneResponses = CSVFileWorker.readLines(FORTUNE_RESPONSE_PATH);

        initializeActions();
    }

    private void initializeActions() {
        actions.add(new ActionInfo("Дакалка удали @<username>", "Удаляет пользователя из списка подписчиков"));
        actions.add(new ActionInfo("Дакалка добавь @<username>", "Добавляет пользователя в список подписчиков"));
        actions.add(new ActionInfo("Дакалка запомни @<username>", "Добавляет пользователя в список подписчиков"));
        actions.add(new ActionInfo("Дакалка кого ты...", "Выводит список запомненных подписчиков"));
        actions.add(new ActionInfo("Дакалка как...", "Выводит список на что откликается"));
        actions.add(new ActionInfo("Дакалка <вопрос>?", "Отвечает на вопрос абсолютно точно"));
        actions.add(new ActionInfo("Да или нет", "Поддакивает или неожидано протестует"));
        actions.add(new ActionInfo("Дакалка фразочку", "Выдает базу)"));
    }

    private String chatId;
    private String messageToSend;
    private final List<ActionInfo> actions = new ArrayList<>();
    private final List<String> fortuneResponses;
    private final List<String> quotes;
    private final Set<String> usernames;
    private String currentUsername;

    private boolean isSilent = false;
    //private int silentCounter = 0;


    @Override
    public void onUpdateReceived(Update update) {
        // Обработка полученного обновления
        chatId = update.getMessage().getChatId().toString();

        currentUsername = update.getMessage().getFrom().getUserName();
        String messageText = update.getMessage().getText();

        if (messageText == null || isSilent()) return;

        //CHAT LOG
        System.out.println(currentUsername + ":" + messageText);

        String receivedCommand = lowerCaseWithUsername(messageText);

        handleDakalkaCommand(receivedCommand);

        if (receivedCommand.contains("молчи")) {
            isSilent = true;
            return;
        }

        if ((receivedCommand.startsWith("дакалка") && receivedCommand.endsWith("?")) ) {
            sendMessage(getRandomValueFromList(fortuneResponses));
            return;
        }

        if (receivedCommand.contains("дяя") || receivedCommand.contains("да или нет") || receivedCommand.contains("да?") || receivedCommand.contains("да!") || receivedCommand.contains("нет!") || receivedCommand.contains("нет? ") || receivedCommand.equals("нет") || receivedCommand.equals("да") && usernames.contains(currentUsername)) {

            if (receivedCommand.contains("да?") && currentUsername.equals("Shantazh69") || currentUsername.equals("Helubimaya")) {
                String[] response = {"Да-да, госпожа\uD83D\uDE18",
                        "Дяяяяя, госпожа!❤\uFE0F",
                        "Дя, госпожа!",
                        "Нет, госпожа \uD83E\uDD14",
                        "Нет-Нет, госпожа\uD83D\uDE43",
                        "Нет, госпожа!\uD83D\uDE07"};
                sendMessage(response[random.nextInt(response.length)]);
                return;
            }

            String[] response = {"Да-да\uD83D\uDE18", "Дяяяяя!❤\uFE0F",
                    "Дя!", "Да!", "Нет \uD83E\uDD14", "Нет-Нет\uD83D\uDE43",
                    "Нет!\uD83D\uDE07"};
            sendMessage(response[random.nextInt(response.length)]);
            return;
        }

        if (receivedCommand.startsWith("дакалка фразочк")) {

            sendMessage(getRandomValueFromList(quotes));
            return;
        }

        if (receivedCommand.contains("любит сашу") || receivedCommand.contains("обожает сашу") || receivedCommand.contains("обожнює сашу")) {
            String[] response = {"Да-да\uD83D\uDE18", "Дяяяяя!❤️",
                    "Дя!", "Да!"};
            sendMessage(response[random.nextInt(response.length)]);
            return;
        }

        // Reply Logic
        Message replyToMessage = update.getMessage().getReplyToMessage();
        if (replyToMessage != null && replyToMessage.getFrom() != null && replyToMessage.getFrom().getUserName().equals(getBotUsername())) {
            if (messageText.toLowerCase().contains("молодец") || messageText.toLowerCase().contains("красав")) {
                String[] response = {"Спасибо за похвалу! Это приятно слышать!\uD83D\uDE18",
                        "Спасибки!❤️", "Хих)", "Ня!"};
                sendMessage(response[random.nextInt(response.length)]);
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


    private void handleDakalkaCommand(String receivedCommand) {

//        if (!CSVFileWorker.containsLine(USERNAMES_CSV, currentUsername) && getChatAdministrators() != null && getChatAdministrators().stream().noneMatch(i -> i.getUser().getUserName().equals(currentUsername))) {
//            sendMessage("Ты не из нашей банды!");
//            return;
//        }

        if (receivedCommand.startsWith("дакалка удали @")) {
            String usernameToDelete = extractUsername(receivedCommand);
            handleUsernameDelete(usernameToDelete);

        } else if (receivedCommand.contains("дакалка удали всех")) {
            handleUsernamesClearCommand();

        } else if (receivedCommand.startsWith("дакалка добавь @") || receivedCommand.startsWith("дакалка запомни @")) {
            String usernameToDelete = extractUsername(receivedCommand);
            handleUsernameAddition(usernameToDelete);

        } else if (receivedCommand.startsWith("дакалка кого ты")) {
            handleGetUsernamesCommand();

        } else if (receivedCommand.startsWith("дакалка как")) {
            handleDakalkaInfoCommand();

        }
    }

    private void handleDakalkaInfoCommand() {
        StringBuilder builder = new StringBuilder("Я могу выполнить следующие действия:\n\n");
        for (ActionInfo actionInfo : actions) {
            builder.append(actionInfo.getAction()).append(" - ").append(actionInfo.getDescription()).append("\n");
        }
        sendMessage(builder.toString());
    }

    private void handleGetUsernamesCommand() {
        if (usernames.isEmpty()) {
            sendMessage("Никого!");
            return;
        }

        StringBuilder builder = new StringBuilder("Собственно я запомнил :\n");
        for (String username : usernames) {
            builder.append(username).append("\n");
        }
        builder.append("\n" + "Только им и буду дакать ❤️" + "\n");
        sendMessage(builder.toString());
    }

    private void handleUsernamesClearCommand() {

        if (CSVFileWorker.removeAll(USERNAMES_CSV)) {
            usernames.clear();
            sendMessage("Удачно удалил всех подписчиков!");
        } else {
            sendMessage("Что-то пошло не так, зовите админа!!");
        }

    }

    private void handleUsernameDelete(String usernameToDelete) {
        if (usernameToDelete == null || usernameToDelete.isEmpty()) {
            sendMessage("Пустое имя пользователя!");
            return;
        }

        if (usernames.remove(usernameToDelete)) {
            if (CSVFileWorker.removeLine(USERNAMES_CSV, usernameToDelete)) {
                sendMessage("Удачно своего подписчика!");
            } else {
                sendMessage("Что-то пошло не так, зовите админа!!");
            }
        } else {
            sendMessage("Такого пользователя нет!");
        }
    }

    private void handleUsernameAddition(String usernameToSave) {
        boolean userAlreadyPresent = CSVFileWorker.containsLine(USERNAMES_CSV, usernameToSave);

        if (usernameToSave.isEmpty()) {
            sendMessage("Неверный юзернейм!");
        } else if (userAlreadyPresent) {
            sendMessage("Такой уже есть \uD83D\uDE0A!");
        } else {
            if (CSVFileWorker.writeLine(USERNAMES_CSV, usernameToSave)) {
                usernames.add(usernameToSave);
                sendMessage("Удачно сохранил подписчика! \n Цьом его в пупок");
            } else {
                sendMessage("Что-то пошло не так, зовите админа!!");
            }
        }
    }

    private String getRandomValueFromList(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    private boolean isSilent() {
        if (isSilent) {
            isSilent = false;
            return true;
        }
        return false;
    }

    private void sendMessage(String text) {
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

    private String extractUsername(String receivedCommand) {
        String[] parts = receivedCommand.split("@");
        if (parts.length >= 2) {
            return parts[1].trim();
        } else {
            return null;
        }
    }

    private List<ChatMember> getChatAdministrators() {
        List<ChatMember> administrators = null;
        try {
            GetChatAdministrators getChatAdministrators = new GetChatAdministrators();
            getChatAdministrators.setChatId(chatId);
            administrators = execute(getChatAdministrators);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
