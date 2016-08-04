package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.tools.formatters.IEventBFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 23:30
 */
public final class RangeSet extends ASet {

    private final Int lowerBound;
    private final Int upperBound;

    public RangeSet(Int lowerBound, Int upperBound) {
        super(computeElements(lowerBound.getValue(), upperBound.getValue()));
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    private static Int[] computeElements(int lowerBound, int upperBound) {
        List<Int> elements = new ArrayList<>();
        if (lowerBound > upperBound) {
            throw new Error("The lower bound (here : " + lowerBound + ") must be lower or equal to the upper bound (here : " + upperBound + ") when constructing RangeSet instance.");
        } else {
            for (int i = lowerBound; i <= upperBound; i++) {
                elements.add(new Int(i));
            }
        }
        return elements.toArray(new Int[elements.size()]);
    }

    public Int getLowerBound() {
        return lowerBound;
    }

    public Int getUpperBound() {
        return upperBound;
    }

}
