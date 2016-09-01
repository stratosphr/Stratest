package algorithms.tools;

import com.microsoft.z3.Status;
import eventb.Machine;
import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Exists;
import eventb.expressions.bool.Not;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractTransition;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.UTuple;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:45
 */
public final class ModalityChecker {

    private Machine machine;

    public ModalityChecker(Machine machine) {
        this.machine = machine;
    }

    public Boolean isMay(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource().getExpression(),
                (ABooleanExpression) abstractTransition.getTarget().getExpression().prime(),
                abstractTransition.getEvent().getSubstitution().getPrd(getMachine())
        )));
        return z3.checkSAT() == Status.SATISFIABLE;
    }

    public UTuple<Boolean, Model> isMayWithModel(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource().getExpression(),
                (ABooleanExpression) abstractTransition.getTarget().getExpression().prime(),
                abstractTransition.getEvent().getSubstitution().getPrd(getMachine())
        )));
        return z3.checkSAT() == Status.SATISFIABLE ? new UTuple<>(true, z3.getModel()) : new UTuple<>(false, null);
    }

    public Boolean isMustMinus(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                new Not(new Exists(
                        new And(
                                getMachine().getInvariant(),
                                abstractTransition.getEvent().getSubstitution().getPrd(getMachine()),
                                abstractTransition.getSource().getExpression()
                        ),
                        getMachine().getVariables().toArray(new Variable[getMachine().getAssignables().size()])
                )),
                (ABooleanExpression) abstractTransition.getTarget().getExpression().prime()
        )));
        return z3.checkSAT() == Status.UNSATISFIABLE;
    }

    public Boolean isMustPlus(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                new Not(new Exists(
                        new And(
                                (ABooleanExpression) getMachine().getInvariant().prime(true),
                                abstractTransition.getEvent().getSubstitution().getPrd(getMachine()),
                                (ABooleanExpression) abstractTransition.getTarget().getExpression().prime()
                        ),
                        getMachine().getAssignables().stream().map(assignable -> (AAssignable) assignable.prime()).toArray(Variable[]::new)
                )),
                abstractTransition.getSource().getExpression()
        )));
        return z3.checkSAT() == Status.UNSATISFIABLE;
    }

    /*public Boolean isMay(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource(),
                abstractTransition.getEvent().getSubstitution().getWCP(abstractTransition.getTarget()),
                (ABooleanExpression) abstractTransition.getTarget().prime()
        )));
        return z3.checkSAT() == Status.SATISFIABLE;
    }

    public UTuple<Boolean, Model> isMayWithModel(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource(),
                abstractTransition.getEvent().getSubstitution().getWCP(abstractTransition.getTarget()),
                (ABooleanExpression) abstractTransition.getTarget().prime()
        )));
        Status isSAT = z3.checkSAT();
        if (isSAT == Status.SATISFIABLE) {
            return new UTuple<>(Boolean.TRUE, z3.getModel());
        } else {
            return new UTuple<>(Boolean.FALSE, null);
        }
    }

    public Boolean isMustPlus(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource(),
                new Not(abstractTransition.getEvent().getSubstitution().getWCP(abstractTransition.getTarget())),
                (ABooleanExpression) abstractTransition.getTarget().prime()
        )));
        return z3.checkSAT() == Status.UNSATISFIABLE;
    }

    public Boolean isMustMinus(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource(),
                new Not(abstractTransition.getEvent().getSubstitution().getSP(abstractTransition.getSource(), getMachine())),
                (ABooleanExpression) abstractTransition.getTarget().prime()
        )));
        return z3.checkSAT() == Status.UNSATISFIABLE;
    }*/

    public Machine getMachine() {
        return machine;
    }

}
