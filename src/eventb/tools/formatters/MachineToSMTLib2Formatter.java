package eventb.tools.formatters;

import eventb.Event;
import eventb.Machine;
import eventb.expressions.FunctionDefinition;
import eventb.expressions.arith.Variable;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.NamedSet;
import eventb.expressions.sets.RangeSet;
import eventb.substitutions.*;
import utilities.UCharacters;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 09:44
 */
public final class MachineToSMTLib2Formatter extends AExpressionToSMTLib2Formatter implements IEventBFormatter {

    private Machine machine;

    private MachineToSMTLib2Formatter() {
    }

    public static String getMachineToSMTLib2(Machine machine) {
        return machine.accept(new MachineToSMTLib2Formatter());
    }

    @Override
    public String visit(Machine machine) {
        this.machine = machine;
        String formatted = "";
        formatted += "; VARIABLES" + UCharacters.LINE_SEPARATOR;
        for (Variable variable : getMachine().getVariables()) {
            formatted += "(declare-fun " + variable.getName() + " () Int)" + UCharacters.LINE_SEPARATOR;
        }
        formatted += UCharacters.LINE_SEPARATOR;
        formatted += "; INVARIANT" + UCharacters.LINE_SEPARATOR;
        formatted += "(assert " + getMachine().getInvariant().accept(this) + ")";
        System.out.println();
        System.out.println(formatted);
        return formatted;
    }

    @Override
    public String visit(Event event) {
        return null;
    }

    @Override
    public String visit(Skip skip) {
        return null;
    }

    @Override
    public String visit(Assignment assignment) {
        return null;
    }

    @Override
    public String visit(MultipleAssignment multipleAssignment) {
        return null;
    }

    @Override
    public String visit(Select select) {
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        return null;
    }

    @Override
    public String visit(CustomSet customSet) {
        return null;
    }

    @Override
    public String visit(NamedSet namedSet) {
        return null;
    }

    @Override
    public String visit(RangeSet rangeSet) {
        return null;
    }

    @Override
    public String visit(FunctionDefinition functionDefinition) {
        return null;
    }

    private Machine getMachine() {
        return machine;
    }

}
