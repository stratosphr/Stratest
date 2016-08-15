package eventb.parsers;

import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import eventb.expressions.sets.NamedSet;
import eventb.substitutions.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 27/06/16.
 * Time : 11:40
 */
@SuppressWarnings("unchecked")
public class EBMParser {

    private String name;
    private Set<NamedSet> sets;
    private Set<AAssignable> assignables;
    private ABooleanExpression properties;
    private ABooleanExpression invariant;
    private ASubstitution initialization;
    private Set<Event> events;
    private boolean isInAny;

    private void debug(Node invariant) {
        System.out.println(invariant.getNodeName() + " :");
        NodeList children = invariant.getChildNodes();
        System.out.print("\t");
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                System.out.print(child.getNodeName() + " - ");
            }
        }
        System.out.print("\n");
    }

    public EBMParser() {
        name = "";
        sets = new LinkedHashSet<>();
        assignables = new LinkedHashSet<>();
        properties = new True();
        invariant = new True();
        initialization = new Skip();
        events = new LinkedHashSet<>();
        isInAny = false;
    }

    public Object parse(File file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            return visit(root, document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new Error(e);
        }
    }

    private List<Node> getChildren(Node invariant) {
        List<Node> elementChildren = new ArrayList<>();
        NodeList children = invariant.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = invariant.getChildNodes().item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                elementChildren.add(child);
            }
        }
        return elementChildren;
    }

    private Node getChild(int index, Node invariant) {
        List<Node> elementChildren = new ArrayList<>();
        NodeList children = invariant.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = invariant.getChildNodes().item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                elementChildren.add(child);
            }
        }
        return elementChildren.get(index);
    }

    private Object visit(Node node, Document document) {
        switch (node.getNodeName()) {
            case "AbstractionPredicatesList":
                List<Predicate> predicates = new ArrayList<>();
                for (int i = 0; i < getChildren(node).size(); i++) {
                    predicates.add(new Predicate("p" + i, (ABooleanExpression) visit(getChild(i, node), document)));
                }
                return predicates;
            case "MACHINE":
                Node setsNode = document.getElementsByTagName("SETS").item(0);
                if (setsNode != null) {
                    sets = new LinkedHashSet<>((Collection<NamedSet>) visit(setsNode, document));
                }
                Node propertiesNode = document.getElementsByTagName("PROPERTIES").item(0);
                if (propertiesNode != null) {
                    properties = (ABooleanExpression) visit(propertiesNode, document);
                }
                Node invariantNode = document.getElementsByTagName("INVARIANT").item(0);
                if (invariantNode != null) {
                    invariant = new And(properties, (ABooleanExpression) visit(invariantNode, document));
                }
                //System.out.println(invariant);
                Node eventsNode = document.getElementsByTagName("EVENTS").item(0);
                if (eventsNode != null) {
                    events = new LinkedHashSet<>((Collection<Event>) visit(eventsNode, document));
                }
                Node initialisationNode = document.getElementsByTagName("INITIALISATION").item(0);
                if (initialisationNode != null) {
                    initialization = (ASubstitution) visit(initialisationNode, document);
                }
                name = ((Element) node).getAttribute("name");
                return new Machine(name, sets.stream().collect(Collectors.toList()), assignables.stream().collect(Collectors.toList()), invariant, initialization, events.stream().collect(Collectors.toList()));
            case "INVARIANT":
                return visit(getChild(0, node), document);
            case "EVENTS":
                return new ArrayList<>(getChildren(node).stream().map(event -> (Event) visit(event, document)).collect(Collectors.toList()));
            case "INITIALISATION":
                return visit(getChild(0, node), document);
            case "CNot":
                return new Not((ABooleanExpression) visit(getChild(0, node), document));
            case "CAnd":
                return new And(getChildren(node).stream().map(child -> (ABooleanExpression) visit(child, document)).toArray(ABooleanExpression[]::new));
            case "COr":
                return new Or(getChildren(node).stream().map(child -> (ABooleanExpression) visit(child, document)).toArray(ABooleanExpression[]::new));
            case "CInDomain":
                if (((Element) node).getAttribute("type").equals("0")) {
                    return new GreaterOrEqual((Variable) visit(getChild(0, node), document), new Int(0));
                } else {
                    throw new Error("Unhandled CInDomain case.");
                }
            case "CVariable":
                Variable variable = new Variable(((Element) node).getAttribute("val"));
                if (!isInAny) {
                    assignables.add(variable);
                }
                return variable;
            case "CPlus":
                return new Sum(getChildren(node).stream().map(parameter -> (AArithmeticExpression) visit(parameter, document)).toArray(AArithmeticExpression[]::new));
            case "CMinus":
                return new Subtraction(getChildren(node).stream().map(parameter -> (AArithmeticExpression) visit(parameter, document)).toArray(AArithmeticExpression[]::new));
            case "CEquals":
                return new Equals((AArithmeticExpression) visit(getChild(0, node), document), (AArithmeticExpression) visit(getChild(1, node), document));
            case "CGreater":
                return new GreaterThan((AArithmeticExpression) visit(getChild(0, node), document), (AArithmeticExpression) visit(getChild(1, node), document));
            case "CNumber":
                return new Int(Integer.parseInt(((Element) node).getAttribute("val")));
            case "CNonGuardedEvent":
                return new Event(((Element) node).getAttribute("name"), (ASubstitution) visit(getChild(0, node), document));
            case "CGuardedEvent":
                return new Event(((Element) node).getAttribute("name"), new Select((ABooleanExpression) visit(getChild(0, node), document), (ASubstitution) visit(getChild(1, node), document)));
            case "CAnyEvent":
                isInAny = true;
                Event event = new Event(((Element) node).getAttribute("name"), new Any((ABooleanExpression) visit(getChild(1, node), document), (ASubstitution) visit(getChild(2, node), document), getChildren(getChild(0, node)).stream().map(assignable -> (AAssignable) visit(assignable, document)).toArray(Variable[]::new)));
                isInAny = false;
                return event;
            case "CParallel":
                return new Parallel(getChildren(node).stream().map(child -> (ASubstitution) visit(child, document)).toArray(ASubstitution[]::new)).getSurrogate();
            case "CGuarded":
                return new Select((ABooleanExpression) visit(getChild(0, node), document), (ASubstitution) visit(getChild(1, node), document));
            case "CAny":
                isInAny = true;
                Any any = new Any((ABooleanExpression) visit(getChild(1, node), document), (ASubstitution) visit(getChild(2, node), document), getChildren(getChild(0, node)).stream().map(assignable -> (AAssignable) visit(assignable, document)).toArray(Variable[]::new));
                isInAny = false;
                return any;
            case "CNDChoice":
                return new Choice(getChildren(node).stream().map(substitution -> (ASubstitution) visit(substitution, document)).toArray(ASubstitution[]::new));
            case "CMultipleAssignment":
                return new MultipleAssignment(getChildren(node).stream().map(assignment -> (Assignment) visit(assignment, document)).toArray(Assignment[]::new));
            case "CAssignment":
                return new Assignment((AAssignable) visit(getChild(0, node), document), (AArithmeticExpression) visit(getChild(1, node), document));
            case "CSkip":
                return new Skip();
            case "CIf":
                if (getChildren(node).size() == 3) {
                    return new IfThenElse((ABooleanExpression) visit(getChild(0, node), document), (ASubstitution) visit(getChild(1, node), document), (ASubstitution) visit(getChild(2, node), document));
                } else if (getChildren(node).size() == 2) {
                    return new IfThenElse((ABooleanExpression) visit(getChild(0, node), document), (ASubstitution) visit(getChild(1, node), document), new Skip());
                } else {
                    throw new Error("Unhandled CIf case.");
                }
            default:
                throw new Error("Unhandled node name case in EBMParser : \"" + node.getNodeName() + "\"");
        }
    }

}

