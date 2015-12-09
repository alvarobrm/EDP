/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.util.ArrayList;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author alvar
 */
public class EDP {

    /**
     * @param args the command line arguments
     */
    
    public static void printGraph(edp.Graph g, Solution s){
        int n = g.getNodes();
        org.graphstream.graph.Graph gr = new SingleGraph("G1"); 
        for (int i=0; i<n;i++){
           gr.addNode(""+i);
        }
        int [][] m = g.getAdjacent();
        for (int i=0; i<n; i++){
            for (int j =0; j<n; j++){
                if (m[i][j]== 1){
                    gr.addEdge(i+"-"+j, ""+i,""+j);
                    m[j][i]= 0;
                }
                    
            }
        }
        
        for (Node node : gr) {
			node.addAttribute("ui.label", node.getId());
			node.addAttribute("ui.style", "size: 30px; "
										+ "text-alignment: center;"
										+ "shape: box;"
										+ "fill-color: green;");
        }
        gr.addAttribute("ui.antialias", true);
        gr.display();
    }
    
    
    
    public static void main(String[] args) {
        
        long start =System.currentTimeMillis();
        Instance i = new Instance ("instancias/AS-BA.R-Wax.v100e217.bb","instancias/AS-BA.R-Wax.v100e217.rpairs.10.1" );
        Solution s= new Solution();

        
        ArrayList<Integer> del; 
        for (int j=0; j< i.getNodeMatrix().size();j++  ){
            del=Dijkstra.Dijkstra(i.getNodeMatrix().get(j) [0],i.getNodeMatrix().get(j) [1], i.getG().getAdjacent(), i, s);
            s.addRoute(del);
            i.getG().setAdjacent(Dijkstra.deleteEdges(i.getG().getAdjacent(), del));
        }
        System.out.println(s.toString());
        long end =System.currentTimeMillis();
        long time = end - start;
        System.out.println("Tiempo empleado: "+time+" ms");
        System.out.println("¿Desea ver las rutas?s/n");
        Scanner scan = new Scanner (System.in);
        String op=scan.nextLine();
        if (op.equals("s"))
           System.out.println(s.routesToString());
    }
    
}
