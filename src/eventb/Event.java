package eventb;

import eventb.substitutions.ASubstitution;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:34
 */
public final class Event extends AObjectEventB implements Comparable<Event> {

    private final String name;
    private final ASubstitution substitution;

    public Event(String name, ASubstitution substitution) {
        this.name = name;
        this.substitution = substitution;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

    @Override
    public int compareTo(Event event) {
        return toString().compareTo(event.toString());
    }

}
