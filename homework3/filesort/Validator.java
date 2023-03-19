package filesort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

public class Validator {
    private File file;

    public Validator(File file) {
        this.file = file;
    }

    public boolean isSorted() {
        int lineCount = 0;
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
                    System.out.println("Файл не отсортирован!");
                    return false;
                } else {
                    prev = current;
                }
                lineCount++;
            }
            System.out.println("Файл отсортирован успешно!");
            System.out.println("Количество строк на входе: " + lineCount);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try (Stream<String> lines = Files.lines(file.toPath())) {
                long count = lines.count();
                if (count != lineCount) {
                    System.out.println("Количество строк в файле: " + count);
                } else {
                    System.out.println("Количество строк на выходе: " + lineCount);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}