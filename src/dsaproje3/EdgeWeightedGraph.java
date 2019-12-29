/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsaproje3;

import java.util.LinkedList;

/**
 *
 * @author win7
 */
public class EdgeWeightedGraph {

    private int V;
    private int E;
    private LinkedList<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList();
        }

    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public void addEdge(Edge e) {// burası her ihtimalde kalsın
        int v = e.either();
        int w = e.other(v);
        checkVertex(v);
        checkVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
//     public void addEdge(Edge e) {//the problem has been solved
//        int v = e.either();
//        int w = e.other(v);
//        checkVertex(v);
//        checkVertex(w);
//        Edge e2=new Edge(w, v, e.weight());
//        adj[v].add(e);
//        adj[w].add(e2);
//        E++;
//    }

    public int degree(int v) {
        checkVertex(v);
        return adj[v].size();
    }
    
     public LinkedList<Edge> neighborsList(int from) {
        checkVertex(from);
         return (LinkedList<Edge>) adj[from].clone();
    }

    public Edge[] neighborsArray(int from) {
       checkVertex(from);
        Edge[] ar = new Edge[adj[from].size()];
        adj[from].toArray(ar);
        return ar;
    }
    

    public LinkedList<Edge> edges() {
        LinkedList<Edge> list = new LinkedList();

        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                } else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) {
                        list.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return list;

    }

    public LinkedList<Edge> adj(int v) {
        checkVertex(v);
        return adj[v];
    }

    @Override
    public String toString() {
        String x = "";
        for (int v = 0; v < V; v++) {
            x = x + "\n";
            for (Edge e : adj[v]) {
                x = x + "  -  " + e;

            }

        }
        return x;

    }

    public static void main(String[] args) {

        EdgeWeightedGraph g = new EdgeWeightedGraph(10);
        g.addEdge(new Edge(1, 2, 12));
        g.addEdge(new Edge(2, 3, 11));
        g.addEdge(new Edge(3, 4, 3));
        g.addEdge(new Edge(3, 5, 17));
        g.addEdge(new Edge(5, 6, 9));
        g.addEdge(new Edge(6, 5, 9));
        LinkedList<Edge> l = g.edges();

        for (int i = 0; i < l.size(); i++) {
            System.out.print(l.get(i));
            System.out.println("");
        }
        System.out.println("");
        System.out.println(g.toString());

    }
}
