package model.expression;

import exception.ArithmeticException;
import exception.RelationalException;
import model.state.IDictionary;
import model.state.IHeap;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

import java.beans.Expression;

public record RelationalExpression(IExpression e1, IExpression e2, String op) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        IValue v1, v2;
        v1 = e1.evaluate(symbolTable, heap);
        v2 = e2.evaluate(symbolTable, heap);

        if (!(v1 instanceof IntValue(int leftTerm))) {
            throw new ArithmeticException("Left value is not an integer");
        }
        if (!(v2 instanceof IntValue(int rightTerm))) {
            throw new ArithmeticException("Right value is not an integer");
        }

        return switch (op) {
            case "<" -> new BoolValue(leftTerm < rightTerm);
            case "<=" -> new BoolValue(leftTerm <= rightTerm);
            case "==" -> new BoolValue(leftTerm == rightTerm);
            case "!=" -> new BoolValue(leftTerm != rightTerm);
            case ">" -> new BoolValue(leftTerm > rightTerm);
            case ">=" -> new BoolValue(leftTerm >= rightTerm);
            default -> throw new RelationalException("Unknown relational operator");
        };
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }
}
