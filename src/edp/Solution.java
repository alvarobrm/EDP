
package edp;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class Solution {
    private Instance i;
    
    
    
    public Solution(Instance i){
        this.i=i;
    }
    
  
    public int[] Dijkstra(int ini, int end){
       // valores iniciales
        int n = i.getG().getNodes();
        
        boolean [] visitados= new boolean [n];
        int [] costes = new int [n];
        int ultimo []= new int [n]; // ultimo vertice que se visito
        int [][] matrix = i.getG().getAdjacent();
        for (int i=0; i<n ; i++){
            visitados [i]=false;
            costes [i] = matrix[ini][i];
            ultimo[i]=ini;
        }
        
        visitados[ini]=true;
        costes[ini]=0;
        
        // marcar los n-1 vertices
        ArrayList <Integer> l = new ArrayList<> ();
        for (int i=0; i<n;i++){
            int v = minimo(n, visitados, costes); //seleciona el vertice no amrcado de menor disctancia
            
            visitados[v]=true;
            
            //actualiza la distancia de vertices no marcados
            for (int w=0;w<n;w++){
                if (!visitados[w]){
                    if((costes[v]+matrix[v][w]< costes[w])){
                        costes[w]= costes[v]+matrix[v][w];
                        ultimo[w]=v;
                        
                    }    
                }
            }
        }
        for (int i=0;i<n;i++)
            System.out.println("Costo minimo a "+i+": "+costes[i]);
        return ultimo;
        
    }
    
    public void camino (int[] c, int s, int e){
        if (c[e]==100000){
            System.out.println("Nodo no alcanzable");
        }else{
            final ArrayList path = new ArrayList();
            int x = e;
            while (x != s) {
                path.add("Vertice "+x);
                x = c[x];
            }
        
            path.add("Vertice "+x);
            System.out.println(path);
        }
        
    }
    
    public int minimo(int n,boolean [] vis, int [] m ){
        int mx = 100;
        int v = 1;
        for (int j=0;j<n; j++){
            if (!vis[j]&& (m[j]<= mx)){
                mx= m[j];
                v=j;
            }
        }
        return v;
    }
    
    public static void main(String[] args) {
        Instance i = new Instance ("AS-BA.R-Wax.v100e217.bb","AS-BA.R-Wax.v100e217.rpairs.10.1" );
        Solution s= new Solution(i);
        s.camino(s.Dijkstra(24, 20), 24, 10);
    }
        
        
}


