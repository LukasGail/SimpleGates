package com.github.lukasgail.saveAndLoad;

import com.github.lukasgail.simplegates.GateBlock;
import com.github.lukasgail.simplegates.SimpleGates;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
    public HashMap<Integer, GateBlock[]> data;

    public void save(Object o, File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(o);
            oos.flush();
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object load(File f){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object result = ois.readObject();
            ois.close();
            return result;

        } catch (Exception e){
            return null;
        }
    }

    public void loadExistingGatesToGame(){

    }

    public void reorderGateNumbers(){
        ArrayList<GateBlock[]> gates = new ArrayList<>();

        for(GateBlock[] gateArray : data.values()){
            gates.add(gateArray);
        }
        data.clear();
        for (int i = 1; i <= gates.size(); i++){

            data.put(i, gates.get(i-1));
        }
    }



}
