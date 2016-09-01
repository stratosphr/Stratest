package eventb.substitutions;

import eventb.Machine;
import eventb.expressions.arith.AArithmeticExpression;
import eventb.expressions.arith.FunctionCall;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.ForAll;
import eventb.tools.formatters.IEventBFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 10:35
 */
public final class MultipleAssignment extends ASubstitution {

    private final List<Assignment> assignments;

    public MultipleAssignment(Assignment... assignments) {
        this.assignments = Arrays.asList(assignments);
    }

    @Override
    public ABooleanExpression getPrd(Machine machine) {
        List<ABooleanExpression> assignmentsPrd = getAssignments().stream().map(assignment -> assignment.getPrd(machine, true)).collect(Collectors.toList());
        machine.getAssignables().stream().filter(assignable -> getAssignments().stream().noneMatch(assignment -> assignment.getAssignable().getName().equals(assignable.getName()))).forEach(assignable -> {
            if (assignable instanceof Variable) {
                assignmentsPrd.add(new Equals((AArithmeticExpression) assignable.prime(), assignable));
            } else if (assignable instanceof FunctionCall) {
                Variable quantifiedVariable = new Variable("p!0");
                assignmentsPrd.add(new ForAll(new Equals(new FunctionCall(((FunctionCall) assignable.prime()).getDefinition(), quantifiedVariable), new FunctionCall(((FunctionCall) assignable).getDefinition(), quantifiedVariable)), quantifiedVariable));
            } else {
                throw new Error("Unhandled assignment case : the assignable is neither a Variable or a FunctionCall.");
            }
        });
        /*Map<String, List<Assignment>> functionCallsAssignments = new LinkedHashMap<>();
        getAssignments().stream().filter(assignment -> assignment.getAssignable() instanceof FunctionCall).forEach(assignment -> {
            if (!functionCallsAssignments.containsKey(assignment.getAssignable().getName())) {
                functionCallsAssignments.put(assignment.getAssignable().getName(), new ArrayList<>());
            }
            functionCallsAssignments.get(assignment.getAssignable().getName()).add(assignment);
        });
        functionCallsAssignments.forEach((name, assignments) -> {
            Variable quantifiedVariable = new Variable("p!0");
            assignmentsPrd.add(new ForAll(new Implication(new And(assignments.stream().map(assignment -> new Not(new Equals(quantifiedVariable, ((FunctionCall) assignment.getAssignable()).getOperands().get(0)))).toArray(ABooleanExpression[]::new)), new Equals((AArithmeticExpression) new FunctionCall(name, quantifiedVariable).prime(), new FunctionCall(name, quantifiedVariable))), quantifiedVariable));
        });*/
        return new And(assignmentsPrd.toArray(new ABooleanExpression[assignmentsPrd.size()]));
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        List<Assignment> assignments = new ArrayList<>(getAssignments());
        Collections.reverse(assignments);
        ABooleanExpression tmpPostCondition = postCondition;
        for (Assignment assignment : assignments) {
            tmpPostCondition = assignment.getWP(tmpPostCondition);
        }
        return tmpPostCondition;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

}
