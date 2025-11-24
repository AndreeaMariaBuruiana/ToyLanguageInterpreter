package model.state;

import exception.MyException;
import model.value.IValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IHeap<K,V> {
    V get(K key) throws MyException;
    void put(K key, V value);
    boolean isDefined(K key);
    void remove(K key) throws MyException;
    Map<K,V> getHeap();
    List<V> getValues();
    void setHeap(Map<K,V> newHeap);
    Map<Integer, IValue> safeGarbageCollector(Set<Integer> usedAddr, Map<Integer, IValue> heap);
    Integer allocate();
}
