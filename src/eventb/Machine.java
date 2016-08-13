package eventb;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.FunctionCall;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.sets.NamedSet;
import eventb.substitutions.ASubstitution;
import eventb.tools.formatters.IEventBFormatter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 11:08
 */
public final class Machine extends AObjectEventB {

    private final String name;
    private final List<NamedSet> sets;
    private final List<AAssignable> assignables;
    private final ABooleanExpression invariant;
    private final ASubstitution initialization;
    private final List<Event> events;

    public Machine(String name, List<NamedSet> sets, List<AAssignable> assignables, ABooleanExpression invariant, ASubstitution initialization, List<Event> events) {
        this.name = name;
        this.sets = sets;
        this.assignables = assignables;
        this.invariant = invariant;
        this.initialization = initialization;
        this.events = events;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public List<NamedSet> getSets() {
        return sets;
    }

    public List<AAssignable> getAssignables() {
        return assignables;
    }

    public List<Variable> getVariables() {
        return getAssignables().stream().filter(assignable -> assignable instanceof Variable).map(assignable -> (Variable) assignable).collect(Collectors.toList());
    }

    public List<FunctionCall> getFunctionCalls() {
        return getAssignables().stream().filter(assignable -> assignable instanceof FunctionCall).map(assignable -> (FunctionCall) assignable).collect(Collectors.toList());
    }

    public ABooleanExpression getInvariant() {
        return invariant;
    }

    public ASubstitution getInitialization() {
        return initialization;
    }

    public List<Event> getEvents() {
        return events;
    }

}
