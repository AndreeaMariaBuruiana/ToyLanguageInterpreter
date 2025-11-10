package model.state;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOut<T> implements IOut<T> {
    private final List<T> values = new ArrayList<>();

    @Override
    public void add(T value) {
        values.add(value);
    }

    @Override
    public String toString() {
        if (values.isEmpty()) {
            return "  {empty}\n";
        }

        StringBuilder sb = new StringBuilder();
        for (T item : values) {
            sb.append("  ").append(item).append("\n");
        }
        return sb.toString();
    }
}
