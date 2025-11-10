package model.statement;

import exception.MyException;
import model.state.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;

    IStatement deepCopy();
}
