package com.legendaryrandomizer;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aarelovich on 8/30/15.
 */
public class ReqContainer {

    // All card types than can be on Hero Or Villain Deck
    public static final int CT_HEROS = 0;
    public static final int CT_VILLAINS = 1;
    public static final int CT_HENCHMEN = 2;
    public static final int CT_SIDEKICKS = 3;
    public static final int CT_BYSTADERS = 4;
    public static final int CT_WOUNDS = 5;
    public static final int CT_MASTERMINDS = 6;
    public static final int CT_TWISTS = 7;
    public static final int CT_MST = 8;
    public static final int TOTAL_CT = 9;

    // List of card types that might need to be randomized, becasue they are named groups and are not all the same.
    public static List<Integer> NAMED_CARDTYPES = Arrays.asList(CT_HENCHMEN,CT_HEROS,CT_MASTERMINDS,CT_VILLAINS);

    // List of card types that will only be specified in numbers
    public static List<Integer> NUMBER_CARDTYPES = Arrays.asList(CT_SIDEKICKS,CT_BYSTADERS,CT_WOUNDS,CT_TWISTS,CT_MST);
    public static List<String> NUMBER_CARDTYPES_NAMES = Arrays.asList("Sidekicks","Bystanders","Wounds","Scheme Twists","Master Strikes");


    public static final int MAXPLAYERS = 5;

    // Class for grouping the requirements
    public static class Requisites {

        public int hdeck[][] = new int[MAXPLAYERS][TOTAL_CT];
        public int vdeck[][] = new int[MAXPLAYERS][TOTAL_CT];
        public String hdeckReq[][] = new String[MAXPLAYERS][TOTAL_CT];
        public String vdeckReq[][] = new String[MAXPLAYERS][TOTAL_CT];
        public List<String> SpecialInst;
        public int nselected = 0;

        public Requisites(){

            // All none initialized values should be zero.

            // 1 Player default requisites
            hdeck[0][CT_HEROS] = 3;

            vdeck[0][CT_VILLAINS] = 1;
            vdeck[0][CT_HENCHMEN] = 1;
            vdeck[0][CT_BYSTADERS] = 1;
            vdeck[0][CT_MST] = 5;

            // 2 Player default requisites
            hdeck[1][CT_HEROS] = 5;

            vdeck[1][CT_VILLAINS] = 2;
            vdeck[1][CT_HENCHMEN] = 1;
            vdeck[1][CT_BYSTADERS] = 2;
            vdeck[1][CT_MST] = 5;

            // 3 Player default requisites
            hdeck[2][CT_HEROS] = 5;

            vdeck[2][CT_VILLAINS] = 3;
            vdeck[2][CT_HENCHMEN] = 1;
            vdeck[2][CT_BYSTADERS] = 8;
            vdeck[2][CT_MST] = 5;

            // 4 Player default requisites
            hdeck[3][CT_HEROS] = 5;

            vdeck[3][CT_VILLAINS] = 3;
            vdeck[3][CT_HENCHMEN] = 2;
            vdeck[3][CT_BYSTADERS] = 8;
            vdeck[3][CT_MST] = 5;

            // 4 Player default requisites
            hdeck[4][CT_HEROS] = 6;

            vdeck[4][CT_VILLAINS] = 4;
            vdeck[4][CT_HENCHMEN] = 2;
            vdeck[4][CT_BYSTADERS] = 12;
            vdeck[4][CT_MST] = 5;

            // Initializing the string lists
            SpecialInst = new ArrayList<String>();

            for (int i = 0;  i < MAXPLAYERS; i++){
                SpecialInst.add("");
                for (int j = 0; j < TOTAL_CT; j++){
                    vdeckReq[i][j] = "";
                    hdeckReq[i][j] = "";
                }
            }

            SpecialInst.set(0,"Only 3 Henchmen cards");

        }

        public int getRandomNeededForHDWithSelected(int cardtype){
            return Math.max(0,hdeck[nselected][cardtype]);
        }

        public int getRandomNeededForVDWithSelected(int cardtype){
            return Math.max(0,vdeck[nselected][cardtype]);
        }

        public void subOneFromHDWithSelected(int cardtype){
            hdeck[nselected][cardtype] = hdeck[nselected][cardtype] - 1;
        }

        public void subOneFromVDWithSelected(int cardtype){
            vdeck[nselected][cardtype] = vdeck[nselected][cardtype] - 1;
        }

        public void setVDName(int nplayers, int cardtype, String value){
            vdeckReq[nplayers][cardtype] = value;
        }

        public void setHDName(int nplayers, int cardtype, String value){
            hdeckReq[nplayers][cardtype] = value;
        }

        public void setVDNameForAll(int cardtype,String value){
            for (int i = 0; i < MAXPLAYERS; i++){
                setVDName(i, cardtype, value);
            }
        }

