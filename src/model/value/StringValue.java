package model.value;

import model.type.IType;
import model.type.StringType;

public record StringValue(String val) implements IValue {

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(IValue another) {
       return another instanceof StringValue && this.val.equals(((StringValue) another).val);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StringValue that = (StringValue) obj;
        return val.equals(that.val);
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }
}
