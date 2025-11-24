package model.expression;

import exception.LogicException;
import model.state.IDictionary;
import model.state.IHeap;
import model.value.BoolValue;
import model.value.IValue;

public record LogicExpression(IExpression e1, IExpression e2, String op) implements IExpression {


    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        IValue v1 = e1.evaluate(symbolTable, heap);
        IValue v2 = e2.evaluate(symbolTable, heap);
        if(!(v1 instanceof BoolValue(boolean leftTerm))){
            throw new LogicException("First argument is not a boolean");
        }
        if(!(v2 instanceof BoolValue (boolean rightTerm))){
            throw new LogicException("Second argument is not a boolean");
        }
        return switch(op){
            case "and" -> new BoolValue(leftTerm && rightTerm);
            case "or" -> new BoolValue(leftTerm || rightTerm);
            default -> throw new LogicException("Invalid logical operator");

        };

    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(e1.deepCopy(), e2.deepCopy(), op);
    }
}
