/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alvar
 */
public class EDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Instance i = new Instance ("instancias/AS-BA.R-Wax.v100e217.bb","instancias/AS-BA.R-Wax.v100e217.rpairs.40.20" );
        Solution s= new Solution();

        ArrayList<Integer> del; 
        for (int j=0; j< i.getNodeMatrix().size();j++  ){
            del=Dijkstra.Dijkstra(i.getNodeMatrix().get(j) [0],i.getNodeMatrix().get(j) [1], i.getG().getAdjacent(), i, s);
            s.addRoute(del);
            i.getG().setAdjacent(Dijkstra.deleteEdges(i.getG().getAdjacent(), del));
        }
        System.out.println(s.toString());
        
        System.out.println("¿Desea ver las rutas?s/n");
        Scanner scan = new Scanner (System.in);
        String op=scan.nextLine();
        if (op.equals("s"))
           System.out.println(s.routesToString());
    }
    
}
