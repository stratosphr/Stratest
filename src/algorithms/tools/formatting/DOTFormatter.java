package algorithms.tools.formatting;

import algorithms.outputs.Test;
import formatting.AFormatter;
import graphs.AState;
import graphs.ATransition;
import graphs.ConcreteTransition;
import utilities.UCharacters;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 04/09/16.
 * Time : 14:58
 */
public final class DOTFormatter extends AFormatter {

    public String formatGraph(List<? extends AState> initialStates, List<? extends ATransition> transitions) {
        List<AState> states = transitions.stream().map(transition -> Arrays.asList(transition.getSource(), transition.getTarget())).flatMap(Collection::stream).collect(Collectors.toList());
        Map<AState, String> renamedStates = new HashMap<>();
        for (int i = 0; i < initialStates.size(); i++) {
            renamedStates.put(initialStates.get(i), initialStates.get(i).getName() + "_" + i);
        }
        for (int i = 0; i < states.size(); i++) {
            renamedStates.put(states.get(i), states.get(i).getName() + "_" + i);
        }
        String str = "";
        str += "digraph G {" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        indentRight();
        str += indent() + "rankdir = LR;" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        for (AState initialState : initialStates) {
            str += indent() + "start_" + renamedStates.get(initialState) + "[style=invisible];" + UCharacters.LINE_SEPARATOR;
        }
        str += UCharacters.LINE_SEPARATOR;
        str += indent() + "node[shape=box, style=\"rounded, filled\", color=lightblue2];" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        for (AState state : renamedStates.keySet()) {
            //str += indent() + renamedStates.get(state) + "[label=\"" + renamedStates.get(state) + "\"];" + UCharacters.LINE_SEPARATOR;
            str += indent() + renamedStates.get(state) + "[label=\"" + state + "\"];" + UCharacters.LINE_SEPARATOR;
        }
        str += UCharacters.LINE_SEPARATOR;
        for (AState initialState : initialStates) {
            str += indent() + "start_" + renamedStates.get(initialState) + " -> " + renamedStates.get(initialState) + ";" + UCharacters.LINE_SEPARATOR;
        }
        for (ATransition transition : transitions) {
            str += indent() + renamedStates.get(transition.getSource()) + " -> " + renamedStates.get(transition.getTarget()) + "[label=\"" + transition.getEvent().getName() + "\"];" + UCharacters.LINE_SEPARATOR;
        }
        str += UCharacters.LINE_SEPARATOR;
        indentLeft();
        str += indent() + "}";
        return str;
    }

    public String formatTestsOnGraph(List<Test> tests, List<? extends AState> initialStates, List<? extends ATransition> transitions) {
        LinkedHashSet<ConcreteTransition> flattenedTestsTransitions = new LinkedHashSet<>(tests.stream().flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new)));
        List<AState> states = transitions.stream().map(transition -> Arrays.asList(transition.getSource(), transition.getTarget())).flatMap(Collection::stream).collect(Collectors.toList());
        Map<AState, String> renamedStates = new HashMap<>();
        for (int i = 0; i < initialStates.size(); i++) {
            renamedStates.put(initialStates.get(i), initialStates.get(i).getName() + "_" + i);
        }
        for (int i = 0; i < states.size(); i++) {
            renamedStates.put(states.get(i), states.get(i).getName() + "_" + i);
        }
        String str = "";
        str += "digraph G {" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        indentRight();
        str += indent() + "rankdir = LR;" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        for (AState initialState : initialStates) {
            str += indent() + "start_" + renamedStates.get(initialState) + "[style=invisible];" + UCharacters.LINE_SEPARATOR;
        }
        str += UCharacters.LINE_SEPARATOR;
        str += indent() + "node[shape=box, style=\"rounded, filled\", color=lightblue2];" + UCharacters.LINE_SEPARATOR;
        str += UCharacters.LINE_SEPARATOR;
        for (AState state : renamedStates.keySet()) {
            str += indent() + renamedStates.get(state) + "[label=\"" + renamedStates.get(state) + "\"];" + UCharacters.LINE_SEPARATOR;
            //str += indent() + renamedStates.get(state) + "[label=\"" + state + "\"];" + UCharacters.LINE_SEPARATOR;
        }
        str += UCharacters.LINE_SEPARATOR;
        for (AState initialState : initialStates) {
            str += indent() + "start_" + renamedStates.get(initialState) + " -> " + renamedStates.get(initialState) + "[penwidth=3, color=red];" + UCharacters.LINE_SEPARATOR;
        }
        for (ATransition transition : transitions) {
            if (!flattenedTestsTransitions.contains(transition)) {
                str += indent() + renamedStates.get(transition.getSource()) + " -> " + renamedStates.get(transition.getTarget()) + "[label=\"" + transition.getEvent().getName() + "\"];" + UCharacters.LINE_SEPARATOR;
            } else {
                str += indent() + renamedStates.get(transition.getSource()) + " -> " + renamedStates.get(transition.getTarget()) + "[label=\"" + transition.getEvent().getName() + "\", penwidth=3, color=red];" + UCharacters.LINE_SEPARATOR;
            }
        }
        str += UCharacters.LINE_SEPARATOR;
        indentLeft();
        str += indent() + "}";
        return str;
    }

}
