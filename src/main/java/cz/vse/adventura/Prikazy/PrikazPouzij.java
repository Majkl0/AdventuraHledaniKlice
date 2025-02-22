package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.*;

/**
 * Třída PrikazPouzij
 * Implementuje pro hru příkaz "Použij", který umožňuje používat klíč z inventáře na odemykání
 * zamčených objektů ve hře.
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */

public class PrikazPouzij implements IPrikaz {
    private static final String NAZEV = "Použij";
    private HerniPlan herniPlan;
    public Casovac casovac;
    private Kapsa kapsa;
    private Hra hra;

    /**
 * Konstruktor třídy PrikazPouzij, inicializuje třídu s herním plánem, kapsou (inventářem hráče),
 * časovačem a instancí hry. Umožňuje hráči použít klíče s inventáře, interagovat se zamčenými objekty a ubírá čas.
 */
public PrikazPouzij(HerniPlan herniPlan, Kapsa kapsa, Casovac casovac, Hra hra) {
        this.herniPlan = herniPlan;
        this.kapsa = kapsa;
        this.casovac = casovac;
        this.hra = hra;

}

    /**
     * Udává nám logiku, jak se odemykají dveře/skříň a co se stane pokud správně věc odemčeme.
     * Pokud odemčeme správně skříň, odebere se předmět Klíč od skříně, získáme klíč od dveří a
     * zamčená skříň se změní na odemčenou.
     *Pokud správně odemčeme dveře - vyhrajeme hru
     *
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length < 2) {
            return "Musíš specifikovat, co chceš použít a na co, například: 'Použij Klíč od dveří Zamčené dveře'.";
        }

        casovac.provedAkci();
        String celkovyPrikaz = String.join(" ", parametry);
        if ("Klíč od skříně Zamčená skříň".equals(celkovyPrikaz)) {
            return odemkniSkrin();
        }
        else if ("Klíč od dveří Zamčené dveře".equals(celkovyPrikaz)) {
            return otevriDvere();
        }
        else {
            return "Nelze použít příkaz Použij " + celkovyPrikaz + ". Zkus to formulovat jinak nebo najdi jiné řešení.";
        }
    }

    private String odemkniSkrin() {
        if (kapsa.obsahujePredmet("Klíč od skříně") && herniPlan.getAktualniProstor().najdiVec("Zamčená skříň") != null) {
        kapsa.odeberPredmet("Klíč od skříně");
        herniPlan.getAktualniProstor().odeberVec("Zamčená skříň");
        herniPlan.getAktualniProstor().vlozVec(new Vec("Odemčená skříň", false));
        kapsa.pridejPredmet(new Vec("Klíč od dveří", true));
        return "Skříň byla úspěšně odemčena, našel si v ní klíč od dveří.";
        } else {
            return "Nemáš 'Klíč od skříně' nebo v místnosti není 'Zamčená skříň'.";
        }
    }

    private String otevriDvere() {
        if (kapsa.obsahujePredmet("Klíč od dveří") && "Chodba".equals(herniPlan.getAktualniProstor().getNazev())){
            hra.setKonecHry(true);
            return "Použil jsi Klíč od dveří na Zamčené dveře a otevřel jsi cestu ven.";
        } else {
            return "Nemáš 'Klíč od dveří' nebo nejsi na správném místě 'Chodba' pro jeho použití.";
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
 * Vrátí popis příkazu "Použij", který slouží k informování hráče o tom, jak tento příkaz používat.
 * Tento popis je zobrazen v nápovědě.
 */
@Override
public String getPopis() {
    return "Příkaz 'Použij' [název předmětu z kapsy název zamčeného místa], Použije předmět z inventáře na určitý cíl. Například 'Použij Klíč od Skříně Zamčená skříň' nebo 'Použij Klíč od dveří Zamčené vchodové dveře'.";
}
}