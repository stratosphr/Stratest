package graphs;

import eventb.Event;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 21:42
 */
public final class AbstractTransition extends ATransition {

    public AbstractTransition(AbstractState source, Event label, AbstractState target) {
        super(source, label, target);
    }

}
