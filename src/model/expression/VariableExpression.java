package model.expression;

import exception.DictionaryException;
import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.value.IValue;

public record VariableExpression(String varName) implements IExpression {
    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        if(!(symbolTable.isDefined(varName))){
            throw new DictionaryException("The variable " + varName + " is not defined!");
        }
        return symbolTable.lookUp(varName);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        if (!typeEnv.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined!");
        }
        return typeEnv.lookUp(varName);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(varName);
    }
}
