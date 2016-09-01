package eventb.substitutions;

import eventb.Machine;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 09:30
 */
public class Parallel extends ASubstitution {

    private final List<ASubstitution> substitutions;
    private final ASubstitution surrogate;

    public Parallel(ASubstitution... substitutions) {
        this.substitutions = Arrays.asList(substitutions);
        this.surrogate = computeSurrogate();
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABooleanExpression getPrd(Machine machine) {
        return getSurrogate().getPrd(machine);
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return getSurrogate().getWP(postCondition);
    }

    private ASubstitution computeSurrogate() {
        if (getSubstitutions().isEmpty()) {
            return new Skip();
        } else {
            ASubstitution surrogate = getSubstitutions().get(0);
            for (ASubstitution substitution : getSubstitutions().subList(1, getSubstitutions().size())) {
                surrogate = computeSurrogate(surrogate, substitution);
            }
            return surrogate instanceof Parallel ? ((Parallel) surrogate).getSurrogate() : surrogate;
        }
    }

    private ASubstitution computeSurrogate(ASubstitution substitution1, ASubstitution substitution2) {
        if (substitution1 instanceof Parallel) {
            return new Parallel(((Parallel) substitution1).getSurrogate(), substitution2);
        } else if (substitution2 instanceof Parallel) {
            return new Parallel(substitution1, ((Parallel) substitution2).getSurrogate());
        } else if (substitution1 instanceof Skip) {
            return substitution2;
        } else if (substitution2 instanceof Skip) {
            return substitution1;
        } else if (substitution1 instanceof Choice) {
            return new Choice(((Choice) substitution1).getSubstitutions().stream().map(substitution -> computeSurrogate(substitution2, substitution)).toArray(ASubstitution[]::new));
        } else if (substitution2 instanceof Choice) {
            return new Choice(((Choice) substitution2).getSubstitutions().stream().map(substitution -> computeSurrogate(substitution1, substitution)).toArray(ASubstitution[]::new));
        } else if (substitution1 instanceof Select) {
            return new Select(((Select) substitution1).getCondition(), computeSurrogate(((Select) substitution1).getSubstitution(), substitution2));
        } else if (substitution2 instanceof Select) {
            return new Select(((Select) substitution2).getCondition(), computeSurrogate(substitution1, ((Select) substitution2).getSubstitution()));
        } else if (substitution1 instanceof Any) {
            return new Any(((Any) substitution1).getWherePart(), computeSurrogate(((Any) substitution1).getThenPart(), substitution2), ((Any) substitution1).getQuantifiedVariables().toArray(new Variable[((Any) substitution1).getQuantifiedVariables().size()]));
        } else if (substitution2 instanceof Any) {
            return new Any(((Any) substitution2).getWherePart(), computeSurrogate(substitution1, ((Any) substitution2).getThenPart()), ((Any) substitution2).getQuantifiedVariables().toArray(new Variable[((Any) substitution2).getQuantifiedVariables().size()]));
        } else if (substitution1 instanceof IfThenElse) {
            return new IfThenElse(((IfThenElse) substitution1).getCondition(), computeSurrogate(((IfThenElse) substitution1).getThenPart(), substitution2), computeSurrogate(((IfThenElse) substitution1).getElsePart(), substitution2));
        } else if (substitution2 instanceof IfThenElse) {
            return new IfThenElse(((IfThenElse) substitution2).getCondition(), computeSurrogate(substitution1, ((IfThenElse) substitution2).getThenPart()), computeSurrogate(substitution1, ((IfThenElse) substitution2).getElsePart()));
        } else if (substitution1 instanceof MultipleAssignment && substitution2 instanceof MultipleAssignment) {
            List<Assignment> assignments = new ArrayList<>(((MultipleAssignment) substitution1).getAssignments());
            assignments.addAll(((MultipleAssignment) substitution2).getAssignments());
            return new MultipleAssignment(assignments.toArray(new Assignment[assignments.size()]));
        } else if (substitution1 instanceof MultipleAssignment) {
            List<Assignment> assignments = new ArrayList<>(((MultipleAssignment) substitution1).getAssignments());
            assignments.add((Assignment) substitution2);
            return new MultipleAssignment(assignments.toArray(new Assignment[assignments.size()]));
        } else if (substitution2 instanceof MultipleAssignment) {
            List<Assignment> assignments = new ArrayList<>(((MultipleAssignment) substitution2).getAssignments());
            assignments.add((Assignment) substitution1);
            return new MultipleAssignment(assignments.toArray(new Assignment[assignments.size()]));
        } else if (substitution1 instanceof Assignment && substitution2 instanceof Assignment) {
            return new MultipleAssignment((Assignment) substitution1, (Assignment) substitution2);
        } else {
            throw new Error("Unable to compute parallel's surrogate.");
        }
    }

    public List<ASubstitution> getSubstitutions() {
        return substitutions;
    }

    public ASubstitution getSurrogate() {
        return surrogate;
    }

}
