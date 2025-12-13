package view.command;

import controller.Controller;
import exception.MyException;
import model.statement.IStatement;

public class RunExample extends Command {

    private Controller controller;
    private boolean hasBeenExecuted;

    public RunExample(String key, IStatement stmt, Controller controller) {
        super(key, stmt.toString());
        this.controller = controller;
        this.hasBeenExecuted = false;

    }

    @Override
    public void execute() {
        if(hasBeenExecuted){
            System.out.println("This example has already been executed.");
        }
        try {
            controller.executeAllSteps();
            hasBeenExecuted = true;

        }catch (MyException e){
            System.out.println("Error during execution: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasBeenExecuted(){
        return hasBeenExecuted;
    }
}
