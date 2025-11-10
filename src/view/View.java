package view;

import controller.Controller;
import exception.MyException;
import model.statement.IStatement;
import repository.ArrayListRepository;
import repository.IRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class View {
    private final Map<Integer, IStatement> programs;
    private final Map<Integer, String> programDescriptions;
    private final Scanner scanner;

    public View() {
        this.programs = new HashMap<>();
        this.programDescriptions = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if ("0".equals(choice)) {
                System.out.println("Exiting interpreter...");
                return;
            }

            try {
                int programId = Integer.parseInt(choice);
                runProgram(programId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option! Please enter a number.");
            } catch (Exception e) {
                System.err.println("An error occurred during execution: " + e.getMessage());

            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Toy Language Interpreter ===");
        for (Map.Entry<Integer, String> entry : programDescriptions.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        System.out.println("0. Exit");
    }

    private void runProgram(int id) {
        IStatement selectedStatement = programs.get(id);
        if (selectedStatement == null) {
            System.out.println("No program found for ID " + id);
            return;
        }

        System.out.println("\n=== Running: " + programDescriptions.get(id) + " ===\n");

        try {
            IRepository repository = new ArrayListRepository();


            Controller controller = new Controller(repository);


            controller.addProgramState(selectedStatement);


            controller.executeAllSteps();

        } catch (MyException e) {
            System.err.println("Execution Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    public void addProgram(int id, String description, IStatement programStatement) {
        programs.put(id, programStatement);
        programDescriptions.put(id, description);
    }


}
