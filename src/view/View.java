package view;

import controller.Controller;
import exception.MyException;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.*;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static IStatement createExample5() {
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v), print(a);
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new model.type.RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new model.type.RefType(new model.type.RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample6() {
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)), print(rH(rH(a))+5);
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new model.type.RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new model.type.RefType(new model.type.RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(
                                                                        new ReadHeapExpression(new VariableExpression("a"))
                                                                ),
                                                                new ValueExpression(new IntValue(5)),
                                                                '+'
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample7() {
        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new model.type.RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        '+'
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample8() {
        //int v; v = 4; (while (v > 0) print(v); v = v - 1); print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                ">"
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                '-')
                                                )
                                )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
    }

    private static IStatement createExample9() {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new model.type.RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new model.type.RefType(new model.type.RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample10() {
        // int v; Ref int a; v=10; new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
        )
        )
                ;


    }

    private static IStatement createExampleFail() {
        // int v; v = true;  <-- ERROR: mismatch types
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new BoolValue(true)))
        );
    }

    private static ProgramState createPrgState(IStatement originalProgram) {
        IDictionary<String, IType> typeEnv = new MapDictionary<>();
        originalProgram.typeCheck(typeEnv);

        IExecutionStack<IStatement> exeStack = new LinkedListExecutionStack<>();
        IDictionary<String, IValue> symTable = new MapDictionary<>();
        IOut<IValue> output = new ArrayListOut<>();
        IFileTable fileTable = new MapFileTable();
        IHeap<Integer,IValue> heap = new MyHeap<>();

        exeStack.push(originalProgram);

        return new ProgramState(exeStack, symTable, output, fileTable,heap,originalProgram);
    }

    private static Controller createController(IStatement stmt, String logFilePath) {
        try {
            ProgramState prgState = createPrgState(stmt);
            List<ProgramState> prgStates = new ArrayList<>();
            prgStates.add(prgState);
            IRepository repo = new ArrayListRepository(prgStates, logFilePath);
            return new Controller(repo, true);

        } catch (MyException e) {
            System.out.println("Type Check Failed: " + e.getMessage());
            return null;
        }
    }

    public void run(){
        TextMenu menu = new TextMenu();

        menu.addCommand(new RunExample("1", createExample1(),createController(createExample1(), "log1.txt")));
        menu.addCommand(new RunExample("2", createExample2(),createController(createExample2(), "log2.txt")));
        menu.addCommand(new RunExample("3", createExample3(),createController(createExample3(), "log3.txt")));
        menu.addCommand(new RunExample("4", createExample4(),createController(createExample4(), "log4.txt")));
        menu.addCommand(new RunExample("5", createExample5(),createController(createExample5(), "log5.txt")));
        menu.addCommand(new RunExample("6", createExample6(),createController(createExample6(), "log6.txt")));
        menu.addCommand(new RunExample("7", createExample7(),createController(createExample7(), "log7.txt")));
        menu.addCommand(new RunExample("8", createExample8(),createController(createExample8(), "log8.txt")));
        menu.addCommand(new RunExample("9", createExample9(),createController(createExample9(), "log9.txt")));
        menu.addCommand(new RunExample("10", createExample10(),createController(createExample10(), "log10.txt")));
        menu.addCommand(new RunExample("11", createExampleFail(),createController(createExampleFail(), "logFail.txt")));
        menu.addCommand(new ExitCommand("0","Exit"));

        menu.show();


    }


}