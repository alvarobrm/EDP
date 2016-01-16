/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edp;

import java.util.Random;

/**
 *
 * @author alvar
 */
public class MyRandom {
    private static Random r = new Random(6);
    
    
    
     public static int nextInt(int n){
         return r.nextInt(n);
     }
     
    
     
}


