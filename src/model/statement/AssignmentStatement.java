package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;

public record AssignmentStatement(String valueName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable());
        var expressionType = value.getType();
        var variableType = state.symbolTable().lookUp(valueName).getType();
        if (expressionType != variableType) {
            throw new MyException("Different types!");
        }
        state.symbolTable().put(valueName, value);
        return state;
    }

    @Override
    public String toString() {
        return valueName + " = " + expression.toString();
    }
}
