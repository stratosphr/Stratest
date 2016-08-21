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

    @Override
    public ConcreteState getSource() {
        return (ConcreteState) super.getSource();
    }

    @Override
    public ConcreteState getTarget() {
        return (ConcreteState) super.getTarget();
    }

}
