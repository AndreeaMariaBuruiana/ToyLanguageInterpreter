package controller;

import exception.EmptyStackException;
import model.state.MapDictionary;
import model.state.*;
import model.statement.IStatement;
import model.value.IValue;
import repository.IRepository;

public record Controller(IRepository repository, boolean displayFlag) {
    public void addProgramState(IStatement program) {
        var executionStack = new LinkedListExecutionStack<IStatement>();
        executionStack.push(program);
        repository.addProgramState(new ProgramState(executionStack, new MapDictionary<String, IValue>(),
                new ArrayListOut<IValue>(), new MapFileTable(), program));
    }

    private ProgramState executeOneStep(ProgramState programState) {
        IExecutionStack<IStatement> executionStack = programState.executionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyStackException("Execution stack is empty.");
        }
        IStatement statement = executionStack.pop();
        return statement.execute(programState);
    }

    public void executeAllSteps() {
        var programState = repository.getCurrentState();
        if(displayFlag) {
            repository.logProgramState(programState);
        }
        while (!programState.executionStack().isEmpty()) {
            programState = executeOneStep(programState);
            if(displayFlag) {
                repository.logProgramState(programState);
            }
        }
        if (!displayFlag) {
            repository.logProgramState(programState);
        }

    }

}
