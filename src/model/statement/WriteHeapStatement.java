package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.value.IValue;
import model.value.RefValue;

public record WriteHeapStatement(String varName, IExpression expr) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!state.symbolTable().isDefined(varName))
            throw new MyException("Variable "+varName+" is not defined");
        var varValue = state.symbolTable().lookUp(varName);
        if(!(varValue instanceof RefValue refValue)){
            throw new MyException("Variable "+varName+" is not of type RefValue");
        }
        Integer addr = refValue.address();
        if(!state.heap().isDefined(addr)) {
            throw new MyException("The address " + addr + " is not defined in the heap");
        }

        IValue value;
        value = expr.evaluate(state.symbolTable(), state.heap());
        if(!value.getType().equals(refValue.locationType())){
            throw new MyException("Type mismatch: variable "+varName+" and expression do not have the same type");
        }
        state.heap().put(addr, value);
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expr.deepCopy());
    }

    @Override
    public String toString() {
        return "WriteHeapStmt(" + varName + ", " + expr.toString() + ")";
    }
}
