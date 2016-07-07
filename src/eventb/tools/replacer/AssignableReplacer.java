package eventb.tools.replacer;

import eventb.expressions.AExpression;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 11:47
 */
public final class AssignableReplacer implements IAssignableReplacer {

    private final AAssignable assignable;
    private final AArithmeticExpression substitute;

    public AssignableReplacer(AAssignable assignable, AArithmeticExpression substitute) {
        this.assignable = assignable;
        this.substitute = substitute;
    }

    @Override
    public AExpression visit(True aTrue) {
        return aTrue;
    }

    @Override
    public AExpression visit(Not not) {
        return new Not((ABooleanExpression) not.getOperand().accept(this));
    }

    @Override
    public AExpression visit(And and) {
        return new And(and.getOperands().stream().map(operand -> operand.accept(this)).toArray(ABooleanExpression[]::new));
    }

    @Override
    public AExpression visit(Equals equals) {
        return new Equals((AArithmeticExpression) equals.getLeft().accept(this), (AArithmeticExpression) equals.getRight().accept(this));
    }

    @Override
    public AExpression visit(LowerThan lowerThan) {
        return new LowerThan((AArithmeticExpression) lowerThan.getLeft().accept(this), (AArithmeticExpression) lowerThan.getRight().accept(this));
    }

    @Override
    public AExpression visit(LowerOrEqual lowerOrEqual) {
        return new LowerOrEqual((AArithmeticExpression) lowerOrEqual.getLeft().accept(this), (AArithmeticExpression) lowerOrEqual.getRight().accept(this));
    }

    @Override
    public AExpression visit(GreaterThan greaterThan) {
        return new GreaterThan((AArithmeticExpression) greaterThan.getLeft().accept(this), (AArithmeticExpression) greaterThan.getRight().accept(this));
    }

    @Override
    public AExpression visit(GreaterOrEqual greaterOrEqual) {
        return new GreaterOrEqual((AArithmeticExpression) greaterOrEqual.getLeft().accept(this), (AArithmeticExpression) greaterOrEqual.getRight().accept(this));
    }

    @Override
    public AExpression visit(Implication implication) {
        return new Implication((ABooleanExpression) implication.getIfPart().accept(this), (ABooleanExpression) implication.getThenPart().accept(this));
    }

    @Override
    public AExpression visit(Int anInt) {
        return anInt;
    }

    @Override
    public AExpression visit(Variable variable) {
        if (variable.equals(getAssignable())) {
            return getSubstitute();
        } else {
            return variable;
        }
    }

    @Override
    public AExpression visit(Subtraction subtraction) {
        return new Subtraction(subtraction.getOperands().stream().map(operand -> operand.accept(this)).toArray(AArithmeticExpression[]::new));
    }

    @Override
    public AExpression visit(Multiplication multiplication) {
        return new Multiplication(multiplication.getOperands().stream().map(operand -> operand.accept(this)).toArray(AArithmeticExpression[]::new));
    }

    public AAssignable getAssignable() {
        return assignable;
    }

    public AArithmeticExpression getSubstitute() {
        return substitute;
    }

}
