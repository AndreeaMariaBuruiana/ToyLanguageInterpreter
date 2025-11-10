package model.expression;

import exception.DictionaryException;
import model.state.IDictionary;
import model.value.IValue;

public record VariableExpression(String varName) implements IExpression {
    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolTable) {
        if(!(symbolTable.containsKey(varName))){
            throw new DictionaryException("The variable " + varName + " is not defined!");
        }
        return symbolTable.lookUp(varName);
    }
}
