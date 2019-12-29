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
public class City {
    
    private String name;
    private int vertexNum;

    public City(String name,int vertexNum) {
        this.vertexNum=vertexNum;
        this.name = name;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "name : "+name+" vertexNum : "+vertexNum+"      ";
    }
    
    

    @Override
    public int hashCode() {
        return name.hashCode()+31*vertexNum;

    }

    
    
}
