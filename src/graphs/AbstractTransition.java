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

    @Override
    public AbstractState getSource() {
        return (AbstractState) super.getSource();
    }

    @Override
    public AbstractState getTarget() {
        return (AbstractState) super.getTarget();
    }

}
