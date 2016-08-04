package eventb.tools.formatters;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 13:05
 */
public interface IExpressionFormatterVisitable {

    String accept(IExpressionFormatter visitor);

}
