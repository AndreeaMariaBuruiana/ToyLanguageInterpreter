package model.value;

import model.type.BoolType;
import model.type.IType;

public record BoolValue(boolean value) implements IValue {

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(value);
    }

    @Override
    public boolean equals(IValue another) {
        return another instanceof BoolValue && ((BoolValue) another).value == value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
