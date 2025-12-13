package model.expression;

import exception.ArithmeticException;
import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public record ArithmeticExpression(IExpression left, IExpression right, char op) implements IExpression{

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer,IValue> heap) {
        IValue v1, v2;
        v1 = left.evaluate(symbolTable,heap);
        v2 = right.evaluate(symbolTable,heap);
        if (!(v1 instanceof IntValue(int leftTerm))) {
            throw new ArithmeticException("Left value is not an integer");
        }
        if (!(v2 instanceof IntValue(int rightTerm))) {
            throw new ArithmeticException("Right value is not an integer");
        }
        return switch (op) {
            case '+' -> new IntValue(leftTerm + rightTerm);
            case '-' -> new IntValue(leftTerm - rightTerm);
            case '*' -> new IntValue(leftTerm * rightTerm);
            case '/' -> divide(leftTerm, rightTerm);
            default -> throw new ArithmeticException("Unknown operator");
        };
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType t1, t2;
        t1 =left.typecheck(typeEnv);
        t2 = right.typecheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new IntType();
            } else {
                throw new MyException("Right operand is not an integer");
            }
        } else {
            throw new MyException("Left operand is not an integer");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(left.deepCopy(), right.deepCopy(), op);
    }

    private static IntValue divide(int l, int r) {
        if(r == 0){
            throw new ArithmeticException("Division by zero");
        }
        return new IntValue(l / r);
    }


}
