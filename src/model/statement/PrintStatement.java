package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;

public record PrintStatement(IExpression exp) implements IStatement {


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.out().add(exp.evaluate(state.symbolTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
