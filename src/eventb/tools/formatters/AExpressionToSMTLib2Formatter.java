package eventb.tools.formatters;

import eventb.expressions.IBinaryOperation;
import eventb.expressions.INaryOperation;
import eventb.expressions.IUnaryOperation;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import formatting.AFormatter;
import utilities.UCharacters;

import java.util.stream.Collectors;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 09:46
 */
public abstract class AExpressionToSMTLib2Formatter extends AFormatter implements IExpressionFormatter {

    private String visitUnaryOperation(IUnaryOperation operation, String operator) {
        return "(" + operator + " " + operation.getOperand().accept(this) + ")";
    }

    private String visitBinaryOperation(IBinaryOperation operation, String operator) {
        String formatted = "(" + operator + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += indent() + operation.getLeft().accept(this) + UCharacters.LINE_SEPARATOR;
        formatted += indent() + operation.getRight().accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + ")";
        return formatted;
    }

    private String visitNaryOperation(INaryOperation operation, String operator) {
        String formatted = "(" + operator + UCharacters.LINE_SEPARATOR;
        indentRight();
        formatted += operation.getOperands().stream().map(operand -> indent() + operand.accept(this)).collect(Collectors.joining(UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + ")";
        return formatted;
    }

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
        if (and.getOperands().isEmpty()) {
            return "and";
        } else {
            String formatted = "(and" + UCharacters.LINE_SEPARATOR;
            indentRight();
            formatted += and.getOperands().stream().map(operand -> indent() + operand.accept(this)).collect(Collectors.joining(UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
            indentLeft();
            formatted += indent() + ")";
            return formatted;
        }
    }

    @Override
    public String visit(Equals equals) {
        return visitBinaryOperation(equals, "=");
    }

    @Override
    public String visit(LowerThan lowerThan) {
        return visitBinaryOperation(lowerThan, "<");
    }

    @Override
    public String visit(LowerOrEqual lowerOrEqual) {
        return visitBinaryOperation(lowerOrEqual, "<=");
    }

    @Override
    public String visit(GreaterThan greaterThan) {
        return visitBinaryOperation(greaterThan, ">");
    }

    @Override
    public String visit(GreaterOrEqual greaterOrEqual) {
        return visitBinaryOperation(greaterOrEqual, ">=");
    }

    @Override
    public String visit(Implication implication) {
        return visitBinaryOperation(implication, "=>");
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
