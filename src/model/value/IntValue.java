package model.value;

import model.type.IType;
import model.type.IntType;

public record IntValue(int value) implements IValue {
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }

    @Override
    public boolean equals(IValue another) {
        return another instanceof IntValue && this.value == ((IntValue) another).value;
    }
}
