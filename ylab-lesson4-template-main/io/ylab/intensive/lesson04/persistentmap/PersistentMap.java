package io.ylab.intensive.lesson04.persistentmap;

import java.util.List;

public interface PersistentMap {

  void init(String name);

  boolean containsKey(String key);

  List<String> getKeys();

  String get(String key);

  void remove(String key);

  void put(String key, String value);

  void clear();
}
