package eventb.expressions;

import java.util.List;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 10:02
 */
public interface INaryOperation {

    List<? extends AExpression> getOperands();

}
