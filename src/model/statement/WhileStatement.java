package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.BoolType;
import model.value.BoolValue;

public record WhileStatement(IExpression expr, IStatement statement) implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expr.evaluate(state.symbolTable(),state.heap());
        if(!(value instanceof BoolValue boolValue)){
            throw new MyException("While condition is not boolean!");
        }
        if(boolValue.value()){
            state.executionStack().push(this);
            state.executionStack().push(statement);
        }
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expr.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + expr.toString() + ") {" + statement.toString() + "}";
    }
}
