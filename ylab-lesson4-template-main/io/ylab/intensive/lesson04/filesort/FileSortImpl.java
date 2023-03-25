package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;

/**
 * У меня вопрос - правильно в этой задаче перед каждым запросом
 * открывать сеанс (connection) или можно было в начале метода
 * открыть, а в конце закрыть?
 */
public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private int inputLines = 0;
    private int outputLines = 0;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) throws IOException, SQLException {
        // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ

        System.out.println("Сортировка с помощью batch-processing:");
        System.out.println(new Date());

        String insertSQL = "INSERT INTO numbers (val) VALUES (?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            String line;
            while ((line = reader.readLine()) != null) {
                long number = Long.parseLong(line);
                preparedStatement.setLong(1, number);
                preparedStatement.addBatch();
                inputLines++;
            }
            preparedStatement.executeBatch();
        }

        String sortSQL = "SELECT val FROM numbers ORDER BY val DESC";
        File sortedFile = new File("sorted_data.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sortedFile));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sortSQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long number = resultSet.getLong(1);
                writer.write(Long.toString(number));
                writer.newLine();
                outputLines++;
            }
        }

        String dropSQL = "DROP TABLE numbers";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropSQL)) {

            preparedStatement.executeUpdate();
        }
        System.out.println(new Date());
        System.out.println("Количество чисел в начальном файле - " + inputLines);
        System.out.println("Количество чисел в отсортированном файле - " + outputLines);
        inputLines = 0;
        outputLines = 0;
        return sortedFile;
    }

    // Извиняюсь за дублирование - просто хочу показать медленную реализацию
    @Override
    public File sortWithoutBatch(File data) throws IOException, SQLException {
        // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ
        System.out.println("Сортировка без batch-processing:");
        System.out.println(new Date());
        String insertSQL = "INSERT INTO numbers (val) VALUES (?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            String line;
            while ((line = reader.readLine()) != null) {
                long number = Long.parseLong(line);
                preparedStatement.setLong(1, number);
                preparedStatement.executeUpdate();
                inputLines++;
            }
        }

        String sortSQL = "SELECT val FROM numbers ORDER BY val DESC";
        File sortedFile = new File("sorted_data.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sortedFile));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sortSQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long number = resultSet.getLong(1);
                writer.write(Long.toString(number));
                writer.newLine();
                outputLines++;
            }
        }

        String dropSQL = "DROP TABLE numbers";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropSQL)) {

            preparedStatement.executeUpdate();
        }
        System.out.println(new Date());
        System.out.println("Количество чисел в начальном файле - " + inputLines);
        System.out.println("Количество чисел в отсортированном файле - " + outputLines);
        inputLines = 0;
        outputLines = 0;
        return sortedFile;
    }
}