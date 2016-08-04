package eventb.tools.formatters;

import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import formatting.AFormatter;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 13:12
 */
public class EventBToSMTLib2Formatter extends AFormatter implements IExpressionFormatter {

    @Override
    public String visit(True aTrue) {
        return "true";
    }

    @Override
    public String visit(Not not) {
        return "(not " + not.getOperand().accept(this) + ")";
    }

    @Override
    public String visit(And and) {
        return and.getOperands().isEmpty() ? "and" : "(and " + and.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" ")) + ")";
    }

    @Override
    public String visit(Equals equals) {
        return "(= " + equals.getLeft() + " " + equals.getRight() + ")";
    }

    @Override
    public String visit(LowerThan lowerThan) {
        return "(< " + lowerThan.getLeft() + " " + lowerThan.getRight() + ")";
    }

    @Override
    public String visit(LowerOrEqual lowerOrEqual) {
        return "(<= " + lowerOrEqual.getLeft() + " " + lowerOrEqual.getRight() + ")";
    }

    @Override
    public String visit(GreaterThan greaterThan) {
        return "(> " + greaterThan.getLeft() + " " + greaterThan.getRight() + ")";
    }

    @Override
    public String visit(GreaterOrEqual greaterOrEqual) {
        return "(>= " + greaterOrEqual.getLeft() + " " + greaterOrEqual.getRight() + ")";
    }

    @Override
    public String visit(Implication implication) {
        return "(=> " + implication.getIfPart().accept(this) + " " + implication.getThenPart().accept(this) + ")";
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
        return "(- " + subtraction.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" ")) + ")";
    }

    @Override
    public String visit(Multiplication multiplication) {
        return "(* " + multiplication.getOperands().stream().map(operand -> operand.accept(this)).collect(Collectors.joining(" ")) + ")";
    }

}
