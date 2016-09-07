package algorithms.outputs;

import algorithms.tools.EStateColor;
import algorithms.tools.formatting.DOTFormatter;
import graphs.AbstractState;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import graphs.ConcreteTransition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gvoiron on 21/03/16.
 * Time : 16:08
 */
public class JSCATS {

    private final Set<AbstractState> q;
    private final Set<AbstractState> q0;
    private Set<ConcreteState> c;
    private Set<ConcreteState> ic0;
    private final Set<AbstractTransition> delta;
    private final Set<AbstractTransition> deltaPlus;
    private final Set<AbstractTransition> deltaMinus;
    private final Map<ConcreteState, AbstractState> alpha;
    private Map<ConcreteState, EStateColor> kappa;
    private final Set<ConcreteTransition> deltaC;

    public JSCATS(Set<AbstractState> q, Set<AbstractState> q0, Set<ConcreteState> c, Set<ConcreteState> ic0, Set<AbstractTransition> delta, Set<AbstractTransition> deltaPlus, Set<AbstractTransition> deltaMinus, Map<ConcreteState, AbstractState> alpha, Map<ConcreteState, EStateColor> kappa, Set<ConcreteTransition> deltaC) {
        this.q = q;
        this.q0 = q0;
        this.c = c;
        this.ic0 = ic0;
        this.delta = delta;
        this.deltaPlus = deltaPlus;
        this.deltaMinus = deltaMinus;
        this.alpha = alpha;
        this.kappa = kappa;
        this.deltaC = deltaC;
    }

    public String getDOTFormatting() {
        return getDOTFormatting(false);
    }

    public String getDOTFormatting(boolean abstractSystem) {
        if (abstractSystem) {
            return new DOTFormatter().formatGraph(new ArrayList<>(getQ0()), new ArrayList<>(getDelta()));
        } else {
            return new DOTFormatter().formatGraph(new ArrayList<>(getIc0()), new ArrayList<>(getDeltaC()));
        }
    }

    public String getTestsDOTFormatting(List<Test> tests) {
        return getTestsDOTFormatting(tests, false);
    }

    public String getTestsDOTFormatting(List<Test> tests, boolean abstractSystem) {
        if (abstractSystem) {
            return new DOTFormatter().formatTestsOnGraph(tests, new ArrayList<>(getQ0()), new ArrayList<>(getDelta()));
        } else {
            return new DOTFormatter().formatTestsOnGraph(tests, new ArrayList<>(getIc0()), new ArrayList<>(getDeltaC()));
        }
    }

    public Set<AbstractState> getQ() {
        return q;
    }

    public Set<AbstractState> getQ0() {
        return q0;
    }

    public Set<ConcreteState> getC() {
        return c;
    }

    public Set<ConcreteState> getIc0() {
        return ic0;
    }

    public Set<AbstractTransition> getDelta() {
        return delta;
    }

    public Set<AbstractTransition> getDeltaPlus() {
        return deltaPlus;
    }

    public Set<AbstractTransition> getDeltaMinus() {
        return deltaMinus;
    }

    public Map<ConcreteState, AbstractState> getAlpha() {
        return alpha;
    }

    public Map<ConcreteState, EStateColor> getKappa() {
        return kappa;
    }

    public Set<ConcreteTransition> getDeltaC() {
        return deltaC;
    }

}
