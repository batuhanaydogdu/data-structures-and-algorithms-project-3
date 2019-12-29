/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsaproje3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author win7
 */
public class SocialGraphNetwork {

    private Hash<Integer, City> citiesHash = new Hash(4);
    private int numV;
    private int numE;
    private EdgeWeightedGraph ewg;

    public SocialGraphNetwork() {

        try {
            createCitiesFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SocialGraphNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        ewg = loadFilesGraph();

        creatingCities();

    }

    private void createCitiesFile() throws FileNotFoundException {
        Random rnd = new Random();
        int V = rnd.nextInt(8) + 5;
        int E = rnd.nextInt(5) + 10;
        this.numV = V;
        this.numE = E;
        File f = new File("cities.txt");
        PrintWriter pr = new PrintWriter(f);

        pr.println(V);
        pr.println(E);
        int[][] a = new int[E][2];
        for (int i = 0; i < E; i++) {
            int v1 = rnd.nextInt(V);
            int v2 = rnd.nextInt(V);

            while (v1 == v2 || checkExist(a, v1, v2)) {
                v1 = rnd.nextInt(V);
                v2 = rnd.nextInt(V);
            }
//            System.out.println(v1 + " " + v2);
            pr.println(v1 + " " + v2);
            a[i][0] = v1;
            a[i][1] = v2;

        }
        pr.close();

    }

    private boolean checkExist(int[][] a, int v1, int v2) {
        for (int i = 0; i < a.length; i++) {
            if ((a[i][0] == v1 && a[i][1] == v2) || a[i][0] == v2 && a[i][1] == v1) {
                return true;

            }

        }
        return false;

    }

    private void creatingCities() {
        String[] cities = {"roma", "budapeşte", "istanbul", "paris", "londra", "moskova", "sivas", "atina", "kıprıs", "washington", "ottava", "pekin", "kanalistanbul", "panama", "kahire"};

        for (int i = 0; i < this.numV; i++) {

            City c = new City(cities[i], i);
            citiesHash.put(i, c);

        }
    }

    private EdgeWeightedGraph loadFilesGraph() {
        File f = new File("cities.txt");
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(this.numV + 1);;
        String longstring;

        try {
            Scanner scn = new Scanner(new BufferedReader(new FileReader(f)));
            scn.nextLine();
            scn.nextLine();

            while (scn.hasNextLine()) {
                String s = scn.nextLine();
                Edge e = creatingEdge(s);
                ewg.addEdge(e);

            }
            scn.close();
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
        }
        return ewg;

    }

    private Edge creatingEdge(String s) {
        String[] a = s.split(" ");
        Random rnd = new Random();
        int v1 = Integer.valueOf(a[0]);
        int v2 = Integer.valueOf(a[1]);
        Edge e = new Edge(v1, v2, rnd.nextInt(921) + 80);
        return e;
    }

    public void deneme() {

        File f = new File("cities.txt");
        Scanner scn;
        try {
            scn = new Scanner(new BufferedReader(new FileReader(f)));
            System.out.println(scn.nextLine());
            System.out.println(scn.nextLine());

            while (scn.hasNextLine()) {
                String s = scn.nextLine();
                Edge e = creatingEdge(s);
                System.out.println(e);

            }
            scn.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SocialGraphNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void printAllCities() {

        System.out.println(this.citiesHash.toString());
    }

    public void printCloseCities(int tocity) {

        Edge[] edgelist = ewg.neighborsArray(tocity);

        for (int i = 0; i < edgelist.length; i++) {

            if (edgelist[i].weight() < 300) {
                int other = edgelist[i].other(tocity);
                System.out.print(this.citiesHash.get(other));
                System.out.println("  " + edgelist[i].weight() + " km");

            }

        }

    }

    public void printFartherCities(int tocity) {
        Edge[] edgelist = ewg.neighborsArray(tocity);

        for (int i = 0; i < edgelist.length; i++) {

            if (edgelist[i].weight() > 500) {
                int other = edgelist[i].other(tocity);
                System.out.print(this.citiesHash.get(other));
                System.out.println("  " + edgelist[i].weight() + " km");

            }

        }
    }

    public boolean isConnected(City c1, City c2) {
        Edge[] edgelist = this.ewg.neighborsArray(c1.getVertexNum());
        for (int i = 0; i < edgelist.length; i++) {

            int other = edgelist[i].other(c1.getVertexNum());
            System.out.println(other + " " + c2.getVertexNum());
            if (other == c2.getVertexNum()) {
                return true;
            }

        }
        return false;
    }

    public void pathTo(City c1, City c2) {
        int v1 = c1.getVertexNum();
        int v2 = c2.getVertexNum();
        BreadthFirstSearch bfs = new BreadthFirstSearch(ewg, v1);
        Integer[] a = bfs.pathTo(v2);

        if (a == null) {
            System.out.println("there is no way. they aren't connected");
        } else {

            for (int i = 0; i < a.length; i++) {

                System.out.print(this.citiesHash.get(a[i]).getName() + " ---> ");

            }
            System.out.print(this.citiesHash.get(v2).getName());
            System.out.println("");
        }
    }

    public void NumberofCityGroups() {

        ConnectedComponents cc = new ConnectedComponents(ewg);
        int[] a = cc.getId();
//        int ind=0;
//        int gind=0;
//        System.out.println("Group "+gind+"  ");

        int currentind = 0;
//        System.out.println("Group " + (currentind + 1));
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] == currentind) {
//                System.out.print(this.citiesHash.get(i).getName() + " ");
//            } else {
//
//               
//                currentind++;
//               
//                System.out.println("");
//                System.out.println("Group " + (currentind + 1));
//                i--;
//            }
//            if(currentind>a[a.length-1])
//                   break;
//
//        }
            
            int max=a[0];
            for(int i=1;i<a.length;i++){
                if(a[i]>max)
                    max=a[i];
            }
           
            for(int i=0;i<max;i++){
                  System.out.println("Group "+(currentind+1));
                for(int j=0;j<a.length;j++){
                    if(a[j]==currentind){
                        System.out.print(this.citiesHash.get(j).getName()+" ");
                    }
                }
                currentind++;
                System.out.println("");
              
                
                
            }
                


//        
//        while(ind<a.length-1){
//            
//            if(gind>cc.getCount())
//                break;
//            if(a[ind]==gind){
//                System.out.print(this.citiesHash.get(ind).getName()+" ");
//                ind++;
//            }
//            else{
//                System.out.println("");
//                gind++;
//                System.out.println("Group "+gind);
//            }
//            
//        }
    }
    
    public void minimumDistance(City c1,City c2){
        
        DijkstraUndirectedSP dudsp= new DijkstraUndirectedSP(ewg, c1.getVertexNum());
        if(dudsp.hasPathTo(c2.getVertexNum())){
            System.out.println("distance c1 to c2 is "+ dudsp.distTo(c2.getVertexNum()));
        }
        else
            System.out.println("There is no path");
        
    }

    public static void main(String[] args) {

        SocialGraphNetwork sgn = new SocialGraphNetwork();
        Scanner x = new Scanner(System.in);
        sgn.printAllCities();
//        System.out.println("enter city number to see close cities");
//        int a=x.nextInt();
//        sgn.printCloseCities(a);
//        System.out.println("enter city number to see farther cities");
//        int a2 = x.nextInt();
//        sgn.printFartherCities(a);
//        System.out.println(sgn.isConnected(sgn.citiesHash.get(1), sgn.citiesHash.get(2)));
//          BreadthFirstSearch bfs=new BreadthFirstSearch(sgn.ewg, 0);
//          bfs.printPathTo(6);
//        sgn.pathTo(sgn.citiesHash.get(2), sgn.citiesHash.get(5));
          sgn.NumberofCityGroups();
          System.out.println("");
        sgn.minimumDistance(sgn.citiesHash.get2(2), sgn.citiesHash.get2(4));
    }

}
