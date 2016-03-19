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
       //System.out.println(sol.routesToString());
        int pos = 1;

        while (pos <= sol.getRoutes().size()) {
            if (sol.isRouteConected(pos - 1)) {
                Instance auxInstance = new Instance(sol.getI());
                Solution auxSolution = new Solution(sol);
                auxSolution.setI(auxInstance);
                auxSolution.deletePair(pos - 1);
                ArrayList<int[]> notCon = auxSolution.getRoutesNotConected();
                auxSolution.getI().setNodeMatrix(notCon);
                ArrayList<Integer> del;
                for (int j = 0; j < auxInstance.getNodeMatrix().size(); j++) {
                    del = Dijkstra.Dijkstra(auxInstance.getNodeMatrix().get(j)[0], auxInstance.getNodeMatrix().get(j)[1], auxInstance.getG().getAdjacent(), auxInstance, auxSolution);
                    
                    auxSolution.addRoute(del, j);
                    auxInstance.getG().setAdjacent(Dijkstra.deleteEdges(auxInstance.getG().getAdjacent(), del)); // Elimino las aristas usadas
                }
                int newNotConn = sol.getNotConn() - auxSolution.getConn();
                int newConn= sol.getConn() + auxSolution.getConn();
                auxSolution.setConn(newConn);
                auxSolution.setNotConn(newNotConn);
                if (auxSolution.isBetter(bestSolution)){
                    bestSolution = auxSolution;
                }
            }

            pos++;

        }
        return bestSolution;
    }

    public static Solution localSearchRandom(Solution sol, String graph, String matrix, int rep) {
        Solution bestSolution = sol;
        int size = bestSolution.getRoutes().size();
        int pos = 1;
        
        while (pos <= size) {
            MyRandom random = new MyRandom();
            Solution auxBestSolution = new Solution(sol);
            for (int aux = 0; aux < rep; aux++) {
                if (sol.isRouteConected(pos-1)) {
                    Instance auxInstance = new Instance(sol.getI());
                    auxInstance.deletePair(auxInstance.getNodeMatrix().get(pos-1));
                    
                    auxBestSolution.setI(auxInstance);
                    Solution auxSolution = new Solution(auxBestSolution);
                    auxSolution.setI(auxInstance);
                    ArrayList<Integer> del;
                    for (int j = 0; j < auxInstance.getNodeMatrix().size(); j++) {
                        del = RandomSolution.Dijkstra(auxInstance.getNodeMatrix().get(j)[0], auxInstance.getNodeMatrix().get(j)[1], auxInstance.getG().getAdjacent(), auxInstance, auxSolution, random);
                        auxSolution.addRoute(del);
                        auxInstance.getG().setAdjacent(Dijkstra.deleteEdges(auxInstance.getG().getAdjacent(), del));
                    }
                    
                    auxBestSolution = auxSolution.whoIsBetter(auxBestSolution);
                }
            }
            int newNotConn =sol.getNotConn()- auxBestSolution.getConn()+1;
            int newConn = auxBestSolution.getConn()+ sol.getConn()-1;
            auxBestSolution.setConn(newConn);
            
            auxBestSolution.setNotConn(newNotConn);
            if (auxBestSolution.isBetter(bestSolution)){ 
                auxBestSolution.getRoutes().addAll(sol.getRoutes());
                bestSolution = auxBestSolution;
                
            }
            pos ++;
            
        }
        return bestSolution;
    }

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        String op = "1";
        String nameGraph = "instancias/AS-BA.R-Wax.v100e217.bb";
        String nameMatriz = "instancias/AS-BA.R-Wax.v100e217.rpairs.10.";
        while (op.equals("1") || op.equals("2")) {
            System.out.println("Pulse 1 para Dijkstra y 2 para Aleatoreo. Otro para terminar");
            op = sn.nextLine();
            switch (op) {
                case "1":
                    //Ejecutar caminos mas cortos
                    for (int w = 1; w < 21; w++) {

                        
                        Instance instance = new Instance(nameGraph, nameMatriz + w);
                        Instance localSeachInstance = new Instance(nameGraph, nameMatriz + w);
                        
                        double time = 0;
                        double start = System.currentTimeMillis();

                       

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
                        MyRandom random = new MyRandom();
                        Solution s1 = new Solution();
                        Instance instance = new Instance(nameGraph, nameMatriz+ w);
                        double time = 0;
                        double start = System.currentTimeMillis();
                        
                        for (int aux = 0; aux < rep; aux++) {
                            Instance solutionInstance = new Instance(instance);
                            s1.setI(solutionInstance);
                            Solution s = new Solution();
                            s.setI(solutionInstance);
                            ArrayList<Integer> del;
                            for (int j = 0; j < solutionInstance.getNodeMatrix().size(); j++) {
                                del = RandomSolution.Dijkstra(solutionInstance.getNodeMatrix().get(j)[0], solutionInstance.getNodeMatrix().get(j)[1], solutionInstance.getG().getAdjacent(), solutionInstance, s, random);
                                s.addRoute(del);
                                solutionInstance.getG().setAdjacent(Dijkstra.deleteEdges(solutionInstance.getG().getAdjacent(), del));
                            }
                            //Llamada a busqueda local
                            Solution bestSolution = localSearchRandom(s, nameGraph, nameMatriz+w , rep);
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
    }

}
