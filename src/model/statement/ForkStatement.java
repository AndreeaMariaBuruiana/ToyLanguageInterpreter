package model.statement;

import exception.MyException;
import model.state.IExecutionStack;
import model.state.LinkedListExecutionStack;
import model.state.ProgramState;

public record ForkStatement(IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IExecutionStack<IStatement> newStack = new LinkedListExecutionStack<>();
        newStack.push(statement);
        return new ProgramState(
                newStack,
                state.symbolTable().deepCopy(),
                state.out(),
                state.fileTable(),
                state.heap()
        );
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
