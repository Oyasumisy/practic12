import java.util.Scanner;
import java.io.*;

public class Golybochek {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Записати до файлу");
            System.out.println("2. Прочитати вміст файлу");
            System.out.println("3. Вивести рядки в діапазоні");
            System.out.println("4. Вставити рядок у позицію");
            System.out.println("5. Вийти з редактора");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    writeToFile(scanner);
                    break;
                case "2":
                    readFromFile();
                    break;
                case "3":
                    readRange(scanner);
                    break;
                case "4":
                    insertLine(scanner);
                    break;
                case "5":
                    System.out.println("Вихід з редактора...");
                    return;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    public static void writeToFile(Scanner scanner) {
        System.out.println("Введіть рядки для запису. Щоб завершити, натисніть Enter на порожньому рядку:");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", false))) {
            int lineNumber = 1;
            while (true) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;
                writer.write(lineNumber + ". " + line);
                writer.newLine();
                lineNumber++;
            }
            System.out.println("Дані записано у файл.");
        } catch (IOException e) {
            System.out.println("Помилка запису у файл.");
        }
    }

    public static void readFromFile() {
        System.out.println("\nВміст файлу:");
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка читання з файлу або файл порожній.");
        }
    }

    public static void readRange(Scanner scanner) {
        System.out.print("Введіть початковий номер рядка: ");
        int start = Integer.parseInt(scanner.nextLine());
        System.out.print("Введіть кінцевий номер рядка: ");
        int end = Integer.parseInt(scanner.nextLine());

        if (start > end) {
            System.out.println("Помилка: початковий номер не може бути більшим за кінцевий.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            int currentLine = 1;
            System.out.println("\nРядки з " + start + " по " + end + ":");
            while ((line = reader.readLine()) != null) {
                if (currentLine >= start && currentLine <= end) {
                    System.out.println(line);
                }
                currentLine++;
            }
        } catch (IOException e) {
            System.out.println("Помилка читання з файлу або файл порожній.");
        }
    }

    public static void insertLine(Scanner scanner) {
        System.out.print("Введіть позицію для вставки рядка: ");
        int insertPos = Integer.parseInt(scanner.nextLine());
        System.out.println("Введіть текст для вставки: ");
        String newText = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String[] lines = new String[1000];
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null && count < 1000) {
                lines[count++] = line;
            }

            if (insertPos < 1 || insertPos > count + 1) {
                System.out.println("Неправильна позиція для вставки.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
                for (int i = 0; i < insertPos - 1; i++) {
                    writer.write(lines[i]);
                    writer.newLine();
                }
                writer.write(insertPos + ". " + newText);
                writer.newLine();
                for (int i = insertPos - 1; i < count; i++) {
                    writer.write((i + 2) + ". " + lines[i].substring(lines[i].indexOf(" ") + 1));
                    writer.newLine();
                }
            }
            System.out.println("Рядок вставлено.");
        } catch (IOException e) {
            System.out.println("Помилка при вставці рядка.");
        }
    }
}