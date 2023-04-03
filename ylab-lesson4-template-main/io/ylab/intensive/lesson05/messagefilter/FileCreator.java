package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class FileCreator {
    private File file = new File("censor_list.txt");
    private DataSource dataSource;

    @Autowired
    public FileCreator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addToTable() {
        String insertSQL = "INSERT INTO censor (word) VALUES (?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            String line;
            while ((line = reader.readLine()) != null) {
                statement.setString(1, line);
                statement.executeUpdate();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
    // Метод ниже я использовал, чтобы привести файл с бранью в порядок

//    private void orderingFile(File input) throws IOException {
//        String insertSQL = "INSERT INTO censor (word) VALUES (?)";
//        try (BufferedReader reader = new BufferedReader(new FileReader(input));
//             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                   String[] words = line.split(",");
//                for (String word : words) {
//                    writer.write(word.trim());
//                    writer.newLine();
//                }
//            }
//        }
//    }
