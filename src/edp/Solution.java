
package edp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alvaro Berrocal Martin - URJC
 */
public class Solution {
    private Instance i;
    
    
    
    public Solution(Instance i){
        this.i=i;
    }
    
  
<<<<<<< HEAD
    public ArrayList<Integer> Dijkstra(int ini, int end){
=======
    public ArrayList<Integer>  Dijkstra(int ini,int fin,  int[][] m){
>>>>>>> dev
       // valores iniciales
        int n = i.getG().getNodes();
        
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
<<<<<<< HEAD
        ArrayList<Integer> c =new ArrayList <>();
        // marcar los n-1 vertices
        c.add(ini);
        
        for (int i=0; i<n;i++){
            int v = minimo(n, visitados, costes); //seleciona el vertice no amrcado de menor disctancia
            
            visitados[v]=true;
            c.add(v);
         //actualiza la distancia de vertices no marcados
            for (int w=0;w<n;w++){
                if (!visitados[w]){
                    if((costes[v]+matrix[v][w]< costes[w])){
                        costes[w]= costes[v]+matrix[v][w];
                        ultimo[w]=v;
                    }    
                }
            }
            if (v== end)
                break;
        }
        for (int i=0;i<n;i++)
            System.out.println("Costo minimo a "+i+": "+costes[i]);
        return c;
                
=======
        ultimo[ini]=0;
        
        // marcar los n-1 vertices
        ArrayList <Integer> l = new ArrayList<> ();
        for (int i=0; i<n-1;i++){
            //int v = minimo(n, visitados, costes); //seleciona el vertice no marcado de menor distancia
            int v;
             for (v=0; (v<visitados.length) && (visitados[v]); v++) {}
         
         int men=v;
         for (; v<visitados.length; v++) {
            if (!(visitados[v]) && (costes[v]<costes[men]) && (costes[v]>0))
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
            //matrix[ini]= costes;
        }
        /*for (int i=0;i<n;i++)
            System.out.println("Costo minimo a "+i+": "+costes[i]);*/
        ArrayList<Integer> del = new ArrayList<>();
        imprimirCaminos (ultimo, ini,fin,  costes, del);
        return del;
>>>>>>> dev
        
    }
    
    private static void imprimirCaminos (int[] preds, int origen, int dest, int[] distancias, ArrayList<Integer> del) {
      //for (int i=0; i<preds.length; i++) {
         System.out.println("Ruta entre el vertice "+origen+" y el vertice "+dest+":"+ distancias[dest]);
         if (distancias[dest]>=100000||distancias[dest]<0) /*valor tomado como infinito*/
            System.out.print ("No alcanzable");
         else
            imprimirCamino(preds,origen,dest, del);
         System.out.println ();
     // }
   }
   
   private static void imprimirCamino (int[] preds, int origen, int j, ArrayList<Integer> del) {
      if (j!=origen)
         imprimirCamino (preds,origen,preds[j], del);
      del.add(j);
   }
    
    public ArrayList<int[]> camino (int[] u,int [][] c, int s, int e){
        int anterior=0;
        ArrayList <int[]> delete = new ArrayList <>();
        System.out.println(c[s][e]);
        if ((c[s][e]==100000)||(c[s][e]==-1)){
            System.out.println("Ruta entre el vertice "+s+" y el vertice "+e+":");
            System.out.println("No alcanzable");
        }else{
            final ArrayList path = new ArrayList();
            int x = e;
            while (x != s) {
                if (x==-1 || x==100000)
                    continue;
                anterior = x;
                path.add("Vertice "+x);
                x = u[x];
                int[]aux = {x,anterior};
                delete.add(aux);
            }
        
            path.add("Vertice "+x);
            System.out.println("Ruta entre el vertice "+s+" y el vertice "+e+":");
            System.out.println(path);
        }
        return delete;
    }
    
    public int minimo(int n,boolean [] vis, int [] m ){
        int mx = 100;
        int v = -1;
        for (int j=0;j<n; j++){
            if (!(vis[j] )&& (m[j]<= mx)&& (m[j]>=0)){
                mx= m[j];
                v=j;
                
            }
        }
        
        return v;
    }
    
    public int [][] deleteEdges (int [][] m, ArrayList<Integer> e){
        for (int i = 0 ; i<e.size()-1; i++){
            int a1= e.get(i);
            int a2 = e.get(i+1);
            m[a1][a2]= -1;
            m[a2][a1]= -1;
        }
        return m;
    }
    
    
    
    public static void main(String[] args) {
        Instance i = new Instance ("AS-BA.R-Wax.v100e217.bb","AS-BA.R-Wax.v100e217.rpairs.10.1" );
        Solution s= new Solution(i);
<<<<<<< HEAD
        ArrayList <Integer> cam = s.Dijkstra(1, 5);
        System.out.println(cam);
=======
        ArrayList<int[]> deleteEdges = new ArrayList<>();
        int[][] matrix = i.getG().getAdjacent();
        ArrayList<Integer> del; 
        for (int j=0; j< i.getNodeMatrix().size();j++  ){
            del=s.Dijkstra(i.getNodeMatrix().get(j) [0],i.getNodeMatrix().get(j) [1], i.getG().getAdjacent());
            System.out.println(del);
            i.getG().setAdjacent(s.deleteEdges(i.getG().getAdjacent(), del));
        }
       
       /* for (int j=0; j< i.getNodeMatrix().size();j++  ){
            matrix = i.getG().copyMatrix();
            int[] rute = i.getNodeMatrix().get(j);
            int [] visit = s.Dijkstra(rute[0], matrix);
            deleteEdges=s.camino(visit,matrix, rute[0], rute[1] );
            Iterator <int []> kdb = deleteEdges.iterator();
            matrix = i.getG().getAdjacent();
           while (kdb.hasNext()){
                int [] aux = kdb.next(); 
                matrix [aux[0]][aux[1]]= -1;
                matrix [aux[1]][aux[0]]= -1;
            }
            i.getG().setAdjacent(matrix);
        }*/
        
        
>>>>>>> dev
    }
        
        
}


