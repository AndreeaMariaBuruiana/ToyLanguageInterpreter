package model.type;

import model.value.IValue;
import model.value.StringValue;

public class StringType implements IType {

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
}
