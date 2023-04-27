package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private DatabaseMetaData databaseMetaData;

    @Autowired
    public SQLQueryBuilderImpl(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        List<String> tables = getTables();
        if (!tables.contains(tableName)) {
            return null;
        } else {
            List<String> columns = new ArrayList<>();
            String columnList;
            try (ResultSet resultSet = databaseMetaData.getColumns(null, null, tableName, null)) {
                if (!resultSet.next()) {
                    System.out.print("Таблица без колонок: " + tableName + ": ");
                    return null;
                }
                while (resultSet.next()) {
                    columns.add(resultSet.getString("COLUMN_NAME"));
                }
                columnList = String.join(", ", columns);
            }
            return "SELECT " + columnList + " FROM " + tableName;
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();

        // если вместо null, в четвертом аргументе, написать new String[] {"TABLE"}, то системные таблицы не будут выводиться
        try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, null)) {
            while (resultSet.next()) {
                String table = resultSet.getString("TABLE_NAME");
                tables.add(table);
            }
        }
        return tables;
    }
}
