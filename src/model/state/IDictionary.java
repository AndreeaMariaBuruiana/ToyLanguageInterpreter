package model.state;

import java.util.List;
import java.util.Map;

public interface IDictionary<K,V> {
    void put(K key, V value);
    V lookUp(K key);
    boolean isDefined(K key);
    void update(K key, V value);
    List<V> getValues();
    Map<K, V> getContent();
    IDictionary<K,V> deepCopy();
}
