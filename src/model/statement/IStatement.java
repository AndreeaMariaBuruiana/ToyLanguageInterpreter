package model.statement;

import exception.MyException;
import model.state.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IDictionary<String, IType> typeCheck(IDictionary<String,IType> typeEnv) throws MyException;

    IStatement deepCopy();
}
