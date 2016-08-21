package algorithms.tools;

/**
 * Created by gvoiron on 29/06/16.
 * Time : 14:21
 * <p>
 * Java Program to Implement Tarjan Algorithm
 * <p>
 * Java Program to Implement Tarjan Algorithm
 * <p>
 * Java Program to Implement Tarjan Algorithm
 * <p>
 * Java Program to Implement Tarjan Algorithm
 * <p>
 * Java Program to Implement Tarjan Algorithm
 * <p>
 * Java Program to Implement Tarjan Algorithm
 **/
/**
 *     Java Program to Implement Tarjan Algorithm
 **/

import graphs.ConcreteState;
import graphs.ConcreteTransition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

/** class Tarjan **/
@SuppressWarnings("unchecked")
public final class Tarjan {

    /** number of vertices **/
    private int V;
    /** preorder number counter **/
    private int preCount;
    /** low number of v **/
    private int[] low;
    /** to check if v is visited **/
    private boolean[] visited;
    /** to store given graph **/
    private List<Integer>[] graph;
    /** to store all scc **/
    private List<List<Integer>> sccComp;
    private Stack<Integer> stack;

    /** function to get all strongly connected components **/
    public List<List<Integer>> getSCComponents(List<Integer>[] graph) {
        V = graph.length;
        this.graph = graph;
        low = new int[V];
        visited = new boolean[V];
        stack = new Stack<>();
        sccComp = new ArrayList<>();
        for (int v = 0; v < V; v++)
            if (!visited[v])
                dfs(v);
        return sccComp;
    }

    /** function dfs **/
    public void dfs(int v) {
        low[v] = preCount++;
        visited[v] = true;
        stack.push(v);
        int min = low[v];
        for (int w : graph[v]) {
            if (!visited[w])
                dfs(w);
            if (low[w] < min)
                min = low[w];
        }
        if (min < low[v]) {
            low[v] = min;
            return;
        }
        List<Integer> component = new ArrayList<>();
        int w;
        do {
            w = stack.pop();
            component.add(w);
            low[w] = V;
        } while (w != v);
        sccComp.add(component);
    }

    public List<List<ConcreteState>> computeStronglyConnectedComponents(List<ConcreteState> vertices, List<ConcreteTransition> concreteTransitions) {
        List<Integer>[] g = new List[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            g[i] = new ArrayList<>();
        }
        for (ConcreteTransition concreteTransition : concreteTransitions) {
            g[vertices.indexOf(concreteTransition.getSource())].add(vertices.indexOf(concreteTransition.getTarget()));
        }
        Tarjan tarjan = new Tarjan();
        List<List<Integer>> stronglyConnectedComponents = tarjan.getSCComponents(g);
        return stronglyConnectedComponents.stream().map(indexes -> indexes.stream().map(vertices::get).collect(Collectors.toList())).collect(Collectors.toList());
    }

    /** main **/
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Tarjan algorithm Test\n");
        System.out.println("Enter number of Vertices");
        /** number of vertices **/
        int V = scan.nextInt();

        /** make graph **/
        List<Integer>[] g = new List[V];
        for (int i = 0; i < V; i++)
            g[i] = new ArrayList<>();
        /** accept all edges **/
        System.out.println("\nEnter number of edges");
        int E = scan.nextInt();
        /** all edges **/
        System.out.println("Enter " + E + " x, y coordinates");
        for (int i = 0; i < E; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            g[x].add(y);
        }

        Tarjan t = new Tarjan();
        System.out.println("\nSCC : ");
        /** print all strongly connected components **/
        List<List<Integer>> scComponents = t.getSCComponents(g);
        System.out.println(scComponents);
    }

}
