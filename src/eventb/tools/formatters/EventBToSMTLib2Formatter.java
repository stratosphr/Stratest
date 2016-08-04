package eventb.tools.formatters;

import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.NamedSet;
import eventb.expressions.sets.RangeSet;
import eventb.substitutions.*;
import formatting.AFormatter;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:22
 */
public final class EventBToSMTLib2Formatter extends AFormatter implements IEventBFormatter {

    @Override
    public String visit(Event event) {
        return null;
    }

    @Override
    public String visit(Skip skip) {
        return null;
    }

    @Override
    public String visit(Assignment assignment) {
        return null;
    }

    @Override
    public String visit(MultipleAssignment multipleAssignment) {
        return null;
    }

    @Override
    public String visit(Select select) {
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        return null;
    }

    @Override
    public String visit(True aTrue) {
        return null;
    }

    @Override
    public String visit(Not not) {
        return null;
    }

    @Override
    public String visit(And and) {
        return null;
    }

    @Override
    public String visit(Equals equals) {
        return null;
    }

    @Override
    public String visit(LowerThan lowerThan) {
        return null;
    }

    @Override
    public String visit(LowerOrEqual lowerOrEqual) {
        return null;
    }

    @Override
    public String visit(GreaterThan greaterThan) {
        return null;
    }

    @Override
    public String visit(GreaterOrEqual greaterOrEqual) {
        return null;
    }

    @Override
    public String visit(Implication implication) {
        return null;
    }

    @Override
    public String visit(Variable variable) {
        return null;
    }

    @Override
    public String visit(Int anInt) {
        return null;
    }

    @Override
    public String visit(Subtraction subtraction) {
        return null;
    }

    @Override
    public String visit(Multiplication multiplication) {
        return null;
    }

    @Override
    public String visit(CustomSet customSet) {
        return null;
    }

    @Override
    public String visit(NamedSet namedSet) {
        return null;
    }

    @Override
    public String visit(RangeSet rangeSet) {
        return null;
    }

    @Override
    public String visit(Machine machine) {
        return null;
    }

}
