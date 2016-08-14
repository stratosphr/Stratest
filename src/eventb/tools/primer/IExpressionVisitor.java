package eventb.tools.primer;

import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import graphs.AbstractState;
import graphs.ConcreteState;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 10:31
 */
public interface IExpressionVisitor<T> {

    T visit(ArithmeticITE arithmeticITE);

    T visit(FunctionCall functionCall);

    T visit(GreaterOrEqual greaterOrEqual);

    T visit(GreaterThan greaterThan);

    T visit(Int anInt);

    T visit(True aTrue);

    T visit(LowerOrEqual lowerOrEqual);

    T visit(LowerThan lowerThan);

    T visit(Multiplication multiplication);

    T visit(Subtraction subtraction);

    T visit(Variable variable);

    T visit(And and);

    T visit(Not not);

    T visit(Implication implication);

    T visit(Equals equals);

    T visit(AbstractState abstractState);

    T visit(ConcreteState concreteState);

    T visit(ForAll forAll);

}
