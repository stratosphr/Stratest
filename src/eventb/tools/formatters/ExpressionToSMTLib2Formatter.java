package eventb.tools.formatters;

import eventb.expressions.FunctionDefinition;
import eventb.expressions.IBinaryOperation;
import eventb.expressions.INaryOperation;
import eventb.expressions.IUnaryOperation;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import formatting.AFormatter;
import utilities.UCharacters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 09:46
 */
public final class ExpressionToSMTLib2Formatter extends AFormatter implements IExpressionFormatter {

    private final List<FunctionDefinition> functionDefinitions;

    public ExpressionToSMTLib2Formatter() {
        this.functionDefinitions = new ArrayList<>();
    }

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
        if (operation.getOperands().isEmpty()) {
            return operator;
        } else {
            String formatted = "(" + operator + UCharacters.LINE_SEPARATOR;
            indentRight();
            formatted += operation.getOperands().stream().map(operand -> indent() + operand.accept(this)).collect(Collectors.joining(UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
            indentLeft();
            formatted += indent() + ")";
            return formatted;
        }
    }

    @Override
    public final String visit(True aTrue) {
        return "true";
    }

    @Override
    public final String visit(Not not) {
        return visitUnaryOperation(not, "not");
    }

    @Override
    public final String visit(And and) {
        return visitNaryOperation(and, "and");
    }

    @Override
    public final String visit(Equals equals) {
        return visitBinaryOperation(equals, "=");
    }

    @Override
    public final String visit(LowerThan lowerThan) {
        return visitBinaryOperation(lowerThan, "<");
    }

    @Override
    public final String visit(LowerOrEqual lowerOrEqual) {
        return visitBinaryOperation(lowerOrEqual, "<=");
    }

    @Override
    public final String visit(GreaterThan greaterThan) {
        return visitBinaryOperation(greaterThan, ">");
    }

    @Override
    public final String visit(GreaterOrEqual greaterOrEqual) {
        return visitBinaryOperation(greaterOrEqual, ">=");
    }

    @Override
    public final String visit(Implication implication) {
        return visitBinaryOperation(implication, "=>");
    }

    @Override
    public final String visit(Variable variable) {
        return variable.getName();
    }

    @Override
    public final String visit(Int anInt) {
        return String.valueOf(anInt.getValue());
    }

    @Override
    public final String visit(Subtraction subtraction) {
        return visitNaryOperation(subtraction, "-");
    }

    @Override
    public final String visit(Multiplication multiplication) {
        return visitNaryOperation(multiplication, "*");
    }

    @Override
    public String visit(ArithmeticITE arithmeticITE) {
        return visitNaryOperation(() -> Arrays.asList(arithmeticITE.getCondition(), arithmeticITE.getThenPart(), arithmeticITE.getElsePart()), "ite");
    }

    @Override
    public String visit(FunctionDefinition functionDefinition) {
        Variable index = new Variable("index");
        String formatted = functionDefinition.getDomain().getElements().stream().map(anInt -> "(declare-fun " + functionDefinition.getName() + "!" + anInt + " () Int)").collect(Collectors.joining(UCharacters.LINE_SEPARATOR)) + UCharacters.LINE_SEPARATOR;
        formatted += "(define-fun " + functionDefinition.getName() + " ((" + index.getName() + " Int)) Int" + UCharacters.LINE_SEPARATOR;
        List<Int> elements = new ArrayList<>(functionDefinition.getDomain().getElements());
        ArithmeticITE body = new ArithmeticITE(new Equals(index, elements.get(elements.size() - 1)), new Variable(functionDefinition.getName() + "!" + elements.get(elements.size() - 1)), new Int(-1));
        for (int i = elements.size() - 2; i >= 0; i--) {
            body = new ArithmeticITE(new Equals(index, elements.get(i)), new Variable(functionDefinition.getName() + "!" + elements.get(i)), body);
        }
        indentRight();
        formatted += indent() + body.accept(this) + UCharacters.LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + ")";
        return formatted;
    }

    @Override
    public final String visit(FunctionCall functionCall) {
        return visitNaryOperation(functionCall, functionCall.getDefinition().getName());
    }

}
