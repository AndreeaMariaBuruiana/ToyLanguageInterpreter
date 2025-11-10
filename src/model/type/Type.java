package model.type;

import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public enum Type {
    INTEGER,
    BOOLEAN;

    public IValue getDefaultValue() {
        return switch(this){
            case INTEGER -> new IntValue(0);
            case BOOLEAN -> new BoolValue(false);
        };
    }
}