        public void setHDNameForAll(int cardtype, String value){
            for (int i = 0; i < MAXPLAYERS; i++){
                setHDName(i, cardtype, value);
            }
        }

        public void setVDForAll(int cardtype,int value){
            for (int i = 0; i < MAXPLAYERS; i++){
                setVD(i, cardtype, value);
            }
        }

        public void setHDForAll(int cardtype,int value){
            for (int i = 0; i < MAXPLAYERS; i++){
                setHD(i, cardtype, value);
            }
        }

        public void setVD(int nplayers, int cardtype, int value){
            vdeck[nplayers][cardtype] = value;
        }

        public void setHD(int nplayers, int cardtype, int value){
            hdeck[nplayers][cardtype] = value;
        }

        public void addSpecialInstructionForAll(String inst){
            for (int i = 0; i < MAXPLAYERS; i++){
                addSpecialInstruction(i,inst);
            }
        }

        public void addSpecialInstruction(int nplayers, String inst){
            SpecialInst.set(nplayers,SpecialInst.get(nplayers) + "\n" + inst);
        }

        public void addVDExtraForAll(int cardtype){
            for (int i = 0; i < MAXPLAYERS; i++){
                addVDExtra(i,cardtype);
            }
        }

        public void addVDExtra(int nplayers, int cardtype){
            vdeck[nplayers][cardtype] = vdeck[nplayers][cardtype] + 1;
        }


    }

    public static class MMFollowers{
        public int type = CT_VILLAINS;
        public String name = "";
    }

    public static Map<String,Requisites> Reqs;

    public static Map<String,MMFollowers> AllwaysLeads;

