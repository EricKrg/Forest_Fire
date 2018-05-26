package ForrestFire;

import java.util.*;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daphne
 */
public class ForrestFire {

    public static int SIZE;
    public FireCell[][] myGrid;
    static int isFire;
    static int time;

    public ForrestFire(int n) {
        SIZE = n;
        myGrid = new FireCell[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                myGrid[r][c] = new FireCell();
            }
        }
    }
//Baum wachsen lassen

    public void simulation(int a) {
        time = 0;
        isFire = 0;
        while (time < a) {

            if (isFire == 0) {
                time++;

                for (FireCell[] myGrid1 : myGrid) {

                    for (int c = 0; c < SIZE; c++) {
                        myGrid1[c].firetoAsh();
                    }
                }
                System.out.println("Normal" + "\n" + this);
                for (FireCell[] myGrid1 : myGrid) {

                    for (int c = 0; c < SIZE; c++) {
                        myGrid1[c].lightning();
                        isFire = 1;
                    }
                }

                for (FireCell[] myGrid1 : myGrid) {

                    for (int c = 0; c < SIZE; c++) {
                        myGrid1[c].growTree();
                    }
                }
                
            } else if (isFire == 1) {
                //Feuer brennt durch die Welt
                this.Fire();
                //isFire = 0;
            }
            //System.out.println("Normal" + "\n" + this);
        }            

    }

    //wenn der Blitz einschlägt wird Methode Fire() aufgerufen
    public void Fire() {

        LinkedList<FireCell> neighbours = new LinkedList<>();
        LinkedList<FireCell> fire_fields = new LinkedList<>();
        
        boolean nochFire = true;
        
        int zaehler = 1;
        
        while(nochFire == true) {
            
            nochFire = false;
               // The neighbouring fields of each burning field are stored in a list:
            for (int i = 0; i < SIZE -1; i++) {
                for (int j = 0; j < SIZE -1; j++) {
                    if (myGrid[i][j].getStatus() == 2) {
                        {
                            fire_fields.add(myGrid[i][j]);
                            //neighbours.add(myGrid[(i + 1) % 4][j]);
                            //neighbours.add(myGrid[(i - 1 + 4) % 4][j]);
                            //neighbours.add(myGrid[i][(j + 1) % 4]);
                            //neighbours.add(myGrid[i][(j - 1 + 4) % 4]);
                            

                            //Brand über den Rand hinaus:
                            if (j == 0 ) {//wir befinden uns in der oberen Reihe
                                //Feuer in obere Reihe springt in untere Reihe
                                neighbours.add(myGrid[i][j + SIZE - 1]);
                                if (i == 0) {
                                    // Feuer in Ecke links oben
                                    neighbours.add(myGrid[i + SIZE - 1][j]);
                                } else if (i == SIZE - 1) {
                                    // Feuer Ecke oben rechts
                                    neighbours.add(myGrid[i - SIZE +1][j]);
                                }
                            }
                            else if (j == SIZE -1) {//untere Reihe
                                // Feuer in unterer Reihe spring in obere Reihe
                                neighbours.add(myGrid[i][j - SIZE + 1]);
                                if (i == 0) {//linke untere Ecke
                                    neighbours.add(myGrid[i + SIZE - 1][j]);
                                } else if (i == SIZE - 1) { //rechte untere Ecke
                                    neighbours.add(myGrid[i - SIZE + 1][j]);
                                }

                            }
                            else if (i == 0 && (j > 0 & j < SIZE-1)) {//linker Rand auf rechten Rand
                                neighbours.add(myGrid[i + SIZE - 1][j]);

                            }
                            else if (i == SIZE -1 && (j > 0 & j < SIZE-1)) {//rechter Rand auf linken Rand
                                neighbours.add(myGrid[i - SIZE + 1][j]);
                            }
                            else {
                            neighbours.add(myGrid[(i + 1 + (SIZE-1) )%(SIZE-1) ][j]);//nach rechts
                            neighbours.add(myGrid[(i - 1 + (SIZE))%(SIZE-1) ][j]);//nach links
                            neighbours.add(myGrid[i][(j + 1 + (SIZE-1))%(SIZE-1) ]);//nach unten
                            neighbours.add(myGrid[i][(j - 1 +(SIZE) )%(SIZE-1) ]);//nach oben
                            }
                        }
                    }
                }
            }

                for (FireCell fire_fields1 : fire_fields)//Feuerzellen werden zu Asche
                {
//                    if (fire_fields1.getStatus() == 2) {//FIRE
//                        fire_fields1.setStatus(0); //ASH  if (myGrid[i][j].getStatus() == 2)
                        fire_fields1.setStatus(0);
//                    }
                }
                //else  if (fire_fields1.getStatus() == 0){
                //        fire_fields1.setStatus(0);
                //    }
                for (FireCell neighbours1 : neighbours) {
                    if (neighbours1.getStatus() == 1) {//TREE
//                        neighbours1.setStatus(2);//FIRE
                        neighbours1.setStatus(2);
                        nochFire = true;
                      
                    //} else if (neighbours1.getStatus() == 2) {//FIRE
                    //    neighbours1.setStatus(0);//ASH
                    //    myGrid[i][j].setStatus(0);
                    }
                } 
                //else if (neighbours1.getStatus() == 0){//ASH
                //    neighbours1.setStatus(0);
                //}
    
            //System.out.println(neighbours);
        System.out.println("Feuer" + "\n" + this + ", Durchgang: "+ zaehler);
            zaehler++;
            isFire = 0;
        }   

        
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE ; j++) {
                s += "[" + this.myGrid[i][j] + "]";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        ForrestFire f = new ForrestFire(9);
        f.simulation(10);
        System.out.println("Endstation: \n" + f);
        //System.out.println(f);

    }
}
