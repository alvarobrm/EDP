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
    private Random r ;
    
    MyRandom (){
        r = new Random(6);
    }
    
    
     public int nextInt(int n){
         return r.nextInt(n);
     }
     
    
     
}


