//package Tests;


// Generate random numbers with a certain probability.

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

public class ProbabilityEngine {

   // Proability of drawing the index i is p(i).
   private List<Integer> p;

   public ProbabilityEngine(ArrayList<Integer> probs){
   
       p = probs;
       
       for (int i = 0; i < p.size(); i++){
           System.err.println("P(" + i + ")= " + p.get(i));
       }
   
   }

}
