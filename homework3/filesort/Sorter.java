package filesort;

import java.io.*;
import java.util.*;

/**
 * 1. Разбиваем входящий файл на несколько временных файлов размером не более 1 Гб.
 * 2. Сортируем каждый из временных файлов по возрастанию.
 * 3. Объединяем отсортированные временные файлы в один файл с помощью сортировки слиянием.
 * 4. Удаляем временные файлы.
 */

public class Sorter {
    private static final int CHUNK_SIZE_BYTES = 1000 * 1000 * 1000;
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
            deleteTempFiles(files);
            deleteTempFiles(sortedFiles);
        }
    }

    private List<File> splitFile(File dataFile) throws IOException {
        List<File> files = new ArrayList<>();
        int fileSizeBytes = (int) dataFile.length();
        int chunks = (int) Math.ceil((double) fileSizeBytes / CHUNK_SIZE_BYTES);
        byte[] buffer = new byte[CHUNK_SIZE_BYTES];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dataFile))) {
            for (int i = 0; i < chunks; i++) {
                int bytesRead = bis.read(buffer);
                File file = new File("temp" + i + ".txt");
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(buffer, 0, bytesRead);
                    fos.flush();
                }
                files.add(file);
            }
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