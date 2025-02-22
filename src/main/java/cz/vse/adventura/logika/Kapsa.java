package cz.vse.adventura.logika;

import java.util.HashMap;
import java.util.Map;

/**
 *Třída Kapsa
 * Třída reprezentuje inventář hráče. Má omezenou kapacitu, která se zde nastavuje.
 * Třída poskytuje metody pro manipulaci s předměty v inventáři - přidávání a odebírání předmětů,
 * hledání, zda se předmět nachází v inventáři(pro fungování odemykání a také pro zobrazení obsahu inventáře.)
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */

public class Kapsa {
    private Map<String, Vec> seznamVeci;
    private int MAXIMALNI_POCET_VECI = 3;
    /**
     * Konstruktor třídy Kapsa.
     * Uchovává předměty v inventáři.
     */
    public Kapsa() {
        seznamVeci = new HashMap<>();
    }

    /**
     * Přidává předmět do inventáře (kapsy) hráče. Pokud je kapsa plná, předmět nebude přidán,
     * a metoda vrátí false. Po úspěšném přidání předmětu se vypíše aktuální obsah kapsy.
     *
     * @param predmet Předmět, který má být přidán do kapsy.
     * @return true, pokud byl předmět úspěšně přidán, false, pokud je kapsa plná.
     */
public boolean pridejPredmet(Vec predmet) {
    if (seznamVeci.size() >= MAXIMALNI_POCET_VECI) {
        System.out.println("Kapsa je už plná, musíš něco vyhodit.");
        return false;
    }
    seznamVeci.put(predmet.getNazev(), predmet);
    System.out.println(vypisObsah());
    return true;
}
    /**
     * Odstraní předmět z inventáře podle zadaného názvu.
     *
     * @param nazevVeci Název předmětu k odebrání.
     * @return Odebraný předmět, pokud byl nalezen, jinak null.
     */
    public Vec odeberPredmet(String nazevVeci) {
        return seznamVeci.remove(nazevVeci);
    }

    /**
     * Zjišťuje, zda inventář obsahuje předmět s daným názvem.
     *
     * @param nazevVeci Název předmětu.
     * @return true, pokud inventář obsahuje předmět, jinak false.
     */
    public boolean obsahujePredmet(String nazevVeci) {
        return seznamVeci.containsKey(nazevVeci);
    }

    /**
     * Vypíše obsah kapsy hráče. Pokud je kapsa prázdná, informuje o tom.
     * V opačném případě vypíše seznam všech předmětů v kapse.
     *
     * @return Popis obsahu kapsy.
     */
  public String vypisObsah() {
    if (seznamVeci.isEmpty()) {
        return "Kapsa je prázdná.";
    } else {
        return "V kapse máš:\n- " + String.join("\n- ", seznamVeci.keySet());
    }
}
}