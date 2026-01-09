package model.state;

import exception.MyException;
import model.statement.IStatement;
import model.value.IValue;
import model.value.RefValue;

import java.util.HashSet;
import java.util.Set;

public class ProgramState {
    private IExecutionStack<IStatement> executionStack;
    private IDictionary<String, IValue> symbolTable;
    private IOut<IValue> out;
    private IFileTable fileTable;
    private IHeap<Integer, IValue> heap;
    private IStatement originalProgram;

    private int id;

    private static int lastId = 0;

    private static synchronized int getNewId() {
        return ++lastId;
    }

    public ProgramState(IExecutionStack<IStatement> stack, IDictionary<String,IValue> symTable,
                        IOut<IValue> out, IFileTable fileTable, IHeap<Integer, IValue> heap,
                        IStatement program) {
        this.executionStack = stack;
        this.symbolTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program;

        this.id = getNewId();

        if (program != null && stack.isEmpty()) {
            stack.push(program);
        }
    }


    public ProgramState(IExecutionStack<IStatement> stack, IDictionary<String,IValue> symTable,
                        IOut<IValue> out, IFileTable fileTable, IHeap<Integer, IValue> heap) {
        this.executionStack = stack;
        this.symbolTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNewId();
    }

    public IExecutionStack<IStatement> executionStack() { return executionStack; }
    public IDictionary<String, IValue> symbolTable() { return symbolTable; }
    public IOut<IValue> out() { return out; }
    public IFileTable fileTable() { return fileTable; }
    public IHeap<Integer, IValue> heap() { return heap; }
    public int getId() { return id; }

    public Boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty()) {
            throw new MyException("Program state stack is empty");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public Set<Integer> getUsedAddresses() {
        Set<Integer> usedAddr = new HashSet<>();
        for (IValue value : symbolTable.getValues()) {
            if (value instanceof RefValue) {
                usedAddr.add(((RefValue) value).address());
            }
        }
        return usedAddr;
    }

    @Override
    public String toString() {
        return "=== Program State ===\n" +
                "ID: " + this.id + "\n" +
                "Execution Stack: \n" + executionStack.toString() +
                "Symbol Table: \n" + symbolTable.toString() +
                "Output: \n" + out.toString() +
                "File Table: \n" + fileTable.toString() +
                "Heap: \n" + heap.toString() +
                "---------------------\n";
    }
}