package model.state;

public interface IDictionary<K,V> {
    void put(K key, V value);
    V lookUp(K key);
    boolean containsKey(K key);

}
