package model.statement;

import exception.MyException;
import model.state.IDictionary;
import model.state.IExecutionStack;
import model.state.ProgramState;
import model.type.IType;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        IExecutionStack<IStatement> stack = state.executionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ')';
    }
}
