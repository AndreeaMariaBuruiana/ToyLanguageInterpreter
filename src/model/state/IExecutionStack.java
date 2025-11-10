package model.state;

public interface IExecutionStack <T>{
    void push(T item);
    T pop();
    boolean isEmpty();
}
