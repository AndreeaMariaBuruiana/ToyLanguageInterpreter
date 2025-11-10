package model.statement;

import exception.MyException;
import model.state.ProgramState;
import model.type.Type;
import model.value.IValue;

public record VariableDeclarationStatement(String varName, Type type) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.symbolTable();
        symbolTable.put(varName, type.getDefaultValue());
        return state;
    }
}
