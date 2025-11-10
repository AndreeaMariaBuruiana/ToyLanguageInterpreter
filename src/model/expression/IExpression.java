package model.expression;

import model.state.IDictionary;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String,IValue> symbolTable);

    IExpression deepCopy();
}
