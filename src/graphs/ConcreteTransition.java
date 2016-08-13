package graphs;

import eventb.Event;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 21:42
 */
public final class ConcreteTransition extends ATransition {

    public ConcreteTransition(ConcreteState source, Event label, ConcreteState target) {
        super(source, label, target);
    }

}
