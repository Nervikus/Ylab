package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws SQLException, IOException {
        List<Movie> movies = readDataFromFile(file);

        try (Connection connection = dataSource.getConnection()) {
            for (Movie movie : movies) {
                String sql = "INSERT INTO movie (year,length,title,subject,actors,actress,director,popularity,awards)" +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    setInteger(statement, 1, movie.getYear());
                    setInteger(statement, 2, movie.getLength());
                    statement.setString(3, movie.getTitle());
                    statement.setString(4, movie.getSubject());
                    statement.setString(5, movie.getActors());
                    statement.setString(6, movie.getActress());
                    statement.setString(7, movie.getDirector());
                    setInteger(statement, 8, movie.getPopularity());
                    setBoolean(statement, 9, movie.getAwards());
                    statement.executeUpdate();
                }
            }
        }
    }

    private List<Movie> readDataFromFile(File file) throws IOException {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // сразу пропускаем первую строку
            boolean isSecondLine = true;

            while ((line = reader.readLine()) != null) {
                if (isSecondLine) {
                    isSecondLine = false;
                    continue; // пропускаем вторую строку
                }
                String[] values = line.split(";");
                Movie movie = new Movie();
                movie.setYear(parseInteger(values[0]));
                movie.setLength(parseInteger(values[1]));
                movie.setTitle(values[2]);
                movie.setSubject(values[3]);
                movie.setActors(values[4]);
                movie.setActress(values[5]);
                movie.setDirector(values[6]);
                movie.setPopularity(parseInteger(values[7]));
                movie.setAwards(parseBoolean((values[8])));
                movies.add(movie);
            }
            return movies;
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void setInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(index, value);
        } else {
            statement.setNull(index, Types.INTEGER);
        }
    }

    private Boolean parseBoolean(String value) {
        return "YES".equalsIgnoreCase(value);
    }

    private void setBoolean(PreparedStatement statement, int index, Boolean value) throws SQLException {
        if (value != null) {
            statement.setBoolean(index, value);
        } else {
            statement.setNull(index, java.sql.Types.BOOLEAN);
        }
    }
}