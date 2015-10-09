//package Tests;

import java.util.List;
import java.util.ArrayList;
//import Tests.ProbabilityEngine;

public class main {

    static ProbabilityEngine pe;

    public static void main(String[] args) {
        
        
        ArrayList<Integer> p = new ArrayList<Integer>();
        
        p.add(1);
        p.add(2);
        p.add(3);
        p.add(4);
        p.add(5);
        p.add(6);
        
        int N = 300;
        pe = new ProbabilityEngine(p);
        
        ArrayList<Double> count = new ArrayList<Double>();
//         double base = 0;
//         for (int i = 0; i < p.size(); i++){
//            count.add(0.0);
//            base = base + (double)(p.get(i));
//         }
        
//         System.err.println("Drawing numbers " + N + " numbers");
//         for (int i = 0; i < N; i++){
//           //System.err.print(pe.nextInt() +  " ");
//           int id = pe.nextInt();
//           count.set(id,(count.get(id)+1.0));
//         }
//         
//         for (int i = 0; i < p.size(); i++){
//            count.set(i,(count.get(i)/(double)(N)));
//         }
//         
//         double e = 0.0;
//         for (int i = 0; i < p.size(); i++){
//            e = e + (count.get(i) - (double)(p.get(i))/base)*(count.get(i) - (double)(p.get(i))/base);
//         }
//         
//         System.err.printf("Probability RMS error is %1.5f\n",e);
        
    }

}