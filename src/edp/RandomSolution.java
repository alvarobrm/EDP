/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.util.ArrayList;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class RandomSolution {
     public static ArrayList<Integer>  Dijkstra(int ini,int fin,  int[][] m, Instance ins, Solution s){
       // valores iniciales
        int n = ins.getG().getNodes();
        
        boolean [] visitados= new boolean [n];
        int [] costes = new int [n];
        int ultimo []= new int [n]; // ultimo vertice que se visito
        int [][] matrix = m;
        for (int i=0; i<n ; i++){
            visitados [i]=false;
            costes [i] = matrix[ini][i];
            ultimo[i]=matrix[ini][i]<100000?ini:0;
        }
        
        visitados[ini]=true;
        costes[ini]=0;

        ultimo[ini]=0;
        
        // marcar los n-1 vertices
        ArrayList <Integer> l = new ArrayList<> ();
        for (int i=0; i<n-1;i++){
         
        int v;
             for (v=0; (v<visitados.length) && (visitados[v]); v++) {}
         
         int men=v;
         
         for (; v<visitados.length; v++) {
             int rand = (int) (MyRandom.nextInt(1000)) % 2;
            if (!(visitados[v]) && (costes[v]<costes[men]) && (costes[v]>0) && rand !=0)
               men = v;
         }
            visitados[men]=true;
            //actualiza la distancia de vertices no marcados
            if (costes[men]==-1)
                continue;
            for (int w = 0; w < n; w++) {
                if (!visitados[w]) {
                    if ((matrix [men][w]==-1))
                        continue;
                    if ((costes[men] + matrix[men][w] < costes[w])) {
                        costes[w] = costes[men] + matrix[men][w];
                       
                        ultimo[w] = men;

                    }

                }
            }
            
        }
        ArrayList<Integer> del = new ArrayList<>();
        imprimirCaminos (ultimo, ini,fin,  costes, del, s);
        return del;
        
    }
    
    private static void imprimirCaminos (int[] preds, int origen, int dest, int[] distancias, ArrayList<Integer> del, Solution s) {
         if (distancias[dest]>=100000||distancias[dest]<0){ /*valor tomado como infinito*/
            s.addNotConn();
         }
         else{
            imprimirCamino(preds,origen,dest, del);
            s.addConn();
         }
   }
   
   private static void imprimirCamino (int[] preds, int origen, int j, ArrayList<Integer> del) {
      if (j!=origen)
         imprimirCamino (preds,origen,preds[j], del);
      del.add(j);
      
   }
    
    
    
    public static int [][] deleteEdges (int [][] m, ArrayList<Integer> e){
        for (int i = 0 ; i<e.size()-1; i++){
            int a1= e.get(i);
            int a2 = e.get(i+1);
            m[a1][a2]= -1;
            m[a2][a1]= -1;
        }
        return m;
    }
    
}
