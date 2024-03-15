package org.dakanaka.repo_imitation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public String readLine(String filePath, int index) {
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                if (line != null && counter == index) {
                    break;
                }
                counter++;
            }
        } catch (IOException exception) {
            System.out.println("Cant read from : " + filePath);
        }
        return line;
    }

    public List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException exception) {
            System.out.println("Cant read from : " + filePath);
        }
        return lines;
    }

    public boolean writeLines(String filePath, List<String> lines) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : lines) {
                writer.append(line).append("\n");
            }
            return true;
        } catch (IOException exception) {
            System.out.println("Cant write to : " + filePath);
            return false;
        }
    }

    public boolean containsLine(String filePath, String lineToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(lineToFind)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Can't check for line in file: " + filePath);
        }
        return false;
    }

    public boolean writeLine(String filePath, String line) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(line).append("\n");
            return true;
        } catch (IOException exception) {
            System.out.println("Cant append line at: " + filePath);
            return false;
        }
    }

    public boolean removeAll(String filePath) {
        try (FileWriter ignored = new FileWriter(filePath)) {
            // Не записываем никаких данных, файл будет перезаписан пустым содержимым
            return true;
        } catch (IOException exception) {
            System.out.println("Cant delete all at : " + filePath);
            return false;
        }
    }

    public boolean removeLine(String filePath, String lineToRemove) {
        List<String> lines = new ArrayList<>();
        boolean lineFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(lineToRemove)) {
                    lineFound = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException exception) {
            System.out.println("Cant remove from : " + filePath + "\n"
                    + "Trying to remove:" + lineToRemove);
            return false;
        }

        if (lineFound) {
            return writeLines(filePath, lines);
        } else {
            return lineFound;
        }
    }

    public void createNewFileAtFolder(String folder, String filename) {
        String currentDirectory = System.getProperty("user.dir");

        // Відносний шлях до папки "database" в поточному каталозі
        String databaseFolderPath = currentDirectory + File.separator + folder;

        // Створення об'єкту File для папки "database"
        File databaseFolder = new File(databaseFolderPath);

        // Створення папки "database" та перевірка, чи вона була створена успішно
        if (!databaseFolder.exists()) {
            boolean folderCreated = databaseFolder.mkdirs();
            if (folderCreated) {
                System.out.println("Папка 'database' була успішно створена.");
            } else {
                System.out.println("Помилка при створенні папки " + folder  + ".");
                return; // Вийти з програми, якщо створення папки не вдалося
            }
        } else {
            System.out.println("Папка '" + folder  + "' вже існує.");
        }
        // Відносний шлях до файлу в папці "database"
        String namesFilePath = databaseFolderPath + File.separator + filename;

        // Створення об'єкту File для файлу "names.csv"
        File namesFile = new File(namesFilePath);
        // Створення файлу "names.csv" та перевірка, чи він був створений успішно
        try {
            boolean fileCreated = namesFile.createNewFile();
            if (fileCreated) {
                System.out.println("Файл '" +  filename + "' був успішно створений.");
            } else {
                System.out.println("Новий файл не був створений '" +  filename + "'.");
            }
        } catch (IOException e) {
            System.out.println("Виникла помилка при спробі створити файл '" +  filename + "': " + e.getMessage());
        }
    }
}
