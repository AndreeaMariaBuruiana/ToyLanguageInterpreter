package model.value;

import model.type.Type;

public record BoolValue(boolean value) implements IValue {

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
