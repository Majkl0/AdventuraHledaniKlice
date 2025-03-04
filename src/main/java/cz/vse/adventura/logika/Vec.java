package cz.vse.adventura.logika;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída Vec
 * Reprezentuje předměty ve hře, které mohou být přenášeny nebo zůstat na místě.
 * Každý předmět má název, informaci o přenositelnosti a cestu k obrázku.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public class Vec {
    private String nazev; // Název předmětu
    private boolean jePrenositelna; // Určuje, zda lze předmět přenášet
    private String obrazek; // Cesta k obrázku předmětu
    public static Hra hra; // Odkaz na instanci hry

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
     * Nastaví instanci hry.
     * @param hra Instance hry
     */
    public static void setHra(Hra hra) {
        Vec.hra = hra;
    }

    /**
     * Vrací název předmětu.
     * @return Název předmětu
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací, zda je předmět přenositelný.
     * @return true, pokud je předmět přenositelný, jinak false
     */
    public boolean jePrenositelna() {
        return jePrenositelna;
    }

    private static ObservableList<Vec> seznamVeci = FXCollections.observableArrayList(); // Seznam všech věcí

    /**
     * Vrací seznam všech věcí.
     * @return ObservableList všech věcí
     */
    public static ObservableList<Vec> getSeznamVeci() {
        return seznamVeci;
    }

    /**
     * Vrací instanci hry.
     * @return Instance hry
     */
    public static Hra getHra() {
        return hra;
    }
}