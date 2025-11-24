package model.state;

import exception.DictionaryException;
import exception.MyException;
import model.value.IValue;

import java.util.*;

public class MyHeap<K,V> implements IHeap<K,V> {

    private Integer firstFreeAddress = 1;
    private Map<K,V> heap = new HashMap<>();

    @Override
    public V get(K key) throws MyException {
        if(!isDefined(key))
            throw new DictionaryException("Key is not defined for the heap!");
        return this.heap.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void remove(K key) throws MyException {
        this.heap.remove(key);
    }

    @Override
    public Map<K, V> getHeap() {
        return this.heap;
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<V>(this.heap.values());
    }

    @Override
    public void setHeap(Map<K, V> newHeap) {
        this.heap = newHeap;
    }

    @Override
    public Map<Integer, IValue> safeGarbageCollector(Set<Integer> usedAddr, Map<Integer, IValue> heap) {
        Map<Integer, IValue> newHeap = new HashMap<>();
        for(Integer key : heap.keySet()){
            if(usedAddr.contains(key)){
                newHeap.put(key, heap.get(key));
            }
        }
        return newHeap;
    }

    @Override
    public Integer allocate() {
        return this.firstFreeAddress++;
    }

    @Override
    public String toString() {
        if (this.heap.isEmpty())
            return "{empty heap}\n";
        StringBuilder builder = new StringBuilder();
        for (K key : this.heap.keySet()) {
            builder.append(key.toString()).append(" -> ").append(this.heap.get(key).toString()).append("\n");
        }
        return builder.toString();
    }
}
