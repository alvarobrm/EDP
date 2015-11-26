/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class EDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Instance i = new Instance ("AS-BA.R-Wax.v100e217.bb","AS-BA.R-Wax.v100e217.rpairs.10.1" );
        Solution s= new Solution();

        ArrayList<Integer> del; 
        for (int j=0; j< i.getNodeMatrix().size();j++  ){
            del=Dijkstra.Dijkstra(i.getNodeMatrix().get(j) [0],i.getNodeMatrix().get(j) [1], i.getG().getAdjacent(), i, s);
            System.out.println(del);
            i.getG().setAdjacent(Dijkstra.deleteEdges(i.getG().getAdjacent(), del));
        }
        System.out.println(s.toString());
    }
    
}
