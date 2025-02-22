package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.*;

/**
 * Třída PrikazVezmi
 * Implementuje pro hru příkaz "Vezmi". Tento příkaz umožňuje hráči sebrat předměty z aktuálního prostoru a
 * přidat je do svého inventáře (kapsy).
 * Příkaz zkontroluje, zda je předmět v místnosti a zda je možné ho sebrat. Pokud není kapsa plná,
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */
public class PrikazVezmi implements IPrikaz {
    private static final String NAZEV = "Vezmi";
    public Casovac casovac;
    private HerniPlan herniPlan;
    private Kapsa kapsa;


    /**
 * Konstruktor třídy PrikazVezmi.
 * Inicializuje herní plán a kapsu pro použití v příkazu "Vezmi".
 */
public PrikazVezmi(HerniPlan herniPlan, Kapsa kapsa, Casovac casovac) {
    this.herniPlan = herniPlan;
    this.kapsa = kapsa;
    this.casovac = casovac;
}

    /**
 * Provádí příkaz "vezmi". Metoda zpracovává logiku příkazu vezmi, který umožňuje hráči sebrat předmět
 * z aktuálního prostoru a přidat ho do svého inventáře. Metoda nejprve ověří, zda byl zadán název předmětu.
 * Pokud ne, informuje hráče, že musí zadat název předmětu. Pokud se ho hráč pokusí sebrat, je ověřeno zda má
 *v inventáři místo.
 */
    @Override
    public String provedPrikaz(String... parametry) {
        // Pokud uživatel nezadá žádný parametr, požádáme o jeho zadání
        if (parametry.length == 0) {
            return "Co mám sebrat? Musíš zadat název předmětu."; // Informujeme uživatele o potřebě zadat název předmětu
        }

    String nazevVeci = String.join(" ", parametry);
    Prostor aktualniProstor = herniPlan.getAktualniProstor();

        casovac.provedAkci();

        if (aktualniProstor.obsahujeVec(nazevVeci)) {
        Vec vec = aktualniProstor.vezmiVec(nazevVeci);

        if (!vec.jePrenositelna()) {
            aktualniProstor.vlozVec(vec); // Vrátíme věc zpět do prostoru
            return "Předmět '" + nazevVeci + "' opravdu sebrat nelze.";
        } else {
            boolean uspech = kapsa.pridejPredmet(vec);
            if (!uspech) {
                aktualniProstor.vlozVec(vec); // Vrátíme věc zpět do prostoru, protože kapsa je plná
                return "Kapsa je už plná. Předmět '" + nazevVeci + "' nelze sebrat.";
            }
            return "Sebral jsi předmět '" + nazevVeci + "' a dal jej do kapsy.";
        }
    } else {
        return "Předmět '" + nazevVeci + "' zde není.";
    }
}
    /**
     * Vrátí název tohoto příkazu.
     */
@Override
public String getNazev() {
    return NAZEV;
}

    /**
     * Vrátí popis příkazu "Vezmi", který slouží k informování hráče o tom, jak tento příkaz používat.
     * Tento popis je zobrazen v nápovědě.
     */
@Override
public String getPopis() {
    return "Příkaz 'Vezmi' [název předmětu v mísnosti] umožňuje hráči vzít předměty v místnosti a přidat je do svého inventáře(kapsy).";
}
}