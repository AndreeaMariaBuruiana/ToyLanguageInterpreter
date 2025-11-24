package model.statement;

import exception.FileException;
import exception.InvalidTypeException;
import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.StringType;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record CloseRFile(IExpression exp) implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var val = exp.evaluate(state.symbolTable(), state.heap());
        if(!val.getType().equals(new StringType())){
            throw new InvalidTypeException("CloseRFile: expression must evaluate to StringType");
        }
        StringValue filename = (StringValue) val;
        BufferedReader br = state.fileTable().getFile(filename);

        if(br == null){
            throw new FileException("CloseRFile: file " + filename + " is not opened");
        }

        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Error closing file: " + e.getMessage());
        }
        state.fileTable().close(filename);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }
}
