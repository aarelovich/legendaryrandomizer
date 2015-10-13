package com.legendaryrandomizer;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LegendaryRandomizer extends Activity {

    public static class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTypeface(newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            paint.setTypeface(newType);
        }

    }

    public static class CustomAdapter extends ArrayAdapter<String> {

        private int fontsize;
        private int color;
        private Typeface typeface;
        private int bgcolor = Color.rgb(50, 50, 50);

        public CustomAdapter(Context context, int resource, Typeface tf, int colour, int fsize) {
            super(context, resource);
            fontsize = fsize;
            color = colour;
            typeface = tf;
        }

        public void setBackgroundColor(int bg){
            bgcolor = bg;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView)super.getView(position, convertView, parent);
            v.setTypeface(typeface);
            v.setTextSize(fontsize);
            v.setTextColor(color);
            v.setBackgroundColor(bgcolor);
            return v;
        }


        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
            TextView v = (TextView)super.getDropDownView(position, convertView, parent);
            v.setTypeface(typeface);
            v.setTextSize(fontsize);
            v.setTextColor(color);
            v.setBackgroundColor(bgcolor);
            return v;
        }

    }

    Spinner nplayers;
    Button components;
    Button choose;
    TextView results;

    Typeface tfnormal;
    Typeface tfvillain;
    Typeface tfhero;
    Typeface tfspinner;
    Typeface tfbutton;

    List<String> heropool = new ArrayList<String>();
    List<String> vgpool = new ArrayList<String>();
    List<String> mmpool = new ArrayList<String>();
    List<String> schpool = new ArrayList<String>();
    List<String> hmpool = new ArrayList<String>();

    public static final int color_bg = Color.rgb(21, 25, 45);
    public static final int color_text = Color.rgb(57, 66, 60);
    public static final int color_hero = Color.rgb(177, 0, 0);
    public static final int color_villain = Color.rgb(213, 8, 196);
    public static final int color_stext = Color.rgb(255, 255, 255);
    public static final int color_sbk = Color.rgb(38, 46, 83);

    public static final int vfontsize = 58;
    public static final int nfontsize = 48;
    public static final int hfontsize = 58;
    public static final int sfontsize = 25;
    public static final int bfontsize = 22;

    private String Mastermind;
    private String Scheme;
    private List<String> vdeck = new ArrayList<String>();
    private List<String> hdeck = new ArrayList<String>();

    // Flag for debugging
    private static final boolean DEBUG = false;

    // Deck ids
    private static final int DECK_HERO = 0;
    private static final int DECK_VILLAIN = 1;

    // Probability engine for selecting random cards.
    private ProbabilitySet probEngine;

    //========================= SETUP =========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tfnormal = Typeface.createFromAsset(getAssets(), "normal.ttf");
        tfvillain = Typeface.createFromAsset(getAssets(), "villains.ttf");
        tfhero = Typeface.createFromAsset(getAssets(), "heros.ttf");
        tfspinner = Typeface.createFromAsset(getAssets(), "spinnerfont.ttf");
        tfbutton = Typeface.createFromAsset(getAssets(), "buttonfont.ttf");

        LinearLayout global  = new LinearLayout(this);
        global.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        global.setOrientation(LinearLayout.VERTICAL);

        LinearLayout nchooser  = new LinearLayout(this);
        nchooser.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        nchooser.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout adder  = new LinearLayout(this);
        adder.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        adder.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout changer  = new LinearLayout(this);
        changer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        changer.setOrientation(LinearLayout.HORIZONTAL);

        global.setBackgroundColor(color_bg);

        // Creating the Title
        TextView title = new TextView(this);
        title.setTypeface(tfnormal);
        title.setText("Legendary Randomizer");
        title.setTextColor(Color.RED);
        title.setBackgroundColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(sfontsize);

        nplayers = new Spinner(this);
        CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_spinner_dropdown_item, tfspinner, color_stext, sfontsize);
        adapter.addAll(Arrays.asList("Players", "1", "2", "3", "4", "5"));
        adapter.setBackgroundColor(color_sbk);
        nplayers.setAdapter(adapter);
        nplayers.setLayoutParams(new LinearLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT));

        components = new Button(this);
        components.setTypeface(tfbutton);
        components.setTextSize(bfontsize);
        components.setText("Config");
        components.setLayoutParams(new LinearLayout.LayoutParams(330, LayoutParams.WRAP_CONTENT));
        components.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                configDialog();
            }
        });

        choose = new Button(this);
        choose.setTypeface(tfbutton);
        choose.setTextSize(bfontsize);
        choose.setText("Select");
        choose.setLayoutParams(new LinearLayout.LayoutParams(330, LayoutParams.WRAP_CONTENT));
        choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionPressed();
            }
        });
        nchooser.addView(nplayers);
        nchooser.addView(components);
        nchooser.addView(choose);

        Button tester = new Button(this);
        if (DEBUG) {
            tester.setTypeface(tfbutton);
            tester.setTextSize(bfontsize);
            tester.setText("Run Tests");
            tester.setLayoutParams(new LinearLayout.LayoutParams(330, LayoutParams.WRAP_CONTENT));
            tester.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    RunTests();
                }
            });
        }


        results = new TextView(this);
        results.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1200));
        results.setTextSize(16);
        results.setMaxHeight(1200);
        results.setMovementMethod(new ScrollingMovementMethod());
        results.setBackgroundColor(Color.rgb(179, 196, 195));
        results.setText("Select the number of players\nThen click SELECT");
        results.setGravity(Gravity.CENTER);

        global.addView(title);
        global.addView(nchooser);
        global.addView(results);

        if (DEBUG){
            global.addView(tester);
        }

        setContentView(global);

        // Expansions to use
        for (int i = 0; i < Aux.checkedExpansions.length; i++){
            Aux.checkedExpansions[i] = true;
        }

        // Making sure the directory exists.
        createWorkDir();

        // Probability engine
        probEngine = new ProbabilitySet();
        probEngine.setSaveFile(Aux.WORKDIR+"/"+Aux.STATFILE);
        probEngine.initSet();

    }

    //========================= CONFIG DIALOG & FILE SETUP =========================

    // Configuration dialog that shows all expansions, to select which to include.
    public void configDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check included expansions");

        builder.setMultiChoiceItems((CharSequence[]) Aux.Expansions.toArray(), Aux.checkedExpansions ,new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Aux.checkedExpansions[which] = isChecked;
            }
        });

        builder.setNeutralButton("Done",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                poolChanged();
            }
        });

        AlertDialog diag = builder.create();
        diag.show();
    }

    public void createWorkDir(){
        // Setting up the working directory
        Aux.WORKDIR = Environment.getExternalStorageDirectory().getPath() + "/" + Aux.WORKDIR;

        // Making sure the directory exists
        File f = new File(Aux.WORKDIR);
        if (!f.exists()) {
            // Creating the directory
            if (!f.mkdir()){
                Toast t = Toast.makeText(this,"ERROR: Could not create directory: " + Aux.WORKDIR,Toast.LENGTH_LONG);
                t.show();
                return;
            }
        }
    }

    //========================= DRAWING THE DECKS ====================

    // Changing the pool of decks to choose from
    public void poolChanged(){
        heropool.clear();
        vgpool.clear();
        mmpool.clear();
        schpool.clear();
        hmpool.clear();

        heropool.addAll(Aux.HerosBase);
        vgpool.addAll(Aux.VillainGroupBase);
        mmpool.addAll(Aux.MastermindBase);
        schpool.addAll(Aux.SchemeBase);
        hmpool.addAll(Aux.HenchmenBase);

        if (Aux.checkedExpansions[Aux.EXP_DC]){
            heropool.addAll(Aux.HerosDarkCity);
            vgpool.addAll(Aux.VillainGroupDarkCity);
            mmpool.addAll(Aux.MastermindDarkCity);
            schpool.addAll(Aux.SchemeDarkCity);
            hmpool.addAll(Aux.HenchmenDarkCity);
        }

        if (Aux.checkedExpansions[Aux.EXP_FF]){
            heropool.addAll(Aux.HerosFF);
            vgpool.addAll(Aux.VillainGroupFF);
            mmpool.addAll(Aux.MastermindFF);
            schpool.addAll(Aux.SchemeFF);
        }

        if (Aux.checkedExpansions[Aux.EXP_PTR]){
            heropool.addAll(Aux.HerosPTR);
            vgpool.addAll(Aux.VillainGroupPTR);
            mmpool.addAll(Aux.MastermindPTR);
            schpool.addAll(Aux.SchemePTR);
        }

        if (Aux.checkedExpansions[Aux.EXP_GG]){
            heropool.addAll(Aux.HerosGG);
            vgpool.addAll(Aux.VillainGroupGG);
            mmpool.addAll(Aux.MastermindGG);
            schpool.addAll(Aux.SchemeGG);
        }

        if (Aux.checkedExpansions[Aux.EXP_SW]){
            heropool.addAll(Aux.HerosSW);
            vgpool.addAll(Aux.VillainGroupSW);
            hmpool.addAll(Aux.HenchmenSW);
            mmpool.addAll(Aux.MastermindSW);
            schpool.addAll(Aux.SchemeSW);
        }

        // Need to load the sets of values for each of the probability sets.


    }

    // Selecting the Mastermind
    private void selMastermind() {
        if (mmpool.isEmpty()){
            Toast t = Toast.makeText(this,"No more masterminds",Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        int pos = getRandomPosition(mmpool);
        Mastermind = mmpool.get(pos);
        mmpool.remove(pos);
    }

    // Selecting the Scheme.
    private void selScheme() {
        if (schpool.isEmpty()){
            Toast t = Toast.makeText(this,"No more schemes",Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        int pos = getRandomPosition(schpool);
        Scheme = schpool.get(pos);
        schpool.remove(pos);
    }

    // Adds a group with the name "name" of cardtype to the decktoadd
    private void addGroupToDeck(int cardtype, String name, int deckToAdd){

        int pos  = -1;

        // Finding the name in the pool according to card type.
        switch (cardtype){
            case ReqContainer.CT_HENCHMEN:
                pos = hmpool.indexOf(name);
                if (pos > -1){
                    hmpool.remove(pos);
                    name = name + "-";
                }
                break;
            case ReqContainer.CT_HEROS:
                pos = heropool.indexOf(name);
                if (pos > -1){
                    heropool.remove(pos);
                    name = name + "+";
                }
                break;
            case ReqContainer.CT_MASTERMINDS:
                pos = mmpool.indexOf(name);
                if (pos > -1){
                    mmpool.remove(pos);
                }
                break;
            case ReqContainer.CT_VILLAINS:
                pos = vgpool.indexOf(name);
                if (pos > -1){
                    vgpool.remove(pos);
                    name = name + "=";
                }
                break;
        }

        // If a card was found then it is added to the requested deck
        if (pos > -1){
            if (deckToAdd == DECK_HERO){
                hdeck.add(name);
            }
            else{
                vdeck.add(name);
            }
        }
    }

    // Adds all requirements of the scheme to the deck and removes them from the pool
    private ReqContainer.Requisites addRequirementsToDecks(ReqContainer.Requisites req){

        String[] hdeckreq = req.hdeckReq[req.nselected];
        String[] vdeckreq = req.vdeckReq[req.nselected];

        // Requirements for heros first
        for (int i = 0; i < ReqContainer.TOTAL_CT; i++){
            // If there is something to add ...
            if (!hdeckreq[i].isEmpty()){
                // First it is split into multiple strings
                String[] list = hdeckreq[i].split("\\|");
                // Then each of the part is added
                for (int j  = 0; j < list.length; j++){
                    addGroupToDeck(i,list[j],DECK_HERO);
                    req.subOneFromHDWithSelected(i);
                }
            }
        }

        // Requirements for villains second
        for (int i = 0; i < ReqContainer.TOTAL_CT; i++){
            // If there is something to add ...
            if (!vdeckreq[i].isEmpty()){
                // First it is split into multiple strings
                String[] list = vdeckreq[i].split("\\|");
                // Then each of the part is added
                for (int j  = 0; j < list.length; j++){
                    addGroupToDeck(i, list[j], DECK_VILLAIN);
                    req.subOneFromVDWithSelected(i);
                }
            }
        }

        return req;

    }

    // Adds number randomly selected groups of cardtype to the deck todeck.
    private void addRandomCardsOfType(int cardtype, int todeck, int number){

        // Checking that there is something to add
        if (number <= 0) return;

        for (int i = 0; i < number; i++) {

            int pos = -1;
            String name = "Nothing to add";
            switch (cardtype) {
                case ReqContainer.CT_HENCHMEN:
                    if (!hmpool.isEmpty()) {
                        pos = getRandomPosition(hmpool);
                        if (pos > -1) {
                            name = hmpool.get(pos);
                            name = name + "-";
                            hmpool.remove(pos);
                        }
                    }
                    break;
                case ReqContainer.CT_HEROS:
                    if (!heropool.isEmpty()) {
                        pos = getRandomPosition(heropool);
                        if (pos > -1) {
                            name = heropool.get(pos);
                            name = name + "+";
                            heropool.remove(pos);
                        }
                    }
                    break;
                case ReqContainer.CT_MASTERMINDS:
                    if (!mmpool.isEmpty()) {
                        pos = getRandomPosition(mmpool);
                        if (pos > -1) {
                            name = mmpool.get(pos);
                            mmpool.remove(pos);
                        }
                    }
                    break;
                case ReqContainer.CT_VILLAINS:
                    if (!vgpool.isEmpty()) {
                        pos = getRandomPosition(vgpool);
                        if (pos > -1) {
                            name = vgpool.get(pos);
                            name = name + "=";
                            vgpool.remove(pos);
                        }
                    }
                    break;
            }

            // Adding to the deck if there is something to add.
            if (pos > -1) {
                if (todeck == DECK_HERO) {
                    hdeck.add(name);
                } else {
                    vdeck.add(name);
                }
            }

        }

    }

    // Filling the decks according to requirements and random selections.
    public void selectionPressed(){
        //Check that a number of players is selected.
        int n = nplayers.getSelectedItemPosition();
        if (n > 0){

            poolChanged(); //To reset the pool of choices.

            // To make sure the values are reset.
            ReqContainer.InitRequirements();

            // Clearing all decks
            hdeck.clear();
            vdeck.clear();

            // First the scheme is selected, and the requirements are added.
            selScheme();

            //Scheme = "Transform Citizens into Demons";

            ReqContainer.Requisites req = new ReqContainer.Requisites();
            req = ReqContainer.Reqs.get(Scheme);

            req.nselected = n-1; // This is because 1 player is index 0.

            // Adding the requirements to their deck.
            req = addRequirementsToDecks(req);

            // Selecting the mastermind
            selMastermind();

            // The always leads ability is ignored in solo play
            if ((n-1) > 0){

                ReqContainer.MMFollowers al = ReqContainer.AllwaysLeads.get(Mastermind);
                addGroupToDeck(al.type,al.name,DECK_VILLAIN);
                req.subOneFromVDWithSelected(al.type);
            }

            // All random groups are drawn.
            for (int i = 0; i < ReqContainer.NAMED_CARDTYPES.size(); i++){

                int cardtype = ReqContainer.NAMED_CARDTYPES.get(i);

                // For Hero Deck
                addRandomCardsOfType(cardtype, DECK_HERO, req.getRandomNeededForHDWithSelected(cardtype));

                // For Villain Deck
                addRandomCardsOfType(cardtype,DECK_VILLAIN,req.getRandomNeededForVDWithSelected(cardtype));
            }

            // Now the results are shown.
            SpannableStringBuilder ssb = new SpannableStringBuilder("");
            results.setText(""); //Clearing any previous text
            results.setGravity(Gravity.NO_GRAVITY);

            addText(ssb, "\n ========== Legendary Setup ==========\n", color_text, nfontsize, tfnormal);
            addText(ssb, "Scheme: ", color_text, nfontsize, tfnormal);
            addText(ssb, Scheme + "\n", color_villain, vfontsize, tfvillain);
            if (!req.SpecialInst.get(req.nselected).isEmpty()) {
                addText(ssb, "Special Instructions: " + req.SpecialInst.get(req.nselected) + "\n", color_text, nfontsize, tfnormal);
            }
            addText(ssb, "Mastermind: ", color_text, nfontsize, tfnormal);
            addText(ssb,Mastermind + "\n",color_villain,vfontsize,tfvillain);

            // Contents of Villian Deck
            addText(ssb, "=== Villian Deck Contents === \n", color_text, nfontsize, tfnormal);
            for (int i = 0; i < vdeck.size(); i++){
                addText(ssb,"-> " + vdeck.get(i) + "\n",color_villain,vfontsize,tfvillain);
            }

            int[] others = req.vdeck[req.nselected];
            for (int i = 0; i < ReqContainer.NUMBER_CARDTYPES.size(); i++){
                int value = others[ReqContainer.NUMBER_CARDTYPES.get(i)];
                if (value > 0){
                    addText(ssb, "-> " + ReqContainer.NUMBER_CARDTYPES_NAMES.get(i) + ": " + Integer.toString(value) + "\n", color_text, nfontsize, tfnormal);
                }
            }

            // Contents of Hero Deck
            addText(ssb, "=== Hero Deck Contents === \n", color_text, nfontsize, tfnormal);
            for (int i = 0; i < hdeck.size(); i++){
                addText(ssb,"-> " + hdeck.get(i) + "\n", color_hero, hfontsize, tfhero);
            }

            others = req.hdeck[req.nselected];
            for (int i = 0; i < ReqContainer.NUMBER_CARDTYPES.size(); i++){
                int value = others[ReqContainer.NUMBER_CARDTYPES.get(i)];
                if (value > 0){
                    addText(ssb, "-> " + ReqContainer.NUMBER_CARDTYPES_NAMES.get(i) + ": " + Integer.toString(value) + "\n", color_text, nfontsize, tfnormal);
                }
            }

            // Outputting the results
            results.setText(ssb, TextView.BufferType.SPANNABLE);

        }
        else{
            Toast toast = Toast.makeText(this, "No number of players selected", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //========================= AUX FUNCTIONS =========================

    // Function that adds colored and font changed text to the spannable string builder
    public void addText(SpannableStringBuilder text, String str, int color, int size, Typeface font){
        int start = text.toString().length();
        int end = start + str.length();
        text.append(str);
        text.setSpan(new CustomTypefaceSpan("Font", font), start, end, 0);
        text.setSpan(new AbsoluteSizeSpan(size), start, end, 0);
        text.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }

    public int getRandomPosition(List<String> list){

        // Setting the list as the subset
        probEngine.setSubset(list);

        // Getting the random id.
        int id = probEngine.getRandomId();

        // Saving the new data
        probEngine.saveSet();

        return id;

    }

    // Functions to run random diagnostics
    public void RunTests(){

        int N = 40;

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < N; i++){
            names.add("N"+Integer.toString(i+1));
        }


        ArrayList<String> found = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        int count = 0;
        boolean done = false;
        int next = 0;

        while (!done){
            next = random.nextInt(N);
            count++;
            if (found.indexOf(names.get(next)) == -1){
                found.add(names.get(next));
                if (found.size() == names.size()){
                    done = true;
                }
            }
        }

        System.err.println("Normal random took " + count  + " tries to get all " + N + " values");

        // Now with the probability engine

        ProbabilitySet ps = new ProbabilitySet();

        for (int i = 0; i < names.size(); i++){
            ps.addPair(names.get(i),20);
        }

        ps.setSubset(names);

        count = 0;
        done = false;
        found.clear();

        while (!done){
            next = ps.getRandomId();
            count++;

            //System.err.println("Getting " + next);

            if (found.indexOf(names.get(next)) == -1){
                found.add(names.get(next));
                if (found.size() == names.size()){
                    done = true;
                }
            }
        }

        System.err.println("PE took " + count  + " tries to get all " + N + " values");

    }


}
