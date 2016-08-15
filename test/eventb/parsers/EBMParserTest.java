package eventb.parsers;

import eventb.Machine;
import org.junit.Test;

import java.io.File;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 09:45
 */
@SuppressWarnings("unchecked")
public class EBMParserTest {

    @Test
    public void test_parseThreeBatteries() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
    }

    @Test
    public void test_parsePhone() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/phone/phone.ebm"));
    }

    @Test
    public void test_parseCarAlarm() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/carAlarm/carAlarm.ebm"));
    }

    @Test
    public void test_parseCoffeeMachine() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
    }

    @Test
    public void test_parseFrontWiper() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/frontWiper/frontWiper.ebm"));
    }

    @Test
    public void test_parseCreditCard() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/creditCard/creditCard.ebm"));
    }

}