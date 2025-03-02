package cz.vse.adventura.logika;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Vec {
    private String nazev;
    private boolean jePrenositelna;
    private String obrazek; // přidáno pro cestu k obrázku
    public static Hra hra;

    /**
     * Konstruktor pro vytvoření instance třídy Vec.
     * @param nazevVeci Název předmětu
     * @param jePrenositelna Určuje, zda lze předmět přenášet
     */
    public Vec(String nazevVeci, boolean jePrenositelna) {
        this.nazev = nazevVeci;
        this.jePrenositelna = jePrenositelna;
        this.obrazek = "/cz.vse.adventura/main/prostory/veci/" + nazevVeci.toLowerCase().replace(" ", "_") + ".jpg";
    }

    /**
     * Konstruktor pro vytvoření instance třídy Vec s vlastní cestou k obrázku
     * @param nazevVeci Název předmětu
     * @param jePrenositelna Určuje, zda lze předmět přenášet
     * @param obrazek Cesta k obrázku předmětu
     */
    public Vec(String nazevVeci, boolean jePrenositelna, String obrazek) {
        this.nazev = nazevVeci;
        this.jePrenositelna = jePrenositelna;
        this.obrazek = obrazek;
    }

    public static void setHra(Hra hra) {
        Vec.hra = hra;
    }

    public String getNazev() {
        return nazev;
    }

    public boolean jePrenositelna() {
        return jePrenositelna;
    }

    public String getObrazek() {
        return obrazek;
    }

    private static ObservableList<Vec> seznamVeci = FXCollections.observableArrayList();

    public static ObservableList<Vec> getSeznamVeci() {
        return seznamVeci;
    }
    public static Hra getHra() {
        return hra;
    }
}