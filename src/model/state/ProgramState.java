package model.state;

import model.statement.IStatement;
import model.value.IValue;
import model.value.RefValue;

import java.util.HashSet;
import java.util.Set;

public record ProgramState(IExecutionStack<IStatement> executionStack,
                           IDictionary<String, IValue> symbolTable,
                           IOut<IValue> out,IFileTable fileTable, IHeap<Integer,IValue> heap,IStatement originalProgram) {

    @Override
    public String toString() {
        return "=== Program State ===\n" +
                "Execution Stack: \n" + executionStack.toString() +
                "Symbol Table: \n" + symbolTable.toString() +
                "Output: \n" + out.toString() +
                "File Table: \n" + fileTable.toString() +
                "Heap: \n" + heap.toString() +
                "---------------------\n";
    }

    public Set<Integer> getUsedAddresses() {
        Set<Integer> usedAddr = new HashSet<>();
        for (IValue value : symbolTable.getValues()) {
            if(value instanceof RefValue){
                usedAddr.add(((RefValue) value).address());
            }
        }
        for(IValue value : heap.getValues()){
            if(value instanceof RefValue){
                usedAddr.add(((RefValue) value).address());
            }
        }
        return usedAddr;
    }
}




