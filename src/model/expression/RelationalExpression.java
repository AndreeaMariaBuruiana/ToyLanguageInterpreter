package model.expression;

import exception.ArithmeticException;
import exception.MyException;
import exception.RelationalException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.type.IntType;
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
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType t1,t2;
        t1=e1.typecheck(typeEnv);
        t2=e2.typecheck(typeEnv);
        if(t1.equals(new IntType()) && t2.equals(new IntType())){
            return new IntType();
        }
        else
            throw new MyException("The operands of a relational expression must be integers.");
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }
}
