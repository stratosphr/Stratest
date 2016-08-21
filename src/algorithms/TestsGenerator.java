package algorithms;

import algorithms.outputs.JSCATS;
import algorithms.tools.ChinesePostman;
import algorithms.tools.EStateColor;
import algorithms.tools.Tarjan;
import eventb.Event;
import eventb.expressions.bool.True;
import graphs.AbstractState;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import utilities.UAUninstantiable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 11:23
 */
public final class TestsGenerator extends UAUninstantiable {

    public static JSCATS connectJSCATS(JSCATS universalConcretisation) {
        ConcreteState fictive = new ConcreteState("_fictive_", new True());
        Event beta = new Event("_Beta_", null);
        Event reset = new Event("_Reset_", null);
        Set<ConcreteState> C = new LinkedHashSet<>(universalConcretisation.getIc0());
        Map<ConcreteState, AbstractState> Alpha = new LinkedHashMap<>();
        Map<ConcreteState, EStateColor> Kappa = new LinkedHashMap<>();
        Set<ConcreteTransition> DeltaC = new LinkedHashSet<>();
        List<ConcreteState> sourceStates = new ArrayList<>(universalConcretisation.getIc0());
        for (ConcreteState concreteState : universalConcretisation.getIc0()) {
            Alpha.put(concreteState, universalConcretisation.getAlpha().get(concreteState));
            Kappa.put(concreteState, universalConcretisation.getKappa().get(concreteState));
        }
        while (!sourceStates.isEmpty()) {
            List<ConcreteState> tmpSourceStates = new ArrayList<>();
            for (ConcreteState sourceState : sourceStates) {
                universalConcretisation.getDeltaC().stream().filter(concreteTransition -> concreteTransition.getSource().equals(sourceState)).forEach(concreteTransition -> {
                    if (C.add(concreteTransition.getTarget())) {
                        Alpha.put(concreteTransition.getTarget(), universalConcretisation.getAlpha().get(concreteTransition.getTarget()));
                        Kappa.put(concreteTransition.getTarget(), universalConcretisation.getKappa().get(concreteTransition.getTarget()));
                        tmpSourceStates.add(concreteTransition.getTarget());
                    }
                    DeltaC.add(concreteTransition);
                });
            }
            sourceStates.clear();
            sourceStates.addAll(tmpSourceStates);
        }
        universalConcretisation.getIc0().forEach(initialState -> DeltaC.add(new ConcreteTransition(fictive, beta, initialState)));
        C.forEach(concreteState -> {
            if (DeltaC.stream().noneMatch(concreteTransition -> concreteTransition.getSource().equals(concreteState))) {
                DeltaC.add(new ConcreteTransition(concreteState, reset, fictive));
            }
        });
        List<ConcreteState> C2 = new ArrayList<>(C);
        C2.add(fictive);
        List<List<ConcreteState>> stronglyConnectedComponents = new Tarjan().computeStronglyConnectedComponents(C2, new ArrayList<>(DeltaC));
        DeltaC.addAll(stronglyConnectedComponents.stream().filter(stronglyConnectedComponent -> !stronglyConnectedComponent.contains(fictive)).map(stronglyConnectedComponent -> new ConcreteTransition(stronglyConnectedComponent.get(0), reset, fictive)).collect(Collectors.toList()));
        return new JSCATS(new LinkedHashSet<>(universalConcretisation.getQ()), new LinkedHashSet<>(universalConcretisation.getQ0()), C, new LinkedHashSet<>(Collections.singletonList(fictive)), new LinkedHashSet<>(universalConcretisation.getDelta()), new LinkedHashSet<>(universalConcretisation.getDeltaPlus()), new LinkedHashSet<>(universalConcretisation.getDeltaMinus()), Alpha, Kappa, DeltaC);
    }

    public static List<List<ConcreteTransition>> generateTests(JSCATS abstraction) {
        List<ConcreteState> C = new ArrayList<>(abstraction.getC().stream().collect(Collectors.toList()));
        C.addAll(abstraction.getIc0());
        ChinesePostman G = new ChinesePostman(C);
        abstraction.getDeltaC().forEach(concreteTransition -> G.addArc(concreteTransition.getEvent().getName(), C.indexOf(concreteTransition.getSource()), C.indexOf(concreteTransition.getTarget()), 1));
        List<List<ConcreteTransition>> concreteTests = G.computeCPT(C.indexOf(abstraction.getIc0().iterator().next()));
        concreteTests.forEach(test -> test.removeIf(concreteTransition -> concreteTransition.getEvent().getName().equals("_Beta_") || concreteTransition.getEvent().getName().equals("_Reset_")));
        return concreteTests;
    }

}
