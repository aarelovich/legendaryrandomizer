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
   private int MaxIntValue;

   public ProbabilityEngine(ArrayList<Integer> probs){
   
       p = probs;
       
       // Getting maximum random value.
       SumBase = 0;
       for (int i = 0; i < p.size(); i++){
           System.err.println("P(" + i + ")= " + p.get(i));
           SumBase = SumBase + p.get(i);
       }
       
       // Initializing random number generator.
       random = new SecureRandom();
       
       setMaxInt();
   
   }
   
   // This function finds a multiplier for the probability.
   private void setMaxInt(){
   
      // Calculating the probability for each number
      ArrayList<Double> prob = new ArrayList<Double>();
      
      for (int i = 0; i < p.size(); i++){
         prob.add(((double)p.get(i))/((double)SumBase));
      }
      
      // Calculating the minimum difference
      double mindiff = 1.0;
      for (int i = 0; i < p.size(); i++){
         for (int j = i+1; j < p.size(); j++){
            mindiff = Math.min(mindiff,Math.abs(prob.get(i) - prob.get(j)));
         }
      }
      
      MaxIntValue = (int)(100.0/mindiff);
      
      System.err.println("Min diff is " + Double.toString(mindiff) + " Mutliplier is " + MaxIntValue);
      
      // Now the comparison values are calcualted.
      for (int i = 0; i < p.size(); i++){
         p.set(i,(int)(MaxIntValue*prob.get(i)));
         System.err.println("New P for " + i + " is " + p.get(i));
      }
   
   }
   
   public int getRandomNumber(){
   
      int value = random.nextInt(SumBase);
      int sum = p.get(0);
      int rng = 0;
      while(sum < value){
        rng++;
        sum = sum + p.get(rng);
      }
      return rng;
   
   }

}
