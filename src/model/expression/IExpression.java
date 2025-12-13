package model.expression;

import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String,IValue> symbolTable, IHeap<Integer,IValue> heap);
    IType typecheck(IDictionary<String,IType> typeEnv) throws MyException;

    IExpression deepCopy();
}
