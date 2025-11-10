package model.state;

import exception.EmptyStackException;
import model.statement.IStatement;

import java.util.LinkedList;


public class LinkedListExecutionStack<T> implements IExecutionStack<T> {
    private final LinkedList<T> stack = new LinkedList<>();

    @Override
    public void push(T item) {
        stack.addFirst(item);
    }

    @Override
    public T pop() {
        if(stack.isEmpty()){
            throw new EmptyStackException("Stack is empty!");
        }
        return stack.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        if (stack.isEmpty()) {
            return "  {empty}\n";
        }

        StringBuilder sb = new StringBuilder();
        for (T item : stack) {
            sb.append("  ").append(item).append("\n");
        }
        return sb.toString();
    }
}
