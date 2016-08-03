package eventb.expressions.sets;

import eventb.expressions.AExpression;
import eventb.expressions.arith.Int;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 23:30
 */
public abstract class ASet extends AExpression {

    private final Set<Int> elements;

    public ASet(Int... elements) {
        this.elements = new LinkedHashSet<>(Arrays.asList(elements));
    }

    public Set<Int> getElements() {
        return elements;
    }

}
