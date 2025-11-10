package model.state;

import model.statement.IStatement;
import model.value.IValue;

public record ProgramState(IExecutionStack<IStatement> executionStack,
                           IDictionary<String, IValue> symbolTable,
                           IOut<IValue> out, IStatement originalProgram) {

    @Override
    public String toString() {
        return "=== Program State ===\n" +
                "Execution Stack: \n" + executionStack.toString() +
                "Symbol Table: \n" + symbolTable.toString() +
                "Output: \n" + out.toString() +
                "---------------------\n";
    }
}




