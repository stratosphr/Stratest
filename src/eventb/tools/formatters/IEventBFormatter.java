package eventb.tools.formatters;

import eventb.Event;
import eventb.Machine;
import eventb.expressions.FunctionDefinition;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.NamedSet;
import eventb.expressions.sets.RangeSet;
import eventb.substitutions.*;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:25
 */
public interface IEventBFormatter extends IExpressionFormatter {

    String visit(Machine machine);

    String visit(Event event);

    String visit(Skip skip);

    String visit(Assignment assignment);

    String visit(MultipleAssignment multipleAssignment);

    String visit(Select select);

    String visit(IfThenElse ifThenElse);

    String visit(Any any);

    String visit(CustomSet customSet);

    String visit(NamedSet namedSet);

    String visit(RangeSet rangeSet);

    String visit(FunctionDefinition functionDefinition);

    String visit(Parallel parallel);

    String visit(Choice choice);

}
