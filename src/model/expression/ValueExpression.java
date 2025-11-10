package model.expression;

import model.state.IDictionary;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression{

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable) {
        return value;
    }
}
