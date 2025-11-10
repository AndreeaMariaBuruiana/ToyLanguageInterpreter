package repository;

import exception.MyException;
import model.state.ProgramState;

public interface IRepository {
    void addProgramState(ProgramState state);
    ProgramState getCurrentState();
    void updateCurrentState(ProgramState state);
    void logProgramState(ProgramState prg) throws MyException;
}
