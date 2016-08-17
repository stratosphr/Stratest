package eventb.expressions.arith;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 10:45
 */
public abstract class AAssignable extends AArithmeticExpression implements Comparable<AAssignable> {

    @Override
    public int compareTo(AAssignable assignable) {
        return toString().compareTo(assignable.toString());
    }

}
