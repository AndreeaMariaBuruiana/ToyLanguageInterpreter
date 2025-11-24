package model.statement;

import exception.FileAlreadyOpen;
import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public record OpenRFile(IExpression exp) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var val = exp.evaluate(state.symbolTable(), state.heap());
        if (!val.getType().toString().equals("String")) {
            throw new MyException("File name expression is not a string!");
        }
        StringValue stringVal = (StringValue) val;
        if(state.fileTable().isOpen(stringVal)){
            throw new FileAlreadyOpen("File is already opened!");
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(stringVal.val()));
            state.fileTable().add(stringVal, br);
        }catch(Exception e){
            throw new MyException("File not found!");
        }
        return state;

    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile(" + exp.toString() + ")";
    }
}
