//package Tests;


// Generate random numbers with a certain probability.

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

public class ProbabilityEngine {

   // Proability of drawing the index i is p(i).
   private List<Integer> p;
   private SecureRandom random;
   private int MaxRandomValue;

   public ProbabilityEngine(ArrayList<Integer> probs){
   
       p = probs;
       
       // Getting maximum random value.
       MaxRandomValue = 0;
       for (int i = 0; i < p.size(); i++){
           System.err.println("P(" + i + ")= " + p.get(i));
           MaxRandomValue = MaxRandomValue + p.get(i);
       }
       
       // Initializing random number generator.
       random = new SecureRandom();
   
   }
   
   public int getRandomNumber(){
   
      int value = random.nextInt(MaxRandomValue);
      int sum = p.get(0);
      int rng = 0;
      while(sum < value){
        rng++;
        sum = sum + p.get(rng);
      }
      return rng;
   
   }

}
