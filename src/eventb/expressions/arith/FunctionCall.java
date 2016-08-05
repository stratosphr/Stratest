package eventb.expressions.arith;

import eventb.expressions.AExpression;
import eventb.expressions.FunctionDefinition;
import eventb.expressions.INaryOperation;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 13:43
 */
public class FunctionCall extends AAssignable implements INaryOperation {

    private final FunctionDefinition definition;
    private final List<AExpression> operands;

    public FunctionCall(FunctionDefinition definition, AExpression... operands) {
        this.definition = definition;
        this.operands = Arrays.asList(operands);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    public FunctionDefinition getDefinition() {
        return definition;
    }

    @Override
    public List<AExpression> getOperands() {
        return operands;
    }

}
