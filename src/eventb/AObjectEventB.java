package eventb;

import eventb.tools.formatter.EventBFormatter;
import eventb.tools.formatter.IEventBFormatterVisitable;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:08
 */
public abstract class AObjectEventB implements IEventBFormatterVisitable {

    @Override
    public final int hashCode() {
        return toString().hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return this == obj || obj != null && obj.getClass().equals(getClass()) && toString().equals(obj.toString());
    }

    @Override
    public final String toString() {
        return accept(new EventBFormatter());
    }

}
