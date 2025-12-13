package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public record AssignmentStatement(String valueName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        var expressionType = value.getType();

        if (!state.symbolTable().isDefined(valueName)) {
            throw new MyException("Variable " + valueName + " is not defined!");
        }

        var variableType = state.symbolTable().lookUp(valueName).getType();

        if (!expressionType.equals(variableType)) {
            throw new MyException("Different types! Cannot assign " + expressionType + " to " + variableType);
        }

        state.symbolTable().put(valueName, value);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.lookUp(valueName);
        IType exprType = expression.typecheck(typeEnv);
        if (varType.equals(exprType)) {
            return typeEnv;
        } else {
            throw new MyException("Assignment: right hand side and left hand side have different types!");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(valueName,expression.deepCopy());
    }

    @Override
    public String toString() {
        return valueName + " = " + expression.toString();
    }
}
