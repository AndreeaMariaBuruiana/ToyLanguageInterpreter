package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;

public record AssignmentStatement(String valueName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable());
        var expressionType = value.getType();

        if (!state.symbolTable().isDefined(valueName)) {
            throw new MyException("Variable " + valueName + " is not defined!");
        }

        var variableType = state.symbolTable().lookUp(valueName).getType();

        if (!expressionType.equals(variableType)) {
            throw new MyException("Different types! Cannot assign " + expressionType + " to " + variableType);
        }

        state.symbolTable().put(valueName, value);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(valueName,expression.deepCopy());
    }

    @Override
    public String toString() {
        return valueName + " = " + expression.toString();
    }
}
