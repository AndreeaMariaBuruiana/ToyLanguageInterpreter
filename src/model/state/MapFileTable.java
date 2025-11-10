package model.state;

import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapFileTable implements IFileTable {

    private final Map<StringValue, BufferedReader> fileTable = new HashMap<>();

    @Override
    public boolean isOpen(StringValue filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void add(StringValue filename, BufferedReader bufferedReader) {
        fileTable.put(filename, bufferedReader);
    }

    @Override
    public BufferedReader getFile(StringValue filename) {
        return fileTable.get(filename);
    }

    @Override
    public void close(StringValue filename) {
        try {
            fileTable.remove(filename).close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (fileTable.isEmpty()) {
            return "{empty}";
        }
        for (StringValue filename : fileTable.keySet()) {
            builder.append(filename).append("\n");
        }
        if (!builder.isEmpty()) {
            builder.setLength(builder.length() - 1);
        }

        return builder.toString();
    }
}
