package model.statement;

import exception.MyException;
import model.state.ProgramState;
import model.type.IType;

public record VariableDeclarationStatement(String varName, IType type) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.symbolTable();
        symbolTable.put(varName, type.defaultValue());
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(varName,type.deepCopy());
    }
}
