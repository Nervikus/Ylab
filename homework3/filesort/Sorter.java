package filesort;

import java.io.*;
import java.util.*;

public class Sorter {
    private static final int MAX_SIZE = 50000000; // 50 Mb

    public File sortFile(File dataFile) throws IOException {
        if (dataFile.length() < MAX_SIZE) {
            long[] data = readData(dataFile);
            Arrays.sort(data);
            File outputFile = new File("sorted_" + dataFile.getName());
            writeData(data, outputFile);
            return outputFile;
        } else {
            int[] buffer = new int[MAX_SIZE];
            int fileNum = 0;
            List<File> intermediateFiles = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
                while (reader.ready()) {
                    String[] line = reader.readLine().split(" ");
                    long[] data = new long[line.length];
                    for (int i = 0; i < line.length; i++) {
                        data[i] = Long.parseLong(line[i]);
                    }
                    Arrays.sort(data);
                    File intermediateFile = new File("intermediate_" + fileNum++);
                    intermediateFiles.add(intermediateFile);
                    writeData(data, intermediateFile);
                }
            }
            return mergeFiles(intermediateFiles);
        }
    }

    private long[] readData(File file) throws IOException {
        List<Long> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(" ");
                for (String s : line) {
                    data.add(Long.valueOf(s));
                }
            }
            long[] result = new long[data.size()];
            for (int i = 0; i < data.size(); i++) {
                result[i] = data.get(i);
            }
            return result;
        }
    }

    private void writeData(long[] data, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < data.length; i++) {
                writer.write(String.valueOf(data[i]));
                if (i != data.length - 1) {
                    writer.write("\n");
                }
            }
        }
    }

    private File mergeFiles(List<File> files) throws IOException {
        PriorityQueue<ReaderWrapper> queue = new PriorityQueue<>();
        for (File file : files) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            queue.offer(new ReaderWrapper(reader));
        }
        File outputFile = new File("sorted_output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (!queue.isEmpty()) {
                ReaderWrapper readerWrapper = queue.poll();
                writer.write(String.valueOf(readerWrapper.getCurrentValue()));
                writer.write(" ");
                if (readerWrapper.hasNext()) {
                    queue.offer(readerWrapper);
                } else {
                    readerWrapper.close();
                }
            }
            return outputFile;
        }
    }

    private static class ReaderWrapper implements Comparable<ReaderWrapper>, Closeable {
        private final BufferedReader reader;
        private String currentLine;
        private int currentIndex = 0;

        public ReaderWrapper(BufferedReader reader) throws IOException {
            this.reader = reader;
            readLine();
        }

        public boolean hasNext() throws IOException {
            if (currentIndex < currentLine.length()) {
                return true;
            } else {
                readLine();
                return currentLine != null;
            }
        }

        public int getCurrentValue() {
            String num = currentLine.substring(currentIndex, currentLine.indexOf(" ", currentIndex));
            currentIndex = currentLine.indexOf(" ", currentIndex) + 1;
            return Integer.parseInt(num);
        }

        private void readLine() throws IOException {
            do {
                currentLine = reader.readLine();
                currentIndex = 0;
            } while (currentLine != null && currentLine.isEmpty());
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }

        @Override
        public int compareTo(ReaderWrapper o) {
            return Integer.compare(getCurrentValue(), o.getCurrentValue());
        }
    }
}