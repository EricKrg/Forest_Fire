/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forest_Fire;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Eric
 */
public class Area {

    public static float regenWahrs;
    static int tick;
    static int isfire;
    static int isRain;
    static int wolkenzaehler;
    HashMap<Integer, Integer> feuerZahlen = new HashMap<Integer, Integer>();

    Zell_Zustand[][] grid; //2D Welt

    public static int SIZE;

    public Area(int n) {

        SIZE = n;
        grid = new Zell_Zustand[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                grid[r][c] = new Zell_Zustand();
            }
        }
    }

    void Zeit_Schritt(int schritte, float p) {
        regenWahrs = p;
        while (tick < schritte) {
            if (isfire == 0) {
                tick++;
                feuerZahlen.put(tick, 0);

                //Asche
                for (Zell_Zustand[] grid1 : grid) {
                    {
                        for (int c = 0; c < SIZE; c++) {
                            grid1[c].AscheZuAsche();
                        }
                    }
                }

                // Blitzeinschlag
                for (Zell_Zustand[] grid1 : grid) {
                    {
                        for (int c = 0; c < SIZE; c++) {
                            grid1[c].blitzEinschlag();
                            isfire = 1;
                        }
                    }
                }

                //Baum wachsen
                for (Zell_Zustand[] grid1 : grid) {
                    {
                        for (int c = 0; c < SIZE; c++) {
                            grid1[c].growTree();
                        }
                    }
                }

            } else if (isfire == 1) {
                //Feuer undso, funktion fürs brennen
                this.waldBrand();
                isfire = 0;

                
            }
        }
    }

    void niederschlag() {
        LinkedList<Zell_Zustand> regenfeld = new LinkedList<Zell_Zustand>();
        LinkedList<Zell_Zustand> wolke = new LinkedList<Zell_Zustand>();
        boolean amRegnen = true;
        //isRain = 1;

        while (amRegnen == true) {
            amRegnen = false;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    if (grid[i][j].getStatus() == Zell_Zustand.SOAKED) {
                        regenfeld.add(grid[i][j]);
                        if (j != grid.length - 1 && i != grid.length - 1) {
                            wolke.add(grid[i][j + 1]);
                            wolke.add(grid[i + 1][j]);
                        } else if (i == 0) {
                            wolke.add(grid[i + 1][j]);
                        } else if (j == 0) {
                            wolke.add(grid[i][j + 1]);
                        }

                    }

                }
            }
            for (Zell_Zustand regenfeld1 : regenfeld) {
                regenfeld1.setStatus(3);
            }
            for (Zell_Zustand wolke1 : wolke) {
                wolke1.setStatus(3);
                amRegnen = false;
            }
            wolkenzaehler++; //Bewirkt das nicht alles voller Wolke ist, macht Wolkenverlauf aber diffuser
            if (wolke.size() > grid.length*10) {
                for (Zell_Zustand wolke1 : wolke) {
                    wolke1.growTree();
                }
           }
        }
    }

    void waldBrand() {

        LinkedList<Zell_Zustand> grenzfelder = new LinkedList<Zell_Zustand>();
        LinkedList<Zell_Zustand> feuerfelder = new LinkedList<Zell_Zustand>();

        //Regen wirdf erst ab 2. Runde initialisiert
        if (tick > 1) {

            grid[0][0].regen();

        }
        niederschlag();
        boolean ambrennen = true;

        while (ambrennen == true) {
            ambrennen = false;
          
            niederschlag();
            
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    if (grid[i][j].getStatus() == Zell_Zustand.FIRE) {
                        feuerfelder.add(grid[i][j]);
                        
                        if (j == 0) {
                            grenzfelder.add(grid[i][j + SIZE - 1]);
//                            grenzfelder.add(grid[i + SIZE - 1][j]);
                            grenzfelder.add(grid[i][j + 1]);
                            if (i == 0) {
                                grenzfelder.add(grid[i + SIZE - 1][j]);
                                grenzfelder.add(grid[i + 1][j]);
                                grenzfelder.add(grid[i][j + 1]);
                            } else if (i == SIZE - 1) {
                                grenzfelder.add(grid[i - SIZE + 1][j]);
                                grenzfelder.add(grid[i - 1][j]);
                                grenzfelder.add(grid[i][j + 1]);
//                            } else if (i == 1) {
//                                grenzfelder.add(grid[0][0]);
//                            } else {
//                                grenzfelder.add(grid[i - 1][j]);
//                                grenzfelder.add(grid[i + 1][j]);
//                                grenzfelder.add(grid[i][j + 1]);
                            } else {
                                grenzfelder.add(grid[i - 1][j]);
                            }
                        } else if (j == SIZE - 1) {
                            grenzfelder.add(grid[i][j - SIZE + 1]);
                            grenzfelder.add(grid[i][j - 1]);
                            if (i == 0) {
                                grenzfelder.add(grid[i + SIZE - 1][j]);
                                grenzfelder.add(grid[i + 1][j]);
                                grenzfelder.add(grid[i][j - 1]);
                            } else if (i == SIZE - 1) {
                                grenzfelder.add(grid[i - SIZE + 1][j]);
                                grenzfelder.add(grid[i][j - 1]);
                                grenzfelder.add(grid[i - 1][j]);
                            } else {
                                grenzfelder.add(grid[i - 1][j]);
                                grenzfelder.add(grid[i + 1][j]);
                                grenzfelder.add(grid[i][j - 1]);
                            }
                        } else if (i == 0) {
                            grenzfelder.add(grid[i + SIZE - 1][j]);
                            grenzfelder.add(grid[i + 1][j]);

                        } else if (i == SIZE - 1) {
                            grenzfelder.add(grid[i - SIZE + 1][j]);
                            grenzfelder.add(grid[i][j - 1]);
                            grenzfelder.add(grid[i][j + 1]);
                            grenzfelder.add(grid[i - 1][j]);
                        } else {
                            grenzfelder.add(grid[i][j + 1]);
                            grenzfelder.add(grid[i][j - 1]);
                            grenzfelder.add(grid[i + 1][j]);
                            grenzfelder.add(grid[i - 1][j]);
                        }

                    }
                }
            }

            for (Zell_Zustand feuerfelder1 : feuerfelder) {
                feuerfelder1.setStatus(0);

            }
            for (Zell_Zustand nachbarfelder1 : grenzfelder) {
                if (nachbarfelder1.getStatus() == 1) {
                    nachbarfelder1.setStatus(2);
                    ambrennen = true;
                }
            }
            if (ambrennen == true) {
                System.out.println("Feuer!" + " abgebrannte Bäume: " + feuerfelder.size());
            }

            if (wolkenzaehler > 1) {
                System.out.println("Regenwahrscheinlichkeit:" + regenWahrs * 100 + "%");
           }
          
            System.out.println(this);
        }
        feuerZahlen.put(tick, feuerfelder.size());
       
    }

 
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                s += "[" + this.grid[i][j] + "]";

            }
            s += "\n";
        }
        return s;
    }

    public static void main(String args[]) throws IOException {
        Auswertung test = new Auswertung(15);
   
        test.Zeit_Schritt(20, (float) 0.45);

        test.write("ergebnis");

    }
}
