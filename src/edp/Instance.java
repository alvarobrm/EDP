/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class Instance {
    
    private File fileGraph;
    private String nameGraph, nameFile;
    private Graph g;
    private ArrayList<int []>  nodeMatrix;
    
    public Instance(String fileG, String nameFile){
        this.nameGraph= fileG;
        this.nameFile= nameFile;
        nodeMatrix = new ArrayList<>();
        this.ReadGraph();
        this.ReadFile();
        
    }
    
    public void ReadFile() {
 
      try {
         File f = new File (nameFile);
         FileReader fr = new FileReader (f);
         BufferedReader br = new BufferedReader(fr);
 
         // Lectura del fichero
         String line;
         while((line=br.readLine())!=null){
             String[] p = line.split("\t");
             int i = Integer.parseInt(this.deleteSpaces(p[0]));
             int j = Integer.parseInt(this.deleteSpaces(p[1]));
             int [] aux ={i, j};
             nodeMatrix.add(aux);
         }
            
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }
    
    
    public void ReadGraph() {
        int [][] ad;
        try {
            fileGraph = new File(nameGraph);
            FileReader fr = new FileReader(fileGraph);
            BufferedReader br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            line = br.readLine();
            line = this.deleteSpaces(line);
            g = new Graph (Integer.parseInt(line));
            line = br.readLine();
            ad = g.getAdjacent();
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\t");
                int i = Integer.parseInt(this.deleteSpaces(p[0]));
                int j = Integer.parseInt(this.deleteSpaces(p[1]));
                ad [i][j]=1;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    
    protected String deleteSpaces(String line){
        char l;
        String line_aux="";
        for (int i =0; i< line.length();i++){
                l= line.charAt(i);
                if (l!=' ')
                    line_aux= line_aux+l;
            }
        return line_aux;
    }


    public static void main(String[] args) {
        Instance i = new Instance ("AS-BA.R-Wax.v100e217.bb","AS-BA.R-Wax.v100e217.rpairs.10.1" );
    }
    
    
   
    
}
