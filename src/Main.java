import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import view.View;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

void main(){
    View view = new View();

    IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", Type.INTEGER),
                 new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));


    IStatement ex2 = new CompoundStatement(
            new VariableDeclarationStatement("a", Type.INTEGER),
            new CompoundStatement(
                    new VariableDeclarationStatement("b",Type.INTEGER),
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

    IStatement ex3 = new CompoundStatement(
            new VariableDeclarationStatement("a",Type.BOOLEAN),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", Type.INTEGER),
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
    view.addProgram(1, "Example 1: int v; v=2; Print(v)", ex1);
    view.addProgram(2, "Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)", ex2);
    view.addProgram(3, "Example 3: bool a; int v; a=true; If a Then v=2 Else v=3; Print(v)", ex3);

    view.run();

}
