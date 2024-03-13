package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.CSVFileWorker.createNewFileAtFolder;

public class databaseFiller {

    public static void main(String[] args) {
        final List<String> fortuneResponses = new ArrayList<>();
        fortuneResponses.add("Вероятно.");
        fortuneResponses.add("Похоже на то.");
        fortuneResponses.add("Сложно сказать.");
        fortuneResponses.add("Безусловно!");
        fortuneResponses.add("Никаких сомнений.");
        fortuneResponses.add("Да, это так.");
        fortuneResponses.add("Положительно.");
        fortuneResponses.add("Возможно.");
        fortuneResponses.add("Пока не ясно, попробуйте еще раз.");
        fortuneResponses.add("Спросите позже.");
        fortuneResponses.add("Лучше не рассказывать.");
        fortuneResponses.add("Сейчас нельзя предсказать.");
        fortuneResponses.add("Сконцентрируйтесь и спросите снова.");
        fortuneResponses.add("Мой ответ - нет.");
        fortuneResponses.add("Может быть.");
        fortuneResponses.add("Я считаю, что да.");
        fortuneResponses.add("Мне кажется - да.");
        fortuneResponses.add("Маловероятно.");
        fortuneResponses.add("Слишком рано сказать.");
        fortuneResponses.add("Весьма сомнительно.");
        fortuneResponses.add("Не могу дать точный ответ.");
        fortuneResponses.add("Можете рассчитывать на это.");
        fortuneResponses.add("Ответ отрицательный.");

        String filename = "fortuneResponses.csv";
        createNewFileAtFolder("database", filename);
        String path = "database" + File.separator + filename;
        CSVFileWorker.writeLines(path, fortuneResponses);

        List<String> strings = CSVFileWorker.readLines(path);
    }
}
