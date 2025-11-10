package repository;

import model.state.ProgramState;

public interface IRepository {
    void addProgramState(ProgramState state);
    ProgramState getCurrentState();
    void updateCurrentState(ProgramState state);
}
