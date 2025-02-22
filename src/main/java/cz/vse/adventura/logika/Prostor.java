package cz.vse.adventura.logika;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída Prostor
 * Popisuje jednotlivé prostory (místnosti) hry.
 *
 * Třída také umožňuje vkládání a vybírání věcí, které se v prostoru nacházejí.
 * Kontroluje zda jsme v místnosti byli/prozkoumali ho a vrací výstup.
 * Třída také provádí kontroly nad všemi věcmi, které se s prostorem dějí.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Michael Cerny
 * @version pro školní rok 2016/2017, LS2024, 4IT110
 */

public class Prostor {

    private String nazev;
    private Map<String, Prostor> vychody; // ukládá sousední prostory
    private Map<String, Vec> veci; // ukládá věci v prostoru
    private boolean bylaNavstivena = false;
    public boolean bylProzkoumany = false;

    /**
     * Konstruktor třídy Prostor. Udává místnosti název, východy a věci v ní.
     *
     * @param nazevProstoru Jméno místnsti.
     */
    public Prostor(String nazevProstoru) {
        this.nazev = nazevProstoru;
        vychody = new HashMap<>(); // Inicializace mapy pro ukládání východů z prostoru
        veci = new HashMap<>(); // Inicializace mapy pro ukládání věcí v prostoru
    }

    /**
     * Dává místnosti vlastnot zda byla prozkoumaná
     *
     * @return true, pokud byla prozkoumaná jinak false
     */
    public void setBylProzkoumany(boolean bylProzkoumany) {
        this.bylProzkoumany = bylProzkoumany;
    }

    /**
     * Přidá východ z místnosti do jiné.
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.put(vedlejsi.getNazev(), vedlejsi);
    }

    /**
     * Vloží věc do místnosti.
     *
     * @param vec Věc, která má být do prostoru přidána.
     */
    public void vlozVec(Vec vec) {
        veci.put(vec.getNazev(), vec);
    }

    /**
     * Odebere vec z místnosti a dá ji do inventáře
     */
    public Vec vezmiVec(String nazevPredmetu) {
        return veci.remove(nazevPredmetu);
    }

    /**
     * Vrací název prostoru.
     *
     * @return Název prostoru.
     */

    public String getNazev() {
        return nazev;
    }

    /**
     * Ptáme se místnosti zda obsahuje věc.
     *
     * @return true, pokud místnost obsahuje věc, jinak false.
     */
    public boolean obsahujeVec(String nazevPredmetu) {
        return veci.containsKey(nazevPredmetu);
    }

    /**
     * Vrací východy z místnosti.
     *
     * @return Mapa východů.
     */
    public Prostor vratSousedniProstor(String nazevSmeru) {
        return vychody.get(nazevSmeru);
    }

    /**
     * Vrací všechny věci v místnosti.
     *
     * @return soubor všech věcí.
     */

    public Map<String, Vec> getVeci() {
        return this.veci;
    }

    /**
     * Hledá zda je v místnosti hledaná věc.
     */

    public Vec najdiVec(String nazevVeci) {
        return veci.get(nazevVeci);
    }

    /**
     * Vytváří popis věcí nacházejících se v prostoru. Pokud nebyl prostor prozkoumán nebo neobsahuje žádné věci,
     * metoda vrátí prázdný řetězec. V opačném případě vrátí řetězec obsahující názvy věcí v prostoru.

     */
    private String popisVeci() {
        if (veci.isEmpty() || !bylProzkoumany) {      // Změna podmínky na !bylProzkoumany
            return "";
        } else {
            return "Věci v prostoru: " + String.join(", ", veci.keySet()); //string join tam je aby nebyly hranate zavroky
        }
    }

    /**
     * Vytváří textový řetězec všech východů z aktuálníáho prostoru.
     */

    private String popisVychodu() {
            return "Východy: " + String.join(", ", vychody.keySet());
        }


    /**
     * Vypisuje  popis aktuálního prostoru. Tato metoda vytváří řetězec, který zahrnuje
     * úvodní text založený na tom, zda byl prostor již navštíven, následovaný popisy
     * dostupných východů a předmětů v prostoru. Úvodní text se liší v závislosti na konkrétním prostoru
     * a na tom, zda hráč prostor již navštívil.
     *
     * Metoda zajišťuje, že popis východů a předmětů je zahrnut pouze v případě, že byl prostor prozkoumán
     * pomocí PrikazProzkoumej.
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

        if (bylProzkoumany) {
        }
        return uvodniText + "\n" + popisVychodu() + "\n" + popisVeci();}

    /**
     * Odstraňuje věc z prostoru podle zadaného názvu. Tato metoda vyhledá věc v mapě věcí prostoru
     * a pokud věc s daným názvem existuje, odstraní ji.
     *
     * @return true, pokud věc byla odstraněna, jinak false
     */
    public void odeberVec(String nazevVeci) {
        if (veci.containsKey(nazevVeci)) {
            veci.remove(nazevVeci);

    }
    }

}