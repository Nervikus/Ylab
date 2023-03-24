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
    private static final int CHUNK_SIZE_LINES = 50_000_000;

    public File sortFile(final File dataFile) throws IOException {
        int filesAmount = split(dataFile);
        sortFiles(filesAmount);
        return new File("sorted_data.txt");
    }

    private int split(final File dataFile) throws IOException {
        Long[] numbersChunk = new Long[CHUNK_SIZE_LINES];
        int count = 0;
        int chunksAmount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            while (reader.ready()) {
                numbersChunk[count] = Long.valueOf(reader.readLine());
                count++;
                if (count == CHUNK_SIZE_LINES || !reader.ready()) {
                    mergeSort(numbersChunk, count);
                    try (PrintWriter pw = new PrintWriter("temp_" + chunksAmount + ".txt")) {
                        for (Long number : numbersChunk) {
                            pw.println(number);
                        }
                        pw.flush();
                    }
                    numbersChunk = new Long[CHUNK_SIZE_LINES];
                    count = 0;
                    chunksAmount++;
                }
            }
        }
        return chunksAmount;
    }

    private void sortFiles(final int amount) throws IOException {
        int newAmount = 0;
        for (int i = 0; i < amount; i += 2) {
            File temp1 = new File("temp_" + (i) + ".txt");
            File temp2 = new File("temp_" + (i + 1) + ".txt");
            if (!temp2.exists()) {
                temp1.renameTo(new File("temp_" + newAmount + ".txt"));
                newAmount++;
                break;
            }
            mergeFiles(temp1, temp2, newAmount);
            newAmount++;
        }

        if (newAmount == 1) {
            File output = new File("temp_0.txt");
            File sortedOutput = new File("sorted_data.txt");
            if (new File("sorted_data.txt").isFile()) {
                sortedOutput.delete();
                output.renameTo(sortedOutput);
            } else {
                output.renameTo(sortedOutput);
            }
        } else {
            sortFiles(newAmount);
        }
    }

    private void mergeSort(Long[] array, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Long[] left = new Long[mid];
        Long[] right = new Long[n - mid];

        System.arraycopy(array, 0, left, 0, mid);

        if (n - mid >= 0) {
            System.arraycopy(array, mid, right, 0, n - mid);
        }
        mergeSort(left, mid);
        mergeSort(right, n - mid);
        sort(array, left, right, mid, n - mid);
    }

    private void sort(Long[] input, Long[] left, Long[] right, int leftLength, int rightLength) {
        int i = 0, j = 0, k = 0;
        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                input[k++] = left[i++];
            } else {
                input[k++] = right[j++];
            }
        }

        while (i < leftLength) {
            input[k++] = left[i++];
        }

        while (j < rightLength) {
            input[k++] = right[j++];
        }
    }

    private void mergeFiles(File temp1, File temp2, int number) throws IOException {
        try (PrintWriter printWriter = new PrintWriter("temp");
             Scanner scanner1 = new Scanner(temp1);
             Scanner scanner2 = new Scanner(temp2)) {

            Long left = scanner1.nextLong();
            Long right = scanner2.nextLong();

            while (scanner1.hasNextLong() && scanner2.hasNextLong()) {
                if (left <= right) {
                    printWriter.println(left);
                    left = scanner1.nextLong();
                } else {
                    printWriter.println(right);
                    right = scanner2.nextLong();
                }
            }

            Long last;
            if (left <= right) {
                printWriter.println(left);
                last = right;
            } else {
                printWriter.println(right);
                last = left;
            }

            while (scanner1.hasNextLong()) {
                Long x = scanner1.nextLong();

                if (last != null && last <= x) {
                    printWriter.println(last);
                    last = null;
                    printWriter.println(x);
                } else if (last != null && !scanner1.hasNextLong()) {
                    printWriter.println(x);
                    printWriter.println(last);
                    last = null;
                } else {
                    printWriter.println(x);
                }
            }

            while (scanner2.hasNextLong()) {
                Long x = scanner2.nextLong();

                if (last != null && last <= x) {
                    printWriter.println(last);
                    last = null;
                    printWriter.println(x);
                } else if (last != null && !scanner2.hasNextLong()) {
                    printWriter.println(x);
                    printWriter.println(last);
                    last = null;
                } else {
                    printWriter.println(x);
                }
            }
        }
        temp1.delete();
        temp2.delete();
        File file = new File("temp");
        file.renameTo(new File("temp_" + number + ".txt"));
    }
}