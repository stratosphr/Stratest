package eventb.tools.formatters;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:25
 */
public interface IEventBFormatterVisitable {

    String accept(IEventBFormatter visitor);

}
