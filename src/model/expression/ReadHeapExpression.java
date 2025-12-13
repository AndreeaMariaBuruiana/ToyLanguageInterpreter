package model.expression;

import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record ReadHeapExpression(IExpression expr) implements IExpression {

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) {
        var v1 = expr.evaluate(symbolTable, heap);
        if (! (v1 instanceof RefValue)){
            throw new MyException("The evaluated expression is not a RefValue");
        }
        Integer address = ((RefValue) v1).address();
        if (! heap.isDefined(address)){
            throw new MyException("The address is not defined in the heap");
        }
        return heap.get(address);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType t1;
        t1 = expr.typecheck(typeEnv);
        if (t1 instanceof RefType refType){
            return refType.getInner();
        } else {
            throw new MyException("The rH argument is not a RefType");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expr.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + expr.toString() + ")";
    }
}