    public static void InitRequirements(){

        // Map initialization
        Reqs = new HashMap<String,Requisites>();
        AllwaysLeads = new HashMap<String,MMFollowers>();

        // The requisite and AllwaysLeads variable
        Requisites req = new Requisites();
        MMFollowers follows = new MMFollowers();

        // ================== Adding the Scheme Requisites ==============

        // Base
        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addSpecialInstructionForAll("Wound stack has 6 wounds per player");
        Reqs.put("The Legacy Virus", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_BYSTADERS, 12);
        Reqs.put("Midtown Bank Robbery", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addVDExtraForAll(CT_HENCHMEN);
        Reqs.put("Negative Zone Prison Breakout", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 7);
        Reqs.put("Portals to the Dark Dimension", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_BYSTADERS, 18);
        req.addSpecialInstructionForAll("3 Additional Twists next to scheme");
        Reqs.put("Replace Earth's Leaders with Killbots", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setHDForAll(CT_HEROS, 6);
        req.addSpecialInstructionForAll("12 random cards from the Hero Deck to the Villain Deck");
        req.setVDNameForAll(CT_VILLAINS, "Skrulls");
        Reqs.put("Secret Invasion of the Skrull Shapeshifters", req);

        req = new Requisites();
        req.setVD(0, CT_TWISTS, 8);
        req.setVD(1, CT_TWISTS, 8);
        req.setVD(2, CT_TWISTS, 8);
        req.setVD(3, CT_TWISTS, 5);
        req.setVD(4, CT_TWISTS, 5);
        req.setHD(0, CT_HEROS, 4);
        req.setHD(1, CT_HEROS, 4);
        req.setHDForAll(CT_HEROS, 6);
        Reqs.put("Super Hero Civil War", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("Unleash the Power of the Cosmic Cube", req);

        // Dark city expansion

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addSpecialInstructionForAll("Use token in scheme to represent Baby Hope");
        Reqs.put("Capture Baby Hope", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setHDForAll(CT_HEROS, 6);
        Reqs.put("Detonate the Helicarrier", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("Massive Earthquake Generator", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDNameForAll(CT_HENCHMEN, "Maggia Goons");
        Reqs.put("Organized Crimewave", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setHDForAll(CT_BYSTADERS, 24);
        req.setHD(0, CT_BYSTADERS, 12);
        Reqs.put("Save Humanity", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addVDExtraForAll(CT_VILLAINS);
        Reqs.put("Steal the Weaponized Plutonium", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_BYSTADERS, 0);
        req.setVDForAll(CT_HEROS, 1);
        req.setVDNameForAll(CT_HEROS, "Jean Grey");
        Reqs.put("Transform Citizens into Demons", req);


        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_BYSTADERS, 0);
        req.setVDForAll(CT_HEROS, 1);
        Reqs.put("X-Cutioner's Song", req);

        // Fantastic Four expansion

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 6);
        Reqs.put("Bathe Earth in Cosmic Rays", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("Flood the Planet with Melted Glaciers", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 7);
        Reqs.put("Invincible Force Field", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("Pull Reality into the Negative Zone", req);

        // Paint the town red expansion

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setHDForAll(CT_HENCHMEN, 1);
        req.addSpecialInstructionForAll("Only 6 Henchmen go into the Hero Deck");
        Reqs.put("Invade the Daily Bugle", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDNameForAll(CT_VILLAINS, "Sinister Six");
        Reqs.put("Splice Humans with Spider DNA", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("The Clone Saga", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 7);
        Reqs.put("Weave a Web of Lies", req);

        // Guardians of the Galaxy Expansion

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addSpecialInstructionForAll("Make a facedown deck of 6 Bystanders");
        Reqs.put("Intergalactic Kree Nega-Bomb", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDNameForAll(CT_VILLAINS, "Infinity Gems");
        Reqs.put("Forge the Infinity Gauntlet", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVD(0, CT_VILLAINS, 2);
        req.setVDNameForAll(CT_VILLAINS, "Skrulls|Kree Starforce");
        Reqs.put("The Kree-Skrull War", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 0);
        req.addSpecialInstructionForAll("Twists equal to the number of players + 5. 30 Shards in supply");
        Reqs.put("Unite the Shards", req);

        // Secret wars expansion
        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 9);
        req.addVDExtraForAll(CT_HENCHMEN);
        req.addSpecialInstructionForAll("One of the Henchmen groups starts in the KO pile\n and not the Villain Deck");
        Reqs.put("Build an army of annihilation", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_SIDEKICKS, 10);
        Reqs.put("Corrupt the next Generation of Heroes", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 10);
        Reqs.put("Pan-dimensional plague", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 0);
        req.addVDExtraForAll(CT_VILLAINS);
        req.addSpecialInstructionForAll("Follow instructions in Scheme");
        Reqs.put("Fragmented Realities", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.setVDForAll(CT_MASTERMINDS, 3);
        req.addSpecialInstructionForAll("Villiain Deck needs Tactics from extra Masterminds");
        Reqs.put("Master of Tyrants", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        Reqs.put("Dark Alliance", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 8);
        req.addVDExtraForAll(CT_VILLAINS);
        Reqs.put("Smash two dimensions together", req);

        req = new Requisites();
        req.setVDForAll(CT_TWISTS, 5);
        req.addVDExtra(0, CT_VILLAINS);
        Reqs.put("Crush them with my bare hands", req);

        // ================== Allways Leads ==============

        // Secret Wars Volume 1 Expansion
        follows = new MMFollowers();
        follows.name = "Limbo";
        AllwaysLeads.put("Madelyne Pryor, Goblin Queen",follows);

        follows = new MMFollowers();
        follows.name = "Wasteland";
        AllwaysLeads.put("Wasteland Hulk",follows);

        follows = new MMFollowers();
        follows.name = "The Deadlands";
        AllwaysLeads.put("Zombie Green Goblin",follows);

        follows = new MMFollowers();
        follows.name = "Sentinel Terrorists";
        AllwaysLeads.put("Nimrod, Super Sentinel",follows);

        // Dark City Expansion
        follows = new MMFollowers();
        follows.name = "Marauders";
        AllwaysLeads.put("Mr. Sinister",follows);

        follows = new MMFollowers();
        follows.name = "MLF";
        AllwaysLeads.put("Stryfe",follows);

        follows = new MMFollowers();
        follows.name = "Underworld";
        AllwaysLeads.put("Mephisto",follows);

        follows = new MMFollowers();
        follows.name = "Streets of New York";
        AllwaysLeads.put("Kingpin",follows);

        follows = new MMFollowers();
        follows.name = "Four Horsemen";
        AllwaysLeads.put("Apocalypse",follows);

        // Base Game
        follows = new MMFollowers();
        follows.name = "Brotherhood";
        AllwaysLeads.put("Magneto",follows);

        follows = new MMFollowers();
        follows.name = "Enemies of Asgard";
        AllwaysLeads.put("Loki",follows);

        follows = new MMFollowers();
        follows.name = "Hydra";
        AllwaysLeads.put("Red Skull",follows);

        follows = new MMFollowers();
        follows.name = "Doombot Legion";
        follows.type = CT_HENCHMEN; // So far the only mastermind that leads henchmen.
        AllwaysLeads.put("Dr. Doom",follows);

        // Fantastic Four Expansion
        follows = new MMFollowers();
        follows.name = "Herald of Galactus";
        AllwaysLeads.put("Galactus",follows);

        follows = new MMFollowers();
        follows.name = "Subterranean";
        AllwaysLeads.put("MoleMan",follows);

        // Paint the town red
        follows = new MMFollowers();
        follows.name = "Maximum Carnage";
        AllwaysLeads.put("Carnage",follows);

        follows = new MMFollowers();
        follows.name = "Sinister Six";
        AllwaysLeads.put("Mysterio",follows);

        // Guardians of the Galaxy Expansion
        follows = new MMFollowers();
        follows.name = "Infinity Gems";
        AllwaysLeads.put("Thanos",follows);

        follows = new MMFollowers();
        follows.name = "Kree Starforce";
        AllwaysLeads.put("Supreme Intelligence",follows);



    }


}
