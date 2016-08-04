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
import utilities.UCharacters;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:22
 */
public final class EventBFormatter extends AFormatter implements IEventBFormatter {

    @Override
    public String visit(Event event) {
        indentRight();
        String formatted = event.getName() + " " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + indent() + event.getSubstitution().accept(this);
        indentLeft();
        return formatted;
    }

    @Override
    public String visit(Skip skip) {
        return "SKIP";
    }

    @Override
    public String visit(Assignment assignment) {
        return assignment.getAssignable().accept(this) + " := " + assignment.getValue().accept(this);
    }

    @Override
    public String visit(MultipleAssignment multipleAssignment) {
        return multipleAssignment.getAssignments().stream().map(assignment -> assignment.accept(this)).collect(Collectors.joining(" ||" + UCharacters.LINE_SEPARATOR + indent()));
    }

    @Override
    public String visit(Select select) {
        String formatted = "SELECT" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + select.getCondition().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + "THEN" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + select.getSubstitution().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + "END";
        return formatted;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String formatted = "IF" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + ifThenElse.getCondition().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + "THEN" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + ifThenElse.getThenPart().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + "ELSE" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + ifThenElse.getElsePart().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + "END";
        return formatted;
    }

    @Override
    public String visit(True aTrue) {
        return "_true_";
    }

    @Override
    public String visit(Not not) {
        return UCharacters.LNOT + not.getOperand().accept(this);
    }

    @Override
    public String visit(And and) {
        return "(" + and.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" " + UCharacters.LAND + " ")) + ")";
    }

    @Override
    public String visit(Equals equals) {
        return "(" + equals.getLeft() + " = " + equals.getRight() + ")";
    }

    @Override
    public String visit(LowerThan lowerThan) {
        return "(" + lowerThan.getLeft().accept(this) + " < " + lowerThan.getRight().accept(this) + ")";
    }

    @Override
    public String visit(LowerOrEqual lowerOrEqual) {
        return "(" + lowerOrEqual.getLeft() + " " + UCharacters.LOWER_OR_EQUAL + " " + lowerOrEqual.getRight() + ")";
    }

    @Override
    public String visit(GreaterThan greaterThan) {
        return "(" + greaterThan.getLeft().accept(this) + " > " + greaterThan.getRight().accept(this) + ")";
    }

    @Override
    public String visit(GreaterOrEqual greaterOrEqual) {
        return "(" + greaterOrEqual.getLeft().accept(this) + " " + UCharacters.GREATER_OR_EQUAL + " " + greaterOrEqual.getRight().accept(this) + ")";
    }

    @Override
    public String visit(Implication implication) {
        return "(" + implication.getIfPart().accept(this) + " => " + implication.getThenPart().accept(this) + ")";
    }

    @Override
    public String visit(Variable variable) {
        return variable.getName();
    }

    @Override
    public String visit(Int anInt) {
        return String.valueOf(anInt.getValue());
    }

    @Override
    public String visit(Subtraction subtraction) {
        return "(" + subtraction.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" - ")) + ")";
    }

    @Override
    public String visit(Multiplication multiplication) {
        return "(" + multiplication.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" * ")) + ")";
    }

    @Override
    public String visit(CustomSet customSet) {
        return "{" + customSet.getElements().stream().map(anInt -> anInt.accept(this)).collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public String visit(NamedSet namedSet) {
        return "(" + namedSet.getName() + " : {" + namedSet.getElements().stream().map(anInt -> anInt.accept(this)).collect(Collectors.joining(", ")) + "})";
    }

    @Override
    public String visit(RangeSet rangeSet) {
        return rangeSet.getLowerBound() + ".." + rangeSet.getUpperBound();
    }

    @Override
    public String visit(Machine machine) {
        String formatted = "MACHINE" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + machine.getName() + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "SETS" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += machine.getSets().stream().map(namedSet -> indent() + namedSet.accept(this)).collect(Collectors.joining(UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "VARIABLES" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += machine.getAssignables().stream().map(assignable -> indent() + assignable.accept(this)).collect(Collectors.joining("," + UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "INVARIANT" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + machine.getInvariant().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "INITIALIZATION" + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + machine.getInitialization().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "EVENTS" + UCharacters.LINE_SEPARATOR;
        for (Event event : machine.getEvents()) {
            formatted += UCharacters.LINE_SEPARATOR;
            indentRight();
            formatted += indent() + event.accept(this) + UCharacters.LINE_SEPARATOR;
            indentLeft();
        }
        return formatted;
    }

}
