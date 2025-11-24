package model.value;

import model.type.IType;
import model.type.RefType;

public record RefValue(int address,IType locationType) implements IValue {

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(IValue another) {
        return another instanceof RefValue && ((RefValue) another).address == this.address;
    }

    @Override
    public String toString() {
        return "RefValue(" + address+ ", " + locationType.toString() + ")";
    }

}
