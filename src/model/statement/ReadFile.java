package model.statement;

import exception.DictionaryException;
import exception.FileException;
import exception.InvalidTypeException;
import exception.MyException;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFile(IExpression exp, String varName) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!state.symbolTable().isDefined(varName) ){
            throw new DictionaryException("Variable " + varName + " is not defined!");
        }
        var value = state.symbolTable().lookUp(varName);
        if(!value.getType().equals(new IntType())){
            throw new InvalidTypeException("Variable " + varName + " is not of type int!");
        }

        IValue fileNameVal = exp.evaluate(state.symbolTable(), state.heap());
        if(!fileNameVal.getType().equals(new StringType())){
            throw new InvalidTypeException("Variable " + varName + " is not of type String!");
        }
        StringValue filename = (StringValue) fileNameVal;
        BufferedReader br = state.fileTable().getFile(filename);

        if (br == null) {
            throw new FileException("File " + filename.val() + " is not opened");
        }

        try{
            String line = br.readLine();
            IntValue val;
            if(line == null){
                val = new IntValue(0);
            }else{
                try{
                    val = new IntValue((Integer.parseInt(line)));
                }catch(NumberFormatException e){
                    throw new InvalidTypeException("Line " + line + " is not an integer!");
                }
            }
            try {
                state.symbolTable().update(varName, val);
            } catch (DictionaryException e) {
                throw new MyException(e.getMessage());
            }

        }catch(IOException e){
            throw new FileException("Error reading file " + filename.val());
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }
}
