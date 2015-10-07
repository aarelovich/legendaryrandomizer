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
        
        System.err.println("Drawing numbers " + N + " numbers");
        for (int i = 0; i < N; i++){
          System.err.print(pe.getRandomNumber() +  " ");        
        }
        System.err.println("");
        
    }

}