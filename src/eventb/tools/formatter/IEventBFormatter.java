package eventb.tools.formatter;

import eventb.Event;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import eventb.substitutions.*;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:25
 */
public interface IEventBFormatter {

    String visit(Event event);

    String visit(Skip skip);

    String visit(Assignment assignment);

    String visit(MultipleAssignment multipleAssignment);

    String visit(Select select);

    String visit(IfThenElse ifThenElse);

    String visit(True aTrue);

    String visit(Not not);

    String visit(And and);

    String visit(Equals equals);

    String visit(LowerThan lowerThan);

    String visit(LowerOrEqual lowerOrEqual);

    String visit(GreaterThan greaterThan);

    String visit(GreaterOrEqual greaterOrEqual);

    String visit(Implication implication);

    String visit(Variable variable);

    String visit(Int anInt);

    String visit(Subtraction subtraction);

    String visit(Multiplication multiplication);

}
