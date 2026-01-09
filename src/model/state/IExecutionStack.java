package model.state;

import java.util.List;

public interface IExecutionStack <T>{
    void push(T item);
    T pop();
    boolean isEmpty();
    List<T> getReversedList();
}
