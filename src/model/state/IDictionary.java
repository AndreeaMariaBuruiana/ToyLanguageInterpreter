package model.state;

import java.util.List;

public interface IDictionary<K,V> {
    void put(K key, V value);
    V lookUp(K key);
    boolean isDefined(K key);
    void update(K key, V value);
    List<V> getValues();

    IDictionary<K,V> deepCopy();
}
