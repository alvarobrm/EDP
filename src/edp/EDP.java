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
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

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
    
    public static Solution localSearch(Solution sol, Instance instance) {
        Solution bestSolution = sol;
        Instance auxInstance;
        int pos = 1;
        while (pos <= bestSolution.getConn()) {
            auxInstance = new Instance(instance);
            Solution auxSolution = new Solution();
            auxSolution.setI(auxInstance);
            //eliminar pares
            if (bestSolution.isRouteConected(pos - 1)) {
                auxInstance.deletePair(bestSolution.getRoutes().get(pos - 1));
                ArrayList<Integer> del;
                for (int j = 0; j < auxInstance.getNodeMatrix().size(); j++) {
                    del = Dijkstra.Dijkstra(auxInstance.getNodeMatrix().get(j)[0], auxInstance.getNodeMatrix().get(j)[1], auxInstance.getG().getAdjacent(), auxInstance, auxSolution);

                    auxSolution.addRoute(del);
                    auxInstance.getG().setAdjacent(Dijkstra.deleteEdges(auxInstance.getG().getAdjacent(), del)); // Elimino las aristas usadas
                }
                if (auxSolution.isBetter(bestSolution)) {
                    bestSolution = auxSolution;
                    pos = 1;
                } else {
                    pos++;
                }

            } else {
                pos++;
            }
        }
        return bestSolution;
    }

    public static Solution localSearchRandom(Solution sol, Instance instance, int rep) {
        Solution bestSolution = sol;

        int pos = 1;
        while (pos <= bestSolution.getConn()) {
            Solution auxBestSolution = new Solution();
            for (int aux = 0; aux < rep; aux++) {
                if (bestSolution.isRouteConected(pos - 1)) {
                    Instance auxInstance = new Instance(instance);
                    auxInstance.deletePair(bestSolution.getRoutes().get(pos - 1));
                    
                    auxBestSolution.setI(auxInstance);
                    Solution auxSolution = new Solution();
                    auxSolution.setI(auxInstance);
                
                    ArrayList<Integer> del;
                    for (int j = 0; j < auxInstance.getNodeMatrix().size(); j++) {
                        del = RandomSolution.Dijkstra(auxInstance.getNodeMatrix().get(j)[0], auxInstance.getNodeMatrix().get(j)[1], auxInstance.getG().getAdjacent(), auxInstance, auxSolution);
                        auxSolution.addRoute(del);
                        auxInstance.getG().setAdjacent(Dijkstra.deleteEdges(auxInstance.getG().getAdjacent(), del));
                    }
                    auxBestSolution = auxBestSolution.whoIsBetter(auxSolution);
                }
            }
            if (auxBestSolution.isBetter(bestSolution)){
                bestSolution = auxBestSolution;
                pos =1;
            }else{
                pos ++;
            }
        }

        return bestSolution;
    }

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        String op = "1";

        while (op.equals("1") || op.equals("2")) {
            System.out.println("Pulse 1 para Dijkstra y 2 para Aleatoreo. Otro para terminar");
            op = sn.nextLine();
            switch (op) {
                case "1":
                    //Ejecutar caminos mas cortos
                    for (int w = 1; w < 21; w++) {

                        double time = 0;
                        double start = System.currentTimeMillis();

                        Instance instance = new Instance("instancias/AS-BA.R-Wax.v100e217.bb", "instancias/AS-BA.R-Wax.v100e217.rpairs.40." + w);
                        Instance localSeachInstance = new Instance("instancias/AS-BA.R-Wax.v100e217.bb", "instancias/AS-BA.R-Wax.v100e217.rpairs.40." + w);

                        Solution solution = new Solution();
                        solution.setI(instance);
                        ArrayList<Integer> del;
                        for (int j = 0; j < instance.getNodeMatrix().size(); j++) {
                            del = Dijkstra.Dijkstra(instance.getNodeMatrix().get(j)[0], instance.getNodeMatrix().get(j)[1], instance.getG().getAdjacent(), instance, solution);

                            solution.addRoute(del);
                            instance.getG().setAdjacent(Dijkstra.deleteEdges(instance.getG().getAdjacent(), del)); // Elimino las aristas usadas
                        }
                        //Llamada a busqueda local
                        Solution bestSolution = localSearch(solution, localSeachInstance);
                        //System.out.println(s.toString());
                        double end = System.currentTimeMillis();
                        time = end - start;
                        time = time / 1000;
                        bestSolution.setTime(time);
                        System.out.println("Tiempo empleado: " + time + " s");
                        writeFile(bestSolution, "salida.csv");
                    }
                    break;
                case "2":
                    System.out.println("Introduzca el nº de repeticiones");
                    int rep = Integer.parseInt(sn.nextLine());
                    //Ejecutar solucion aleatoria
                    for (int w = 1; w < 21; w++) {
                        
                        Solution s1 = new Solution();
                        double time = 0;
                        double start = System.currentTimeMillis();
                        
                        Instance localSeachInstance = new Instance("instancias/AS-BA.R-Wax.v100e217.bb", "instancias/AS-BA.R-Wax.v100e217.rpairs.40." + w);
                        for (int aux = 0; aux < rep; aux++) {
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
                            //Llamada a busqueda local
                            Solution bestSolution = localSearchRandom(s, localSeachInstance, rep);
                            s1 =bestSolution.whoIsBetter(s1);
                            double end = System.currentTimeMillis();
                            time = end - start;
                            time = time / 1000;
                            
                        }
                        s1.setTime(time);
                        System.out.println("Tiempo empleado: " + time + " s");
                        writeFile(s1, "salidaRandom.csv");
                    }
            }
        }
        /*System.out.println("¿Desea ver las rutas?s/n");

        Scanner scan = new Scanner(System.in);
        String op = scan.nextLine();
        if (op.equals("s")) {
            System.out.println(s.routesToString());
        }*/
    }

}
