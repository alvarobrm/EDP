/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class Graph {
    private int [][]  adjacent; 
    private int nodes;
    private boolean empty;
    
    
    
    public Graph (int nodes){
        this.nodes= nodes;
        adjacent = new int [nodes][nodes];
        for (int i=0; i<nodes; i++){
            for (int j=0; j<nodes; j++){
                adjacent [i][j] = 0;
            }
        }
        empty=true;
    }

    public int[][] getAdjacent() {
        return adjacent;
    }

    public int getNodes() {
        return nodes;
    }

    public void setAdjacent(int[][] adjacent) {
        this.adjacent = adjacent;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }
    
    
    
    public boolean isEmpty(){
        boolean empty= false;
        for (int i=0; i<nodes; i++){
            for (int j=0; j<nodes; j++){
                empty = adjacent [i][j]==0;
                if (!empty)
                    break;
            }
            if (!empty)
                    break;
        }
        return empty;
    }
    
}
