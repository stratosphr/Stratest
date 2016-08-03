package eventb.substitutions;

import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
