package controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;
import java.util.List;
import model.Automaton;
import model.State;
import model.Structure;
import model.Transition;

public class GeradorXMLController
{

    public void geraXML(String corpoAutomato, String expression){
        StringBuilder header = new StringBuilder();
        //header.append("<?xml version=\"1.0\"?>\n");
        header.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        header.append(" <!--".concat(expression).concat("-->\n"));
        //header.append("<structure xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");

        Transition transition1 = new Transition();
        transition1.setFrom("3");
        transition1.setTo("1");
        transition1.setRead("b");

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(transition1);

        State state1 = new State();
        state1.setId("0");
        state1.setName("q0");
        state1.setX(0.0);
        state1.setY(0.0);
        state1.setInitialState(true);

        State state2 = new State();
        state2.setId("1");
        state2.setName("q1");
        state2.setX(0.0);
        state2.setY(0.0);
        state2.setFinalState(true);

        List<State> states = new ArrayList<State>();
        states.add(state1);
        states.add(state2);

        Automaton automaton = new Automaton();
        automaton.setStates(states);
        automaton.setTransitions(transitions);
        Structure structure = new Structure("fa",automaton);


        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(new Class[] {Structure.class,Automaton.class});
        //xStream.aliasField("state test", Structure.class, "state");
        xStream.addImplicitCollection(Automaton.class, "states");
        xStream.addImplicitCollection(Automaton.class, "transitions");

        String xml = xStream.toXML(structure);

        xml = xml.replace("<final>false</final>","");
        xml = xml.replace("<initial>false</initial>","");

        xml = xml.replace("<final>true</final>","<final />");
        xml = xml.replace("<initial>true</initial>","<initial />");


        header.append(xml);

        System.out.println(header);
    }


    public static void main(String[] args)
    {
        GeradorXMLController controller = new GeradorXMLController();
        controller.geraXML("","");
    }
}
