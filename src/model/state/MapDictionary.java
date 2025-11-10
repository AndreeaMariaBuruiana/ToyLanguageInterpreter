package model.state;

import java.util.HashMap;
import java.util.Map;

public class MapDictionary<K,V> implements IDictionary<K,V> {

    public final Map<K,V> symbolTable = new HashMap<>();
    @Override
    public void put(K key, V value) {
        symbolTable.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        return symbolTable.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return symbolTable.containsKey(key);
    }

    @Override
    public String toString() {
        if (symbolTable.isEmpty()) {
            return "  {empty}\n";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : symbolTable.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }



}
