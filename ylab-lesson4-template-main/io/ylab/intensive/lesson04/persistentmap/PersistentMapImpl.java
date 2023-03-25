package io.ylab.intensive.lesson04.persistentmap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String mapName;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.mapName = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM persistent_map WHERE map_name = ? AND key = ?")) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keys = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT key FROM persistent_map WHERE map_name = ?")) {

            preparedStatement.setString(1, mapName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keys.add(resultSet.getString(1));
            }
            resultSet.close();
        }
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT value FROM persistent_map WHERE map_name = ? AND key = ?")) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            resultSet.close();
        }
        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM persistent_map WHERE map_name = ? AND key = ?")) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (containsKey(key)) {
            remove(key);
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM persistent_map WHERE map_name = ?")) {

            preparedStatement.setString(1, mapName);
            preparedStatement.executeUpdate();
        }
    }
}