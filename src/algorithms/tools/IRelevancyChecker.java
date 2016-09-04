package algorithms.tools;

import graphs.ConcreteState;

import java.util.Map;
import java.util.Set;

/**
 * Created by gvoiron on 02/09/16.
 * Time : 16:11
 */
public interface IRelevancyChecker {

    boolean isRelevant(ConcreteState concreteState, Set<ConcreteState> computedConcreteStates, Map<ConcreteState, EStateColor> concreteStatesColors);

}
