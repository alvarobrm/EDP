package edp;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class EDP {

   private static final boolean activeVNS = true;
   private static final double PERCENTAGE = 0.5;
   
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
      
        int pos = 1;
        int cont =0 ;
        while (pos <= sol.getRoutes().size()) {
            if (sol.isRouteConected(pos - 1)) {
                Instance auxInstance = new Instance(sol.getI());
                Solution auxSolution = new Solution(sol);
                auxSolution.setI(auxInstance);
                auxSolution.deletePair(pos - 1);
                ArrayList<int[]> notCon = auxSolution.getRoutesNotConected();
                auxSolution.getI().setNodeMatrix(notCon);
                ArrayList<Integer> del;
                for (int j = 0; j < sol.getI().getNodeMatrix().size(); j++) {
                    if(sol.getI().getNodeMatrix().get(j)[0]== auxSolution.getI().getNodeMatrix().get(cont)[0]){
                        del = DijkstraSolution.Dijkstra(auxInstance.getNodeMatrix().get(cont)[0], auxInstance.getNodeMatrix().get(cont)[1], auxInstance.getG().getAdjacent(), auxInstance, auxSolution);
                        if (!del.isEmpty()){
                            auxSolution.addRoute(del, j);
                            auxInstance.getG().setAdjacent(DijkstraSolution.deleteEdges(auxInstance.getG().getAdjacent(), del)); // Elimino las aristas usadas
                        }
                        cont ++;
                    }
                    
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

    public static Solution localSearchFI(Solution sol, String graph, String matrix, int rep, MyRandom random, String opcion, int numDesc) {
        Solution bestSolution = new Solution(sol);
        int size = bestSolution.getRoutes().size();
        int pos = 1;
        
        Collections.shuffle(bestSolution.getRoutes(), new Random(6));
        while (pos <= size) {
            Solution auxBestSolution = new Solution(bestSolution);
            for (int aux = 0; aux < rep; aux++) {
                if (auxBestSolution.isRouteConected(pos-1)) { 
                    Instance auxInstance = new Instance(sol.getI());
                    auxBestSolution.setI(auxInstance);
                    Solution auxSolution = new Solution(auxBestSolution);
                    auxSolution.setI(auxInstance);
                    int countDesc = 1;
                    int auxPos = pos;
                    auxSolution.deletePair(pos-1);
                    while (countDesc < numDesc && auxPos <=size ){
                        if(auxBestSolution.isRouteConected(auxPos-1)){
                            auxSolution.deletePair(auxPos-1);
                            countDesc ++;
                        }
                        auxPos++;
                    } 
                    
                    ArrayList<int[]> notCon = auxBestSolution.getRoutesNotConected();
                    if(notCon.size() == 0)
                        break;
                    auxSolution.getI().setNodeMatrix(notCon);
                    int cont = 0;
                    ArrayList<Integer> del = new ArrayList<>();
                    for (int j = 0; j < sol.getI().getNodeMatrix().size(); j++) {
                        if (sol.getI().getNodeMatrix().get(j)[0]== auxSolution.getI().getNodeMatrix().get(cont)[0]){
                            if(opcion.equals("1")){
                                //PONER DIJKSTRA AQUI
                                
                            }else{
                                del = GraspSolution.solve(cont,auxSolution, random);
                            }
                            if (!del.isEmpty()){
                                auxSolution.addRoute(del, j);
                                auxInstance.getG().setAdjacent(DijkstraSolution.deleteEdges(auxInstance.getG().getAdjacent(), del));
                            } 
                            cont++;
                        }
                        if (cont >= auxSolution.getI().getNodeMatrix().size())
                            break;
                    }
                    auxBestSolution = auxSolution.whoIsBetter(auxBestSolution);
                }
            }
            int conn = auxBestSolution.getConn();
            int newNotConn =sol.getNotConn()- auxBestSolution.getConn();
            int newConn = auxBestSolution.getConn()+ sol.getConn();
            auxBestSolution.setConn(newConn);
            auxBestSolution.setNotConn(newNotConn);
            if (conn>=1){
                ArrayList<int[]> pair = new ArrayList<>();
                pair.add(sol.getI().getNodeMatrix().get(pos-1));
                for( int i =0; i<= rep; i++){
                    Solution aux = new Solution(auxBestSolution);
                    aux.setI(auxBestSolution.getI());
                    aux.getI().setNodeMatrix(pair);
                    ArrayList<Integer> del = GraspSolution.solve(0,aux, random);
                    if (auxBestSolution.getConn()==9 && del.size()==1)
                        System.out.println("HOLA");
                    if (!del.isEmpty()){
                        auxBestSolution.addRoute(del, pos-1);
                        auxBestSolution.addConn();
                        auxBestSolution.setNotConn(auxBestSolution.getNotConn()-1);
                        break;
                    }
                }
            }
            
            if (auxBestSolution.isBetter(bestSolution)){   
                bestSolution = auxBestSolution;    
            }
            pos ++;
            if (bestSolution.getConn()==10){
                break;
            }
            
        }
        return bestSolution;
    }

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        String op = "1";
        String nameGraph = "instancias/AS-BA.R-Wax.v100e217.bb";
        String nameMatriz = "instancias/AS-BA.R-Wax.v100e217.rpairs.40.";
        while (op.equals("1") || op.equals("2")) {
            System.out.println("Pulse 1 para Dijkstra y 2 para GRASP. Otro para terminar");
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
                            del = DijkstraSolution.Dijkstra(instance.getNodeMatrix().get(j)[0], instance.getNodeMatrix().get(j)[1], instance.getG().getAdjacent(), instance, solution);

                            solution.addRoute(del);
                            instance.getG().setAdjacent(DijkstraSolution.deleteEdges(instance.getG().getAdjacent(), del)); // Elimino las aristas usadas
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
                    MyRandom random = new MyRandom();
                    for (int w = 1; w < 21; w++) {
                        
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
                                del = GraspSolution.solve(j,s, random);
                                s.addRoute(del);
                                solutionInstance.getG().setAdjacent(DijkstraSolution.deleteEdges(solutionInstance.getG().getAdjacent(), del));
                            }
                            //Llamada a busqueda local
                            Solution bestSolution = localSearchFI(s, nameGraph, nameMatriz+w , rep, random, op, 1);
                            bestSolution.i = s.getI();
                            //VNS
                            if(activeVNS){
                                double kMaxAux = bestSolution.getConn()*PERCENTAGE;
                                int kMax = (int) kMaxAux;
                                int i=0;
                                while (i<=kMax){
                                    Instance auxInstance = new Instance(s.getI());
                                    bestSolution.setI(auxInstance);
                                    Solution VNSBestSolution = localSearchFI(bestSolution, nameGraph, nameMatriz+w , rep, random, op, i);
                                    if(!bestSolution.isBetterOrEqual(VNSBestSolution)){
                                        bestSolution = VNSBestSolution;
                                        i=0;
                                    }
                                    if (bestSolution.getConn() == 10){
                                        i = kMax+1;
                                    }
                                    i++;
                                }
                            }
                            s1 =bestSolution.whoIsBetter(s1);
                            if (s1.getConn() == 10)
                                break;
                        }
                        double end = System.currentTimeMillis();
                        time = end - start;
                        time = time / 1000; 
                        s1.setTime(time);
                        System.out.println(s1.routesToString());
                        System.out.println("---------------------");
                        //System.out.println("Tiempo empleado: " + time + " s");
                        writeFile(s1, "salidaRandom.csv");
                    }
            }
        }
    }

}
