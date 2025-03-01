package cz.vse.adventura.logika;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Vec {
    private String nazev;
    private boolean jePrenositelna;
    static Hra hra;

    /**
     * Konstruktor pro vytvoření instance třídy Vec.
     * @param nazevVeci Název předmětu
     * @param jePrenositelna Určuje, zda lze předmět přenášet
     */
    public Vec(String nazevVeci, boolean jePrenositelna) {
        this.nazev = nazevVeci;
        this.jePrenositelna = jePrenositelna;
    }

    /**
     * Nastaví referenci na hlavní třídu hry
     */
    public static void setHra(Hra hra) {
        Vec.hra = hra;
    }

    /**
     * @return Název předmětu
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * @return true pokud je předmět přenositelný, jinak false
     */
    public boolean jePrenositelna() {
        return jePrenositelna;
    }
    private static ObservableList<Vec> seznamVeci = FXCollections.observableArrayList();

    public static ObservableList<Vec> getSeznamVeci() {
        return seznamVeci;
    }
}