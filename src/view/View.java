package view;

import controller.Controller;
import exception.MyException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.ArrayListRepository;
import repository.IRepository;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedReader;
import java.util.*;

public class View {

    private static IStatement createExample1() {
        // int v; v=2; Print(v)
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    private static IStatement createExample2() {
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),'*')
                                                ,'+')
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),'+')
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

    }

    private static IStatement createExample3() {
        return new CompoundStatement(
                new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }

    private static IStatement createExample4() {
        // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf,
        // varc); Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
        return new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));
    }

    private static ProgramState createPrgState(IStatement originalProgram) {
        IExecutionStack<IStatement> exeStack = new LinkedListExecutionStack<>();
        IDictionary<String, IValue> symTable = new MapDictionary<>();
        IOut<IValue> output = new ArrayListOut<>();
        IFileTable fileTable = new MapFileTable();

        exeStack.push(originalProgram);

        return new ProgramState(exeStack, symTable, output, fileTable,originalProgram);
    }

    private static Controller createController(IStatement stmt, String logFilePath) {
        ProgramState prgState = createPrgState(stmt);
        List<ProgramState> prgStates = new ArrayList<>();
        prgStates.add(prgState);
        IRepository repo = new ArrayListRepository(prgStates, logFilePath);
        return new Controller(repo,true);
    }

    public void run(){
        TextMenu menu = new TextMenu();

        menu.addCommand(new RunExample("1", createExample1(),createController(createExample1(), "log1.txt")));
        menu.addCommand(new RunExample("2", createExample2(),createController(createExample2(), "log2.txt")));
        menu.addCommand(new RunExample("3", createExample3(),createController(createExample3(), "log3.txt")));
        menu.addCommand(new RunExample("4", createExample4(),createController(createExample4(), "log4.txt")));
        menu.addCommand(new ExitCommand("0","Exit"));

        menu.show();


    }


}