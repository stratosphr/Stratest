package eventb.expressions.bool;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 08:46
 */
public abstract class APredicate extends ABooleanExpression {

    private final String name;
    private final ABooleanExpression expression;

    public APredicate(String name, ABooleanExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    public final String getName() {
        return name;
    }

    public final ABooleanExpression getExpression() {
        return expression;
    }

}
