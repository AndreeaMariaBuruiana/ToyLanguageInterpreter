package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record NewStatement(String varName, IExpression expr) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!state.symbolTable().isDefined(varName))
            throw new MyException("Variable "+varName+" is not defined");
        IType varType = state.symbolTable().lookUp(varName).getType();
        if(!(varType instanceof RefType))
            throw new MyException("Variable "+varName+" is not of type RefType");

        IValue val = expr.evaluate(state.symbolTable(), state.heap());
        if(!val.getType().equals(((RefType) varType).getInner()))
            throw new MyException("Type mismatch: variable "+varName+" and expression do not have the same type");

        Integer newAddress = state.heap().allocate();
        state.heap().put(newAddress, val);
        state.symbolTable().put(varName, new RefValue(newAddress,val.getType()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(varName, expr.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expr.toString() + ")";
    }
}
