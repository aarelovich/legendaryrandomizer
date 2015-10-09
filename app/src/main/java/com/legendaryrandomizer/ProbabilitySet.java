package com.legendaryrandomizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ariel on 10/9/15.
 */
public class ProbabilitySet {

    // The set of values
    private HashMap<String,Integer> set;

    // The error code, if any
    private String status;

    // The save file
    private String saveFile;

    // The max value for the set.
    private int maxValue;

    private List<String> subset;

    // Random number generator;
    private SecureRandom random;

    // Default probability value
    private static final int DEF_P = 10;

    public ProbabilitySet(){

        // Initializing the set.
        set = new HashMap<>();

        // Initializing the status message.
        status = "";

        // The save file variable
        saveFile = "";

        maxValue = 0;

        random = new SecureRandom();
    }

    public void setSubset(List<String> tokens){

        subset = tokens;

        // Resetting max value count.
        maxValue = 0;
        for (int i =0; i < subset.size(); i++){
            if (set.containsKey(subset.get(i))) {
                maxValue = maxValue + set.get(subset.get(i));
            }
            else{
                // Never been added before
                maxValue = maxValue + DEF_P;
                set.put(subset.get(i),DEF_P);
            }
        }

    }

    // The core function. This returns an index from the list with a probability of
    // set(subset(index))/maxValue
    public int getRandomId(){

        // Making sure subset is coherent.
        if (subset == null) return -1;
        if (subset.isEmpty()) return -1;

        // Uniformely distributed value
        int value = random.nextInt(maxValue);

        // Initialization
        int compare = set.get(subset.get(0));
        int ret = 0;

        // Returning ID with weighted distribution.

        while (compare < maxValue){

            //System.err.println("Comparing " + value + " with " + compare);

            if (value < compare){
                // This is the value of ret to return.
                break;
            }
            ret = ret+1;
            compare = compare + set.get(subset.get(ret));
        }

        // Increasing the corresponding
        adjustProbabilities(subset.get(ret));
        return ret;
    }

    // Adjust the probabilities of the recently chosen index
    private void adjustProbabilities(String item){
        if (set.get(item) > 1){

            // Decreasing the probability for next time
            set.put(item,set.get(item)-1);

            // Since max value is the sum of all values, this is the way to adjust maxValue
            maxValue = maxValue-1;

            // If all values are one then maxvalue is the same as subset size and a reset is needed.

            if (maxValue == subset.size()) {
                System.err.println("Resetting the system!!!!!");
                maxValue = 0;
                for (int i = 0; i < subset.size(); i++) {
                    set.put(subset.get(i), DEF_P);
                    maxValue = maxValue + DEF_P;
                }
            }
        }
    }

    public void addPair(String name, int value){
        set.put(name, value);
    }

    private void updateMax(){
        maxValue = 0;
        for (HashMap.Entry<String, Integer> entry : set.entrySet()){
           maxValue = maxValue + entry.getValue();
        }
    }

    public void setSaveFile(String file){
        saveFile = file;
    }

    public String getStatus(){return status;}

    // Loading the file with the data.
    public void initSet(){

        if (saveFile.isEmpty()) return;

        // The data is loaded.
        try{
            FileReader reader = new FileReader(saveFile);

            BufferedReader buffer = new BufferedReader(reader);

            String line = null;

            while ((line = buffer.readLine()) != null){
                String[] parts = line.split("\\|");
                set.put(parts[0],Integer.valueOf(parts[1]));
            }

            buffer.close();

        }
        catch (Exception e){
            status = "Error loading file: " + e.toString();
        }

    }
    
    // Saving the set.
    public void saveSet(){

        if (saveFile.isEmpty()) return;

        File f = new File(saveFile);

        // The data is loaded.
        try{
            FileWriter writer = new FileWriter(saveFile);

            BufferedWriter buffer = new BufferedWriter(writer);

            for (HashMap.Entry<String, Integer> entry : set.entrySet()){
                String toprint = entry.getKey() + "|" + entry.getValue().toString() + "\n";
                writer.write(toprint);
            }

            buffer.close();

        }
        catch (Exception e){
            status = "Error loading file: " + e.toString();
        }

    }



}
