package model.type;

import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType{

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }
}
