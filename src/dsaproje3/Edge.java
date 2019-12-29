/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsaproje3;

/**
 *
 * @author win7
 */
public class Edge implements Comparable<Edge> {

    private int v;
    private int w;
    private double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }

        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {//end point
        return v;
    }

    public int other(int vertex) {//control and accesing other end point
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    @Override
    public int compareTo(Edge o) {
       if(this.weight>o.weight)
       {
           return 1;
       }
       else if(this.weight<o.weight){
           return -1;
       }
       else
           return 0;
    }
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    @Override
    public boolean equals(Object obj) {
    
        if(obj instanceof Edge){
            Edge e=(Edge)obj;
            if(this.v==e.v&&this.w==e.w)
                return true;
        }
        return false;
        
    
    }
    


}
