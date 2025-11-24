package model.expression;

import model.state.IDictionary;
import model.state.IHeap;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression{

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }
}
