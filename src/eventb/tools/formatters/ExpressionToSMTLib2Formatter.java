package eventb.tools.formatters;

import eventb.expressions.AExpression;
import eventb.expressions.IBinaryOperation;
import eventb.expressions.INaryOperation;
import eventb.expressions.IUnaryOperation;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import formatting.AFormatter;
import graphs.AState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static utilities.UCharacters.LINE_SEPARATOR;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 09:46
 */
public final class ExpressionToSMTLib2Formatter extends AFormatter implements IExpressionFormatter {

    private ExpressionToSMTLib2Formatter() {
    }

    public static String formatExpression(AExpression expression) {
        ExpressionToSMTLib2Formatter expressionToSMTLib2Formatter = new ExpressionToSMTLib2Formatter();
        String definitions = expressionToSMTLib2Formatter.getDefinitions(expression.getAssignables());
        return definitions.equals("") ? expression.accept(expressionToSMTLib2Formatter) : expressionToSMTLib2Formatter.getDefinitions(expression.getAssignables()) + LINE_SEPARATOR + expression.accept(expressionToSMTLib2Formatter);
    }

    private String getDefinitions(LinkedHashSet<AAssignable> assignables) {
        String definitions = "";
        for (AAssignable assignable : assignables) {
            // TODO : create an assignable visitor
            if (assignable instanceof Variable) {
                definitions += "(declare-fun " + ((Variable) assignable).getName() + " () Int)";
            } else if (assignable instanceof FunctionCall) {
                Variable index = new Variable("index");
                definitions += "(define-fun " + ((FunctionCall) assignable).getDefinition().getName() + " ((" + index.getName() + " Int)) Int" + LINE_SEPARATOR;
                ArrayList<Int> domainElements = new ArrayList<>(((FunctionCall) assignable).getDefinition().getDomain().getElements());
                ArithmeticITE body = new ArithmeticITE(new Equals(index, domainElements.get(domainElements.size() - 1)), new Variable(((FunctionCall) assignable).getDefinition().getName() + "!" + domainElements.get(domainElements.size() - 1)), new Int(-1));
                for (int i = domainElements.size() - 2; i >= 0; i--) {
                    body = new ArithmeticITE(new Equals(index, domainElements.get(i)), new Variable(((FunctionCall) assignable).getDefinition().getName() + "!" + domainElements.get(i)), body);
                }
                indentRight();
                definitions += indent() + body.accept(this) + LINE_SEPARATOR;
                indentLeft();
                definitions += ")";
            } else {
                throw new Error("Unhandled assignable type.");
            }
            definitions += LINE_SEPARATOR;
        }
        return definitions;
    }

    private String visitUnaryOperation(IUnaryOperation operation, String operator) {
        return "(" + operator + " " + operation.getOperand().accept(this) + ")";
    }

    private String visitBinaryOperation(IBinaryOperation operation, String operator) {
        String formatted = "(" + operator + LINE_SEPARATOR;
        indentRight();
        formatted += indent() + operation.getLeft().accept(this) + LINE_SEPARATOR;
        formatted += indent() + operation.getRight().accept(this) + LINE_SEPARATOR;
        indentLeft();
        formatted += indent() + ")";
        return formatted;
    }

    private String visitNaryOperation(INaryOperation operation, String operator) {
        if (operation.getOperands().isEmpty()) {
            return operator;
        } else {
            String formatted = "(" + operator + LINE_SEPARATOR;
            indentRight();
            formatted += operation.getOperands().stream().map(operand -> indent() + operand.accept(this)).collect(Collectors.joining(LINE_SEPARATOR)) + LINE_SEPARATOR;
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
    public String visit(Or or) {
        return visitNaryOperation(or, "or");
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
    public String visit(GreaterOrEqual greaterOrEqual) {
        return visitBinaryOperation(greaterOrEqual, ">=");
    }

    @Override
    public final String visit(Implication implication) {
        return visitBinaryOperation(implication, "=>");
    }

    @Override
    public String visit(AState aState) {
        return aState.getExpression().accept(this);
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
    public String visit(Sum sum) {
        return visitNaryOperation(sum, "+");
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
    public String visit(ForAll forAll) {
        String formatted = "(forall" + LINE_SEPARATOR;
        indentRight();
        formatted += indent() + "(" + forAll.getQuantifiedVariables().stream().map(variable -> "(" + variable.getName() + " Int)").collect(Collectors.joining(LINE_SEPARATOR + indent())) + ")" + LINE_SEPARATOR;
        formatted += indent() + forAll.getExpression().accept(this) + LINE_SEPARATOR;
        indentLeft();
        formatted += ")";
        return formatted;
    }

    @Override
    public String visit(Predicate predicate) {
        return predicate.getExpression().accept(this);
    }

    @Override
    public final String visit(FunctionCall functionCall) {
        return visitNaryOperation(functionCall, functionCall.getDefinition().getName());
    }

}
