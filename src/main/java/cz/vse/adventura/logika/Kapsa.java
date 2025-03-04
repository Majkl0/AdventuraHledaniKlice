package cz.vse.adventura.logika;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída Kapsa
 * Reprezentuje inventář hráče, do kterého si může ukládat předměty nalezené ve hře.
 * Umožňuje přidávání, odebírání a kontrolu předmětů v inventáři.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public class Kapsa {
    private static final int MAXIMALNI_POCET_VECI = 3; // Maximální počet věcí, které může hráč mít v kapse
    private static ObservableList<Vec> seznamVeci; // Seznam věcí v kapse hráče

    /**
     * Konstruktor třídy Kapsa.
     * Inicializuje prázdný seznam věcí.
     */
    public Kapsa() {
        seznamVeci = FXCollections.observableArrayList(); // Inicializace observable seznamu věcí
    }

    /**
     * Přidává předmět do kapsy hráče.
     * @param predmet předmět, který má být přidán do kapsy
     * @return true pokud byl předmět úspěšně přidán, jinak false
     */
    public boolean pridejPredmet(Vec predmet) {
        if (seznamVeci.size() >= MAXIMALNI_POCET_VECI) { // Kontrola, zda není kapsa plná
            return false;
        }
        boolean uspech = seznamVeci.add(predmet); // Přidání předmětu do seznamu věcí
        if (uspech) {
            aktualizujUI(); // Aktualizace uživatelského rozhraní
        }
        return uspech;
    }

    /**
     * Odebere předmět z kapsy hráče.
     * @param nazevVeci název předmětu, který má být odebrán
     * @return odebraný předmět, nebo null pokud předmět nebyl nalezen
     */
    public Vec odeberPredmet(String nazevVeci) {
        Vec predmet = seznamVeci.stream()
                .filter(vec -> vec.getNazev().equals(nazevVeci)) // Vyhledání předmětu podle názvu
                .findFirst()
                .orElse(null);
        if (predmet != null) {
            seznamVeci.remove(predmet); // Odebrání předmětu ze seznamu věcí
            aktualizujUI(); // Aktualizace uživatelského rozhraní
        }
        return predmet;
    }

    /**
     * Kontroluje, zda kapsa obsahuje předmět s daným názvem.
     * @param nazevVeci název předmětu
     * @return true pokud kapsa obsahuje předmět s daným názvem, jinak false
     */
    public boolean obsahujePredmet(String nazevVeci) {
        return seznamVeci.stream()
                .anyMatch(vec -> vec.getNazev().equals(nazevVeci)); // Kontrola, zda seznam věcí obsahuje předmět s daným názvem
    }

    /**
     * Vrací seznam věcí v kapse hráče.
     * @return observable seznam věcí
     */
    public ObservableList<Vec> getVeci() {
        return seznamVeci;
    }

    /**
     * Aktualizuje uživatelské rozhraní.
     * Používá se pro synchronizaci změn v seznamu věcí s grafickým rozhraním.
     */
    private void aktualizujUI() {
        Platform.runLater(() -> {
            List<Vec> kopieSeznamu = new ArrayList<>(seznamVeci); // Vytvoření kopie seznamu věcí
            seznamVeci.clear(); // Vymazání původního seznamu
            seznamVeci.addAll(kopieSeznamu); // Přidání všech věcí z kopie zpět do seznamu
        });
    }
}