package cz.vse.adventura.logika;

import java.util.*;

/**
 * Třída Prostor
 * Popisuje jednotlivé prostory (místnosti) hry.
 *
 * Třída také umožňuje vkládání a vybírání věcí, které se v prostoru nacházejí.
 * Kontroluje, zda jsme v místnosti byli/prozkoumali ho a vrací výstup.
 * Třída také provádí kontroly nad všemi věcmi, které se s prostorem dějí.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */

public class Prostor {

    private String nazev; // Název prostoru
    private Map<String, Prostor> vychody; // Ukládá sousední prostory
    private Map<String, Vec> veci; // Ukládá věci v prostoru
    private boolean bylaNavstivena = false; // Indikátor, zda byl prostor navštíven
    public boolean bylProzkoumany = false; // Indikátor, zda byl prostor prozkoumán

    /**
     * Konstruktor třídy Prostor. Udává místnosti název, východy a věci v ní.
     *
     * @param nazevProstoru Jméno místnosti.
     */
    public Prostor(String nazevProstoru) {
        this.nazev = nazevProstoru;
        vychody = new HashMap<>(); // Inicializace mapy pro ukládání východů z prostoru
        veci = new HashMap<>(); // Inicializace mapy pro ukládání věcí v prostoru
    }

    /**
     * Nastaví, zda byl prostor prozkoumán.
     *
     * @param bylProzkoumany true, pokud byl prostor prozkoumán, jinak false
     */
    public void setBylProzkoumany(boolean bylProzkoumany) {
        this.bylProzkoumany = bylProzkoumany;
    }

    /**
     * Přidá východ z místnosti do jiné.
     *
     * @param vedlejsi Prostor, který je východem z aktuálního prostoru
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.put(vedlejsi.getNazev(), vedlejsi);
    }

    /**
     * Vloží věc do místnosti.
     *
     * @param vec Věc, která má být do prostoru přidána
     */
    public void vlozVec(Vec vec) {
        veci.put(vec.getNazev(), vec);
    }

    /**
     * Odebere věc z místnosti a dá ji do inventáře.
     *
     * @param nazevPredmetu Název předmětu, který má být odebrán
     * @return Odebraný předmět, nebo null pokud předmět nebyl nalezen
     */
    public Vec vezmiVec(String nazevPredmetu) {
        return veci.remove(nazevPredmetu);
    }

    /**
     * Vrací název prostoru.
     *
     * @return Název prostoru
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Ptáme se místnosti, zda obsahuje věc.
     *
     * @param nazevPredmetu Název předmětu
     * @return true, pokud místnost obsahuje věc, jinak false
     */
    public boolean obsahujeVec(String nazevPredmetu) {
        return veci.containsKey(nazevPredmetu);
    }

    /**
     * Vrací východy z místnosti.
     *
     * @param nazevSmeru Název směru východu
     * @return Sousední prostor ve zvoleném směru, nebo null pokud východ neexistuje
     */
    public Prostor vratSousedniProstor(String nazevSmeru) {
        return vychody.get(nazevSmeru);
    }

    /**
     * Vrací všechny věci v místnosti.
     *
     * @return Mapa všech věcí v místnosti
     */
    public Map<String, Vec> getVeci() {
        return this.veci;
    }

    /**
     * Hledá zda je v místnosti hledaná věc.
     *
     * @param nazevVeci Název hledané věci
     * @return Hledaná věc, nebo null pokud věc nebyla nalezena
     */
    public Vec najdiVec(String nazevVeci) {
        return veci.get(nazevVeci);
    }

    /**
     * Vytváří popis věcí nacházejících se v prostoru. Pokud nebyl prostor prozkoumán nebo neobsahuje žádné věci,
     * metoda vrátí prázdný řetězec. V opačném případě vrátí řetězec obsahující názvy věcí v prostoru.
     *
     * @return Popis věcí v prostoru
     */
    private String popisVeci() {
        if (veci.isEmpty() || !bylProzkoumany) {
            return "";
        } else {
            return "Věci v prostoru: " + String.join(", ", veci.keySet()); // String join tam je aby nebyly hranaté závorky
        }
    }

    /**
     * Vytváří textový řetězec všech východů z aktuálního prostoru.
     *
     * @return Popis východů z aktuálního prostoru
     */
    private String popisVychodu() {
        return "Východy: " + String.join(", ", vychody.keySet());
    }

    /**
     * Vrací seznam názvů východů z aktuálního prostoru.
     *
     * @return Seznam názvů východů
     */
    public Collection<Prostor> getSeznamVychodu() {
        return vychody.values();
    }

    /**
     * Vypisuje popis aktuálního prostoru. Tato metoda vytváří řetězec, který zahrnuje
     * úvodní text založený na tom, zda byl prostor již navštíven, následovaný popisy
     * dostupných východů a předmětů v prostoru. Úvodní text se liší v závislosti na konkrétním prostoru
     * a na tom, zda hráč prostor již navštívil.
     *
     * Metoda zajišťuje, že popis východů a předmětů je zahrnut pouze v případě, že byl prostor prozkoumán
     * pomocí PrikazProzkoumej.
     *
     * @return Dlouhý popis aktuálního prostoru
     */
    public String dlouhyPopis() {
        String uvodniText = "";
        if (!bylaNavstivena) {
            if (nazev.equals("Kuchyň")) {
                uvodniText = "Nacházíš se v kuchyni, zkus ji trochu prozkoumat";
            } else if (nazev.equals("Ložnice")) {
                uvodniText = "Nacházíš se v ložnici, zkus to zde prozkoumat";
            } else if (nazev.equals("Obývák")) {
                uvodniText = "Nacházíš se v obývacím pokoji, prozkoumej ho.";
            } else if (nazev.equals("Chodba")) {
                uvodniText = "Nacházíš se na chodbě, jsou zde dveře kterými musíš utéct.";
            }
            bylaNavstivena = true;
        } else {
            if (nazev.equals("Kuchyň")) {
                uvodniText = "Opět se nacházíš v kuchyni.";
            } else if (nazev.equals("Ložnice")) {
                uvodniText = "Jsi opět v ložnici.";
            } else if (nazev.equals("Obývák")) {
                uvodniText = "Nacházíš se opět v obývacím pokoji.";
            } else if (nazev.equals("Chodba")) {
                uvodniText = "Nacházíš se zase na chodbě.";
            }
        }

        return uvodniText + "\n" + popisVychodu() + "\n" + popisVeci();
    }

    /**
     * Odstraňuje věc z prostoru podle zadaného názvu. Tato metoda vyhledá věc v mapě věcí prostoru
     * a pokud věc s daným názvem existuje, odstraní ji.
     *
     * @param nazevVeci Název věci, která má být odstraněna
     */
    public void odeberVec(String nazevVeci) {
        if (veci.containsKey(nazevVeci)) {
            veci.remove(nazevVeci);
        }
    }

    @Override
    public String toString() {
        return getNazev();
    }
}