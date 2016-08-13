package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.INaryOperation;
import eventb.expressions.arith.AAssignable;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 23:30
 */
public final class And extends ABooleanExpression implements INaryOperation {

    private final List<ABooleanExpression> operands;

    public And(ABooleanExpression... operands) {
        this.operands = Arrays.asList(operands);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ABooleanExpression> getOperands() {
        return operands;
    }

    @Override
    public LinkedHashSet<AAssignable> getAssignables() {
        return new LinkedHashSet<>(getOperands().stream().map(AExpression::getAssignables).flatMap(Collection::stream).collect(Collectors.toList()));
    }

}
