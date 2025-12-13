package model.statement;

import exception.MyException;
import model.expression.IExpression;
import model.state.IDictionary;
import model.state.ProgramState;
import model.type.IType;

public record PrintStatement(IExpression exp) implements IStatement {


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.out().add(exp.evaluate(state.symbolTable(), state.heap()));
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
