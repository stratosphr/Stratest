package algorithms.tools.formatting;

import formatting.AFormatter;
import graphs.AState;
import graphs.ATransition;
import graphs.ConcreteState;
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
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i) instanceof ConcreteState) {
                renamedStates.put(states.get(i), states.get(i).getName() + "_" + i);
            } else {
                renamedStates.put(states.get(i), states.get(i).getName() + "_" + i);
            }
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
        for (AState state : states) {
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

}
