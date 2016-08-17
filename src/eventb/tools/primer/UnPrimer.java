package eventb.tools.primer;

import eventb.expressions.AExpression;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import graphs.AbstractState;
import graphs.ConcreteState;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 00:39
 */
public final class UnPrimer implements IExpressionToExpressionVisitor {

    private Set<AAssignable> quantifiedVariables;

    public UnPrimer() {
        this.quantifiedVariables = new LinkedHashSet<>();
    }

    @Override
    public AExpression visit(True aTrue) {
        return aTrue;
    }

    @Override
    public AExpression visit(Predicate predicate) {
        return new Predicate(predicate.getName(), (ABooleanExpression) predicate.getExpression().accept(this));
    }

    @Override
    public AExpression visit(Not not) {
        return new Not((ABooleanExpression) not.getOperand().accept(this));
    }

    @Override
    public AExpression visit(And and) {
        return new And(and.getOperands().stream().map(parameter -> (ABooleanExpression) parameter.accept(this)).toArray(ABooleanExpression[]::new));
    }

    @Override
    public AExpression visit(Or or) {
        return new Or(or.getOperands().stream().map(parameter -> (ABooleanExpression) parameter.accept(this)).toArray(ABooleanExpression[]::new));
    }

    @Override
    public AExpression visit(Implication implication) {
        return new Implication((ABooleanExpression) implication.getLeft().accept(this), (ABooleanExpression) implication.getRight().accept(this));
    }

    @Override
    public AExpression visit(ArithmeticITE ite) {
        return new ArithmeticITE((ABooleanExpression) ite.getCondition().accept(this), (AArithmeticExpression) ite.getThenPart().accept(this), (AArithmeticExpression) ite.getElsePart().accept(this));
    }

    @Override
    public AExpression visit(Equals equals) {
        return new Equals((AArithmeticExpression) equals.getLeft().accept(this), (AArithmeticExpression) equals.getRight().accept(this));
    }

    @Override
    public AExpression visit(AbstractState abstractState) {
        return new AbstractState(abstractState.getName(), (ABooleanExpression) abstractState.getExpression().accept(this));
    }

    @Override
    public AExpression visit(ConcreteState concreteState) {
        return new ConcreteState(concreteState.getName(), (ABooleanExpression) concreteState.getExpression().accept(this));
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
    public AExpression visit(Exists exists) {
        Set<AAssignable> oldQuantifiedVariables = quantifiedVariables;
        quantifiedVariables = new HashSet<>();
        quantifiedVariables.addAll(oldQuantifiedVariables);
        quantifiedVariables.addAll(exists.getQuantifiedVariables());
        Exists unprimed = new Exists((ABooleanExpression) exists.getExpression().accept(this), exists.getQuantifiedVariables().toArray(new Variable[exists.getQuantifiedVariables().size()]));
        quantifiedVariables = new HashSet<>(oldQuantifiedVariables);
        return unprimed;
    }

    @Override
    public AExpression visit(ForAll forAll) {
        Set<AAssignable> oldQuantifiedVariables = quantifiedVariables;
        quantifiedVariables = new HashSet<>();
        quantifiedVariables.addAll(oldQuantifiedVariables);
        quantifiedVariables.addAll(forAll.getQuantifiedVariables());
        ForAll unprimed = new ForAll((ABooleanExpression) forAll.getExpression().accept(this), forAll.getQuantifiedVariables().toArray(new Variable[forAll.getQuantifiedVariables().size()]));
        quantifiedVariables = new HashSet<>(oldQuantifiedVariables);
        return unprimed;
    }

    @Override
    public AExpression visit(Int anInt) {
        return anInt;
    }

    @Override
    public AExpression visit(Variable variable) {
        if (variable.getName().contains("!")) {
            return new Variable(variable.getName().substring(0, variable.getName().indexOf("!")).split("_prime")[0] + variable.getName().substring(variable.getName().indexOf("!")));
        } else {
            return new Variable(variable.getName().split("_prime")[0]);
        }
    }

    @Override
    public AExpression visit(FunctionCall functionCall) {
        //return new FunctionCall(functionCall.getDefinition().getName().split(Primer.getSuffix())[0], functionCall.getOperands().stream().map(parameter -> (AArithmeticExpression) parameter.accept(this)).toArray(AArithmeticExpression[]::new));
        return null;
    }

    @Override
    public AExpression visit(Sum sum) {
        return new Sum(sum.getOperands().stream().map(parameter -> (AArithmeticExpression) parameter.accept(this)).toArray(AArithmeticExpression[]::new));
    }

    @Override
    public AExpression visit(Subtraction subtraction) {
        return new Subtraction(subtraction.getOperands().stream().map(parameter -> (AArithmeticExpression) parameter.accept(this)).toArray(AArithmeticExpression[]::new));
    }

    @Override
    public AExpression visit(Multiplication multiplication) {
        return new Multiplication(multiplication.getOperands().stream().map(parameter -> (AArithmeticExpression) parameter.accept(this)).toArray(AArithmeticExpression[]::new));
    }

}
