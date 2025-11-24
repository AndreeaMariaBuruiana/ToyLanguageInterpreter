package controller;

import exception.EmptyStackException;
import exception.MyException;
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
                new ArrayListOut<IValue>(), new MapFileTable(), new MyHeap<>(), program));
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

        // Log the initial state
        if(displayFlag) {
            repository.logProgramState(programState);
        }

        try {
            while (!programState.executionStack().isEmpty()) {
                // 1. Execute one step
                programState = executeOneStep(programState);

                // 2. Log the state after execution
                if(displayFlag) {
                    repository.logProgramState(programState);
                }

                // 3. Run Garbage Collector (Try commenting this out if it still crashes!)
                IHeap<Integer,IValue> heap = programState.heap();
                heap.setHeap(heap.safeGarbageCollector(
                        programState.getUsedAddresses(),
                        heap.getHeap()
                ));
            }
        } catch (MyException e) {
            // THIS IS THE MISSING PIECE
            System.out.println("The program crashed: " + e.getMessage());
            //e.printStackTrace(); // This will print the red text in your console telling you the line number
        }

        if (!displayFlag) {
            repository.logProgramState(programState);
        }
    }

}
