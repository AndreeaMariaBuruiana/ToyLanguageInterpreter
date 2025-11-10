package model.statement;

import exception.MyException;
import exception.StatementException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.Type;
import model.value.BoolValue;

public record IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable());
        if(value.getType() != Type.BOOLEAN) {
            throw new StatementException("If statement is not boolean!");
        }
        var boolValue = (BoolValue) value;
        if(boolValue.value()){
            state.executionStack().push(thenStatement);
        } else {
            state.executionStack().push(elseStatement);
        }
        return state;

    }

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + thenStatement.toString() + ") ELSE(" + elseStatement.toString() + "))";
    }
}
