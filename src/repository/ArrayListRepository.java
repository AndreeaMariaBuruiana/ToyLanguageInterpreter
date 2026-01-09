package repository;

import exception.FileException;
import exception.MyException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListRepository implements IRepository {
    public  List<ProgramState> programStates;
    public String logFilePath;
    public ArrayListRepository(List<ProgramState> states,String logFilePath) {
        this.programStates = states;
        this.logFilePath = logFilePath;
    }
    @Override
    public void addProgramState(ProgramState state) {
        programStates.add(state);
    }

    @Override
    public ProgramState getCurrentState() {
        return programStates.getFirst();
    }

    @Override
    public void updateCurrentState(ProgramState state) {
        if (programStates.isEmpty()) {
            this.programStates.add(state);
        } else {
            this.programStates.set(0, state);
        }
    }

    @Override
    public void logProgramState(ProgramState prg) throws MyException {
        if(logFilePath == null || logFilePath.isEmpty()) {
            setLogFilePath();
        }
        PrintWriter logFile;

        try{
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }catch (IOException e){
            throw new FileException("Could not open log file: " + logFilePath);
        }
        logFile.println(prg.toString());
        logFile.close();

    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    public void setLogFilePath() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Log File Path: ");
        logFilePath = sc.nextLine();
        sc.close();
    }
}
