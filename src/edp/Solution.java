
package edp;

import java.util.ArrayList;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class Solution {
    private int conn;
    private int notConn;
    private ArrayList<ArrayList<Integer>> routes;
    
    
    
    public Solution(){
        conn=0;
        notConn=0;
        routes= new ArrayList<> ();
    }

    public int getConn() {
        return conn;
    }

    public int getNotConn() {
        return notConn;
    }

    public ArrayList<ArrayList<Integer>> getRoutes() {
        return routes;
    }

    public void setConn(int conn) {
        this.conn = conn;
    }

    public void setNotConn(int notConn) {
        this.notConn = notConn;
    }

    public void setRoutes(ArrayList<ArrayList<Integer>> rutes) {
        this.routes = rutes;
    }
    
    public void addConn (){
        this.conn++;
    }
    
    public void addNotConn(){
        this.notConn++;
    }
    
    public void addRoute(ArrayList<Integer> l){
        routes.add(l);
    }

    @Override
    public String toString() {
        String s= "Nodos que se han podido conectar: "+conn+"\n";
        s=s+ "Nodos que no se han podido conectar: "+notConn+"\n";
        return s;
    }
    
    public String routesToString (){
        String s="";
        for (int i = 0; i < routes.size(); i++){
            if (routes.get(i).size()==0)
                s=s+"Camino no alcanzable\n";
            else
                s=s+routes.get(i).toString()+"\n";
        }
        return s;
    }
    
    
    
  

        
        
}


