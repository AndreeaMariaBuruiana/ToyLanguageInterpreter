package model.state;

import model.value.StringValue;

import java.io.BufferedReader;

public interface IFileTable {
    boolean isOpen(StringValue filename);
    void add(StringValue filename, BufferedReader bufferedReader);
    BufferedReader getFile(StringValue filename);
    void close(StringValue filename);
}
