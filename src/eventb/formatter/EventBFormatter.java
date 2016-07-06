package eventb.formatter;

import eventb.Event;
import eventb.expressions.bool.Implication;
import eventb.expressions.bool.True;
import eventb.substitutions.Select;
import eventb.substitutions.Skip;
import formatting.AFormatter;
import utilities.UCharacters;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:22
 */
public final class EventBFormatter extends AFormatter implements IEventBFormatter {

    @Override
    public String visit(Event event) {
        return event.getName() + " " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + indent(event.getSubstitution().accept(this));
    }

    @Override
    public String visit(Skip skip) {
        return "SKIP";
    }

    @Override
    public String visit(Select select) {
        return "SELECT" + UCharacters.LINE_SEPARATOR + indent(select.getCondition().accept(this)) + UCharacters.LINE_SEPARATOR + "THEN" + UCharacters.LINE_SEPARATOR + indent(select.getSubstitution().accept(this)) + UCharacters.LINE_SEPARATOR + "END";
    }

    @Override
    public String visit(True aTrue) {
        return "_true_";
    }

    @Override
    public String visit(Implication implication) {
        return implication.getIfPart().accept(this) + " => " + implication.getThenPart().accept(this);
    }

}
