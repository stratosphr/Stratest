package algorithms.tools;

import graphs.ConcreteState;

import java.util.Map;
import java.util.Set;

/**
 * Created by gvoiron on 02/09/16.
 * Time : 16:18
 */
public final class UniversalRelevancyChecker implements IRelevancyChecker {

    private int minTrueCount;
    private int trueCounter;

    public UniversalRelevancyChecker(int minTrueCount) {
        this.trueCounter = 0;
        this.minTrueCount = minTrueCount;
    }

    @Override
    public boolean isRelevant(ConcreteState concreteState, Set<ConcreteState> computedConcreteStates, Map<ConcreteState, EStateColor> concreteStatesColors) {
        return concreteStatesColors.get(concreteState) == EStateColor.GREEN && trueCounter++ <= minTrueCount;
    }

}
