package filesort;

import java.io.*;
import java.util.*;

/**
 * 1. Разбиваем входящий файл на несколько временных файлов (размер указываем сами).
 * 2. Сортируем каждый из временных файлов по возрастанию.
 * 3. Объединяем отсортированные временные файлы в один файл с помощью сортировки слиянием.
 * 4. Удаляем временные файлы.
 */

public class Sorter {
    private static final int CHUNK_SIZE_BYTES = 256;
    private List<File> files;
    private List<File> sortedFiles;
    private File resultFile;

    public File sortFile(File dataFile) throws IOException {
        try {
            files = splitFile(dataFile);
            sortedFiles = new ArrayList<>();
            for (File file : files) {
                sortedFiles.add(sortTempFile(file));
            }
            resultFile = mergeSortedFiles(sortedFiles);
            return resultFile;
        } finally {
            deleteTempFiles(sortedFiles);
            deleteTempFiles(files);
        }
    }

    private List<File> splitFile(File dataFile) throws IOException {
        List<File> files = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            int currentLineNumber = 0;
            int fileNumber = 0;
            File tempFile = new File("temp" + fileNumber + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                currentLineNumber++;

                if (currentLineNumber >= CHUNK_SIZE_BYTES) {
                    writer.close();
                    files.add(tempFile);
                    currentLineNumber = 0;
                    fileNumber++;
                    tempFile = new File("temp" + fileNumber + ".txt");
                    writer = new BufferedWriter(new FileWriter(tempFile));
                }
            }
            if (currentLineNumber > 0) {
                writer.close();
                files.add(tempFile);
            }
            writer.close();
        }
        return files;
    }

    private File sortTempFile(File file) throws IOException {
        List<Long> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLong()) {
                list.add(scanner.nextLong());
            }
        }
        Collections.sort(list);
        File sortedFile = new File("sorted_" + file.getName());
        try (PrintWriter pw = new PrintWriter(sortedFile)) {
            for (Long l : list) {
                pw.println(l);
            }
            pw.flush();
        }
        return sortedFile;
    }

    private File mergeSortedFiles(List<File> files) throws IOException {
        PriorityQueue<Long> queue = new PriorityQueue<>();
        List<Scanner> scanners = new ArrayList<>();
        for (File file : files) {
            Scanner scanner = new Scanner(file);
            scanners.add(scanner);
            while (scanner.hasNextLong()) {
                queue.add(scanner.nextLong());
            }
        }
        File resultFile = new File("sorted_data.txt");
        try (PrintWriter pw = new PrintWriter(resultFile)) {
            while (!queue.isEmpty()) {
                pw.println(queue.poll());
            }
            pw.flush();
        }
        for (Scanner scanner : scanners) {
            scanner.close();
        }
        return resultFile;
    }

    private void deleteTempFiles(List<File> files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}