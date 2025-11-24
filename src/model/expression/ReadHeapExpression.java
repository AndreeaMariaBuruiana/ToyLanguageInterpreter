package model.expression;

import exception.MyException;
import model.state.IDictionary;
import model.state.IHeap;
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
    public IExpression deepCopy() {
        return new ReadHeapExpression(expr.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + expr.toString() + ")";
    }
}
