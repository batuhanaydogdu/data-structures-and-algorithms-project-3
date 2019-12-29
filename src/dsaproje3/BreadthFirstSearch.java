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
public class BreadthFirstSearch {

    boolean[] marked;
    int[] edgeTo;
    int[] distTo;
    int from;
    
    public BreadthFirstSearch(EdgeWeightedGraph g, int from) {
        edgeTo = new int[g.V()];// warning this is initalized to 0 
        marked = new boolean[g.V()];
        distTo = new int[g.V()];
        this.from = from;
        bfs(g,from);// this is not recursive
    }
    
    public boolean hasPathTo(int w) {
        return marked[w];
    }
    
    public int distTo(int w) {
        return distTo[w];
    }

    public Integer[] pathTo(int w) {
        int k = edgeTo[w];
        java.util.Stack<Integer> st = new java.util.Stack<Integer>();
        st.push(k);
        while (k != this.from) {
            k = edgeTo[k];
            st.push(k);
        }
        //st.push(from);

        Integer[] path = new Integer[st.size()];
        for (int i = 0; i <path.length; i++)
            path[i] = st.pop();
        return path;
    }
    
    public void printPathTo(int w) {
        
        Integer[] path = pathTo(w);
        
        for(int i = 0; i < path.length; i++){
            
            System.out.print("->"+path[i]);
        }
        System.out.print("->"+w);
    }

    // this is not recursive
    public void bfs(EdgeWeightedGraph g, int source) {
        marked[source] = true;
        Edge[] a = (Edge[]) g.neighborsArray(source);
        if (a.length == 0) {
            return;
        }
        // this is to work as a queue
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.addLast(source);
        while (!q.isEmpty()) {
            source = q.removeFirst();
            a = (Edge[]) g.neighborsArray(source);
            for (int i = 0; i < a.length; i++) {
                int w = a[i].other(a[i].either());
                if (!marked[w]){
//                    System.out.println(w+".");
                    q.addLast(w);
                    marked[w] = true;
                    edgeTo[w] = source;
                    distTo[w] = distTo[source] + 1;
                }
            }
        }
    }
}

