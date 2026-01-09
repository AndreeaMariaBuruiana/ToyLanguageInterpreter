package controller;

import exception.EmptyStackException;
import exception.MyException;
import model.state.MapDictionary;
import model.state.*;
import model.statement.IStatement;
import model.value.IValue;
import repository.IRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repository;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repository,boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
        this.executor = Executors.newFixedThreadPool(2);
    }
    public void addProgramState(IStatement program) {
        var executionStack = new LinkedListExecutionStack<IStatement>();
        executionStack.push(program);
        repository.addProgramState(new ProgramState(executionStack, new MapDictionary<String, IValue>(),
                new ArrayListOut<IValue>(), new MapFileTable(), new MyHeap<>(), program));
    }

    public void oneStepForAllPrograms(List<ProgramState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repository.logProgramState(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (MyException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }))
                .toList();

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try { return future.get(); }
                    catch (Exception e) { return null; }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        List<ProgramState> currentMainList = repository.getProgramList();
        currentMainList.addAll(newPrgList);


        currentMainList.forEach(prg -> {
            try { repository.logProgramState(prg); }
            catch (MyException e) { System.out.println(e.getMessage()); }
        });


        repository.setProgramList(currentMainList);

    }



    public void executeAllSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrograms(repository.getProgramList());

        while(prgList.size()>0) {
            IHeap<Integer,IValue> heap = prgList.get(0).heap();
            Set<Integer> usedAddresses = prgList.stream()
                    .flatMap(p->p.getUsedAddresses().stream())
                    .collect(Collectors.toSet());
            heap.setHeap(heap.safeGarbageCollector(usedAddresses,heap.getHeap()));

            try{
                oneStepForAllPrograms(prgList);
            }catch (InterruptedException e){
                throw new MyException("Program execution interrupted: " + e.getMessage());
            }
            prgList = removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramList(prgList);

    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public IRepository getRepo() {
        return repository;
    }

}
