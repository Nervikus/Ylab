package filesort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 1. Разбиваем входящий файл на несколько временных файлов (размер указываем сами).
 * 2. Сортируем каждый из временных файлов по возрастанию.
 * 3. Объединяем отсортированные временные файлы в один файл с помощью сортировки слиянием.
 * 4. Удаляем временные файлы.


/** Отсортированный файл может быть не переименован, если запускать программу повторно.
* Тогда отсортированные данные будут в файле chunk_0.txt
 */

public class Sorter {
    private static final int CHUNK_SIZE_BYTES = 256;

    public File sortFile(final File dataFile) throws IOException {
        int chunksAmount = splitFile(dataFile);
        sortChunks(chunksAmount);
        return new File("sorted_data.txt");
    }

    private void writeTempFiles(final int chunkNumber, final Long[] data) throws IOException {
        try (PrintWriter pw = new PrintWriter("chunk_" + chunkNumber + ".txt")) {
            for (int i = 0; i < data.length; i++) {
                pw.println(data[i]);
            }
            pw.flush();
        }
    }

    private int splitFile(final File dataFile) throws IOException {
        Long[] chunk = new Long[CHUNK_SIZE_BYTES];
        int count = 0;
        int chunksAmount = 0;

        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNextLong()) {
                chunk[count] = scanner.nextLong();
                count++;

                if (count == CHUNK_SIZE_BYTES || !scanner.hasNextLong()) {
                    mergeSort(chunk, count);
                    writeTempFiles(chunksAmount, chunk);
                    chunk = new Long[CHUNK_SIZE_BYTES];
                    count = 0;
                    chunksAmount++;
                }
            }

        }

        return chunksAmount;
    }

    private void sortChunks(final int amount) throws IOException {
        int newAmount = 0;
        for (int i = 0; i < amount; i += 2) {

            File chunk1 = new File("chunk_" + (i) + ".txt");
            File chunk2 = new File("chunk_" + (i + 1) + ".txt");
            if (!chunk2.exists()) {
                chunk1.renameTo(new File("chunk_" + newAmount + ".txt"));
                newAmount++;
                break;
            }

            mergeSortedFiles(chunk1, chunk2, newAmount);
            newAmount++;
        }
        if (newAmount == 1) {
            File output = new File("chunk_0.txt");
            File sortedOutput = new File("sorted_data.txt");
            output.renameTo(sortedOutput);
        } else {
            sortChunks(newAmount);
        }
    }

    private void mergeSort(Long[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Long[] l = new Long[mid];
        Long[] r = new Long[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        sort(a, l, r, mid, n - mid);
    }

    private Long[] sort(Long[] input, Long[] left, Long[] right, int leftLength, int rightLength) {
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

        return input;
    }


    private void mergeSortedFiles(File chunk1, File chunk2, int number) throws IOException {

        try (PrintWriter printWriter = new PrintWriter("temp")) {
            try (Scanner scanner = new Scanner(chunk1)) {
                try (Scanner scanner1 = new Scanner(chunk2)) {

                    Long left = scanner.nextLong();
                    Long right = scanner1.nextLong();

                    while (scanner.hasNextLong() && scanner1.hasNextLong()) {

                        if (left <= right) {
                            printWriter.println(left);
                            left = scanner.nextLong();
                        } else {
                            printWriter.println(right);
                            right = scanner1.nextLong();
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

                    while (scanner.hasNextLong() || scanner1.hasNextLong()) {
                        Long x;
                        if (scanner.hasNextLong())  {
                            x = scanner.nextLong();
                        } else {
                            x = scanner1.nextLong();
                        }

                        if (last != null && last <= x) {
                            printWriter.println(last);
                            last = null;
                            printWriter.println(x);
                        } else if (last != null && last >= x && !scanner.hasNextLong()) {
                            printWriter.println(x);
                            printWriter.println(last);
                            last = null;
                        } else {
                            printWriter.println(x);
                        }
                    }
                }
            }
        }

        chunk1.delete();
        chunk2.delete();
        File file = new File("temp");
        file.renameTo(new File("chunk_" + number + ".txt"));
    }
}
