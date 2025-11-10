package model.statement;

import exception.MyException;
import model.state.ProgramState;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
}
