package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.IDictionary;
import model.state.ProgramState;
import model.type.BoolType;
import model.type.IType;
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
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expr.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of WHILE does not have the type bool!");
        }
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
