package graphs;

import eventb.Event;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 21:42
 */
public abstract class ATransition {

    private final AState source;
    private final Event event;
    private final AState target;

    public ATransition(AState source, Event event, AState target) {
        this.source = source;
        this.event = event;
        this.target = target;
    }

    public AState getSource() {
        return source;
    }

    public Event getEvent() {
        return event;
    }

    public AState getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        return (getSource().getExpression() + getEvent().getName() + getTarget().getExpression()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!(obj instanceof ATransition)) {
            return false;
        } else {
            ATransition other = (ATransition) obj;
            return getSource().equals(other.getSource()) && getEvent().getName().equals(other.getEvent().getName()) && getTarget().equals(other.getTarget());
        }
    }

    @Override
    public String toString() {
        return getSource() + " - " + getEvent().getName() + " -> " + getTarget();
    }

}
