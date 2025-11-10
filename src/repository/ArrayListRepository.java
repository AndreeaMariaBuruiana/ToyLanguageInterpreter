package repository;

import model.state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class ArrayListRepository implements IRepository {
    public final List<ProgramState> programStates = new ArrayList<>();
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
}
