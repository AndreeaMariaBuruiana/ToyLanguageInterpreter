package model.expression;

import model.state.IDictionary;
import model.state.IHeap;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String,IValue> symbolTable, IHeap<Integer,IValue> heap);

    IExpression deepCopy();
}
