package eventb.tools.replacer;

import eventb.expressions.AExpression;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 12:01
 */
public interface IAssignableReplacer {

    AExpression visit(True aTrue);

    AExpression visit(Not not);

    AExpression visit(And and);

    AExpression visit(Equals equals);

    AExpression visit(LowerThan lowerThan);

    AExpression visit(LowerOrEqual lowerOrEqual);

    AExpression visit(GreaterThan greaterThan);

    AExpression visit(GreaterOrEqual greaterOrEqual);

    AExpression visit(Implication implication);

    AExpression visit(Int anInt);

    AExpression visit(Variable variable);

    AExpression visit(Subtraction subtraction);

    AExpression visit(Multiplication multiplication);

    AExpression visit(FunctionCall functionCall);

    AExpression visit(ArithmeticITE arithmeticITE);

}
