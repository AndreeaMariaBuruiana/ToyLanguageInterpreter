package model.expression;

import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression{

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        return value;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }
}
