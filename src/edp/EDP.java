/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
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
                    m[j][i] = 0;
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

    public static void writeFile(Solution s, String f) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(f, true);
            pw = new PrintWriter(fichero);
            pw.println(s.getI().getNameFile() + ";" + s.getConn() + ";" + s.getNotConn() + ";" + s.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
           // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        //Ejecutar solucion aleatoria
        /*for (int w = 1; w < 21; w++) {

            Solution s1 = new Solution();
            double time = 0;
            double start = System.currentTimeMillis();

            for (int aux = 0; aux < 1000; aux++) {
                Instance i = new Instance("instancias/AS-BA.R-Wax.v100e217.bb", "instancias/AS-BA.R-Wax.v100e217.rpairs.40." + w);
                s1.setI(i);
                Solution s = new Solution();
                s.setI(i);
                ArrayList<Integer> del;
                for (int j = 0; j < i.getNodeMatrix().size(); j++) {
                    //del = Dijkstra.Dijkstra(i.getNodeMatrix().get(j)[0], i.getNodeMatrix().get(j)[1], i.getG().getAdjacent(), i, s);
                    del = RandomSolution.Dijkstra(i.getNodeMatrix().get(j)[0], i.getNodeMatrix().get(j)[1], i.getG().getAdjacent(), i, s);
                    s.addRoute(del);
                    i.getG().setAdjacent(Dijkstra.deleteEdges(i.getG().getAdjacent(), del));
                }
                System.out.println(s.toString());
                double end = System.currentTimeMillis();
                time = end - start;
                time = time /1000;
                s.setTime(time);
                s1 = s1.isBetter(s);
                
            }
            s1.setTime(time);
            System.out.println("Tiempo empleado: " + time + " s");
            writeFile(s1, "salidaRandom.csv");
        }*/

        //Ejecutar caminos mas cortos
        for (int w = 1; w < 21; w++) {

            double time = 0;
            double start = System.currentTimeMillis();

            Instance i = new Instance("instancias/AS-BA.R-Wax.v100e217.bb", "instancias/AS-BA.R-Wax.v100e217.rpairs.40." + w);
            
            Solution s = new Solution();
            s.setI(i);
            ArrayList<Integer> del;
            for (int j = 0; j < i.getNodeMatrix().size(); j++) {
                del = Dijkstra.Dijkstra(i.getNodeMatrix().get(j)[0], i.getNodeMatrix().get(j)[1], i.getG().getAdjacent(), i, s);

                s.addRoute(del);
                i.getG().setAdjacent(Dijkstra.deleteEdges(i.getG().getAdjacent(), del));
            }
            System.out.println(s.toString());
            double end = System.currentTimeMillis();
            time = end - start;
            time= time/1000;
            s.setTime(time);
            System.out.println("Tiempo empleado: " + time + " s");
            writeFile(s, "salida.csv");
        }

        /*System.out.println("¿Desea ver las rutas?s/n");

        Scanner scan = new Scanner(System.in);
        String op = scan.nextLine();
        if (op.equals("s")) {
            System.out.println(s.routesToString());
        }*/
    }

}
