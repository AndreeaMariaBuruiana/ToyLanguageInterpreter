package controller;

import exception.EmptyStackException;
import model.state.MapDictionary;
import model.state.*;
import model.statement.IStatement;
import model.value.IValue;
import repository.IRepository;

public record Controller(IRepository repository) {
    public void addProgramState(IStatement program) {
        var executionStack = new LinkedListExecutionStack<IStatement>();
        executionStack.push(program);
        repository.addProgramState(new ProgramState(executionStack, new MapDictionary<String, IValue>(),
                new ArrayListOut<IValue>(), program));
    }

    private ProgramState executeOneStep(ProgramState programState) {
        IExecutionStack<IStatement> executionStack = programState.executionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyStackException("Execution stack is empty.");
        }
        System.out.println("--- Next Step ---");
        System.out.println(programState.toString());
        IStatement statement = executionStack.pop();
        return statement.execute(programState);
    }

    public void executeAllSteps() {
        var programState = repository.getCurrentState();
        while (!programState.executionStack().isEmpty()) {
            programState = executeOneStep(programState);
            repository.updateCurrentState(programState);
        }
        System.out.println("--- Final State ---");
        System.out.println(programState.toString());
    }

    public void displayCurrentState() {
        var programState = repository.getCurrentState();
        System.out.println(programState);
    }

}
