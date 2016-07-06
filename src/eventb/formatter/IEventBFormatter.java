package eventb.formatter;

import eventb.Event;
import eventb.expressions.bool.Implication;
import eventb.expressions.bool.True;
import eventb.substitutions.Select;
import eventb.substitutions.Skip;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:25
 */
public interface IEventBFormatter {

    String visit(Event event);

    String visit(Skip skip);

    String visit(Select select);

    String visit(True aTrue);

    String visit(Implication implication);

}
