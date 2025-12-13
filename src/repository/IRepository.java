package repository;

import exception.MyException;
import model.state.ProgramState;

import java.util.List;

public interface IRepository {
    void addProgramState(ProgramState state);
    ProgramState getCurrentState();
    void updateCurrentState(ProgramState state);
    void logProgramState(ProgramState prg) throws MyException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStates);
}
