package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.*;

/**
 * Třída PrikazProzkoumej
 * Iplementuje příkaz "Prozkoumej" ve hře a umožňuje hráči získat popis věcí, které se nachází v aktuálním prostoru.
 * Příkaz se používá při vstupu do prostoru bez další specifikace. Pokud se do místnosti vrátíme znovu, je místnosti
 * již prozkoumaná.
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */
public class PrikazProzkoumej implements IPrikaz {
    private static final String NAZEV = "Prozkoumej"; //příkaz pro zadání
    private HerniPlan herniPlan;
    public Casovac casovac;


    public PrikazProzkoumej(HerniPlan herniPlan, Casovac casovac) {
        this.herniPlan = herniPlan;
        this.casovac = casovac;

    }

    /**
     * Provede příkaz "Prozkoumej", který umožňuje hráči získat informace o věcech v aktuálním prostoru.
     */
    @Override
    public String provedPrikaz(String... parametry) {
        casovac.provedAkci();

        if (parametry.length == 0) {
            Prostor aktualniProstor = herniPlan.getAktualniProstor();
            aktualniProstor.setBylProzkoumany(true);

            String veciVProstoru = String.join(", ", aktualniProstor.getVeci().keySet());

            if (veciVProstoru.isEmpty()) {
                return "V místnosti nejsou žádné věci.";
            } else {
                return "V místnosti se nachází: " + veciVProstoru;
            }
        }
        return null;
    }

    /**
     * Vrátí název tohoto příkazu.
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    /**
     * Vrátí popis příkazu "Prozkoumej", který slouží k informování hráče o tom, jak tento příkaz používat.
     * Tento popis je zobrazen v nápovědě.
     */
@Override
public String getPopis() {
    return "Příkaz 'prozkoumej' vypíše hráči všechny věci, které se nacházejí v prostoru. Při opětovné návštěvě místnosti už příkaz nemusíme zadávat.";
}
}