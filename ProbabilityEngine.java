//package Tests;


// Generate random numbers with a certain probability.

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

public class ProbabilityEngine {

   // Proability of drawing the index i is p(i).
   private List<Integer> p;
   private SecureRandom random;
   private int SumBase;

   public ProbabilityEngine(ArrayList<Integer> probs){
   
       p = new ArrayList<Integer>();
       
       // Getting maximum random value.
       SumBase = 0;
       for (int i = 0; i < probs.size(); i++){
           int pval = probs.get(i);
           p.add(pval);
           System.err.println("P(" + i + ")= " + pval);
           SumBase = SumBase + pval;
       }
       
       // Initializing random number generator.
       random = new SecureRandom();
   }
   
   // Draws a random number according to the distribution
   public int nextInt(){
   
      // Uniformely distributed value
      int value = random.nextInt(SumBase);
      
      // Initialization
      int compare = p.get(0);
      int ret = 0;
      
      // Returning ID with weighted distribution.
      while (compare < SumBase){
        
        if (value < compare){
           return ret;
        }
        ret = ret+1;
        compare = compare + p.get(ret);
      }
      
      return ret;
      
   }

}
