package eventb.tools.formatters;

import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.NamedSet;
import eventb.expressions.sets.RangeSet;
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

    String visit(CustomSet customSet);

    String visit(NamedSet namedSet);

    String visit(RangeSet rangeSet);

    String visit(Machine machine);

}
