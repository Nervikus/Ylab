package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface SQLQueryBuilder {
  String queryForTable(String tableName) throws SQLException;
  
  List<String> getTables() throws SQLException;
}
