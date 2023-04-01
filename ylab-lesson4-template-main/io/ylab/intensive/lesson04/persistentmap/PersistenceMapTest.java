package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    // Демонстрация работы
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("numbers");
    persistentMap.put("1", " one");
    persistentMap.put("2", "two");
    persistentMap.put("5", "five");
    System.out.println(persistentMap.getKeys()); // [1, 2, 5]
    System.out.println(persistentMap.get("2")); // two
    persistentMap.put("2", "other number");
    System.out.println(persistentMap.get("2")); // other number
    System.out.println(persistentMap.getKeys()); // [1, 5, 2]
    System.out.println(persistentMap.containsKey("1")); // true
    persistentMap.remove("1");
    System.out.println(persistentMap.containsKey("1")); // false
    persistentMap.init("symbols");
    persistentMap.put("a", "A");
    System.out.println(persistentMap.getKeys()); // [a]
    persistentMap.init("numbers");
    persistentMap.put("7", "seven");
    System.out.println(persistentMap.getKeys()); // [5, 2, 7]
    persistentMap.clear();
    persistentMap.put("2", "two");
    System.out.println(persistentMap.getKeys()); // [2]
    persistentMap.clear();
    System.out.println(persistentMap.getKeys()); // []
    persistentMap.init("symbols");
    System.out.println(persistentMap.getKeys()); //[a]
    persistentMap.clear(); // всё удалили
    System.out.println(persistentMap.getKeys()); // []
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
