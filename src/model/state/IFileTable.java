package model.state;

import model.value.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable {
    boolean isOpen(StringValue filename);
    void add(StringValue filename, BufferedReader bufferedReader);
    BufferedReader getFile(StringValue filename);
    void close(StringValue filename);

    Map<StringValue, BufferedReader> getContent();
}
