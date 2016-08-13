package graphs;

import eventb.Event;
import eventb.expressions.arith.Int;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.True;
import eventb.substitutions.Skip;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 21:49
 */
public class ATransitionTest {

    @Test
    public void test_getSource() {
        ConcreteState concreteSource = new ConcreteState("source", new True());
        ConcreteState concreteTarget = new ConcreteState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        Event event = new Event("event", new Skip());
        ConcreteTransition concreteTransition = new ConcreteTransition(concreteSource, event, concreteTarget);
        Assert.assertEquals(concreteSource, concreteTransition.getSource());
        AbstractState abstractSource = new AbstractState("source", new True());
        AbstractState abstractTarget = new AbstractState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        event = new Event("event", new Skip());
        AbstractTransition abstractTransition = new AbstractTransition(abstractSource, event, abstractTarget);
        Assert.assertEquals(abstractSource, abstractTransition.getSource());
    }

    @Test
    public void test_getLabel() {
        ConcreteState concreteSource = new ConcreteState("source", new True());
        ConcreteState concreteTarget = new ConcreteState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        Event event = new Event("event", new Skip());
        ConcreteTransition concreteTransition = new ConcreteTransition(concreteSource, event, concreteTarget);
        Assert.assertEquals(event, concreteTransition.getEvent());
        AbstractState abstractSource = new AbstractState("source", new True());
        AbstractState abstractTarget = new AbstractState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        event = new Event("event", new Skip());
        AbstractTransition abstractTransition = new AbstractTransition(abstractSource, event, abstractTarget);
        Assert.assertEquals(event, abstractTransition.getEvent());
    }

    @Test
    public void test_getTarget() {
        ConcreteState concreteSource = new ConcreteState("source", new True());
        ConcreteState concreteTarget = new ConcreteState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        Event event = new Event("event", new Skip());
        ConcreteTransition concreteTransition = new ConcreteTransition(concreteSource, event, concreteTarget);
        Assert.assertEquals(concreteTarget, concreteTransition.getTarget());
        AbstractState abstractSource = new AbstractState("source", new True());
        AbstractState abstractTarget = new AbstractState("target", new And(new True(), new Equals(new Int(0), new Int(3))));
        event = new Event("event", new Skip());
        AbstractTransition abstractTransition = new AbstractTransition(abstractSource, event, abstractTarget);
        Assert.assertEquals(abstractTarget, abstractTransition.getTarget());
    }

}