/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forest_Fire;

import java.util.TreeMap;
import java.lang.*;

/**
 *
 * @author Eric
 */
//Alle Zustände der 2D Welt definieren 
public class Zell_Zustand {

    public static final int ASH = 0, TREE = 1, FIRE = 2, SOAKED = 3, LAVA = 4;
    int status;

    public Zell_Zustand() {
        status = ASH;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int n) {
        status = n;
    }

    // growTree wird nur ausgeführt, wenn gerade kein Brand im Gange ist
    public void growTree() {
        if (status != FIRE) {
            if (Math.random() <= 0.75) {
                status = TREE;
            }

        }
    }

    public void blitzEinschlag() {
        if (status == TREE) {

            if (Math.random() <= 0.01) {
                status = FIRE;
            }

        }
    }

    public void AscheZuAsche() {
        if (status == FIRE) {
            status = ASH;
        }
    }

    public void regen() {
        if (status != SOAKED) {
            if (Math.random() <= Area.regenWahrs) {
                status = SOAKED;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";

        if (this.getStatus() == 0) {
            return " ";
        } else if (this.getStatus() == 1) {
            return "♣";
        } else if (this.getStatus() == 2) {
            return "☼";
        } else {
            return "▒";
        }
    }

}
