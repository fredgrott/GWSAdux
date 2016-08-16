package com.github.shareme.gwsadux.core.viewstate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A set of utility methods to create modified
 * collections instead of mutating them directly.
 * Created by fgrott on 8/14/2016.
 */
@SuppressWarnings("unused")
class Util {

  /**
   * Collections with a Map key and value
   * @param src the source
   * @param key the key
   * @param value the value
   * @param <K> K type
   * @param <V> V type
   * @return rad-only collection with map
   */
  static <K, V> Map<K, V> with(Map<K, V> src, K key, V value) {
    HashMap<K, V> dst = new HashMap<>(src);
    dst.put(key, value);
    return Collections.unmodifiableMap(dst);
  }

  /**
   * without the key type map read-only collection
   * @param src the source
   * @param key the key
   * @param <K> K type
   * @param <V> V type
   * @return read-only collection with key removed
   */
  static <K, V> Map<K, V> without(Map<K, V> src, K key) {
    HashMap<K, V> dst = new HashMap<>(src);
    dst.remove(key);
    return Collections.unmodifiableMap(dst);
  }

  /**
   * List with value
   * @param src the source
   * @param value the value
   * @param <T> T type
   * @return read-only collection list with value
   */
  static <T> List<T> with(List<T> src, T value) {
    ArrayList<T> dst = new ArrayList<>(src);
    dst.add(value);
    return Collections.unmodifiableList(dst);
  }

  /**
   * list without ie removed value
   * @param src the source
   * @param value the value
   * @param <T> T type
   * @return read-only collection List with value removed
   */
  static <T> List<T> without(List<T> src, T value) {
    ArrayList<T> dst = new ArrayList<>(src);
    dst.remove(value);
    return Collections.unmodifiableList(dst);
  }
}
