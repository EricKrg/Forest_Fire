/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forest_Fire;

import java.io.*;
import java.util.HashMap;

/**
 *
 * @author Eric
 */
public class Auswertung extends Area {

    public Auswertung(int i) {
        super(i);
    }

    public String statistikAuswertung() {
        String s = "";
        for (HashMap.Entry<Integer, Integer> paar : feuerZahlen.entrySet()) {
            int value = paar.getValue();
            int key = paar.getKey();
            s += String.valueOf(key) + ";" + String.valueOf(value) + "\n";  // s += sonst überschreibt es alles...
        }
        return s; 
    }

    public void write(String Name) throws IOException {
        FileWriter fw = new FileWriter("ausgabe.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(statistikAuswertung());
        bw.close();

    }
}
