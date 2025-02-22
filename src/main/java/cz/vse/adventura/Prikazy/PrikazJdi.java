package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.Casovac;
import cz.vse.adventura.logika.HerniPlan;
import cz.vse.adventura.logika.IPrikaz;
import cz.vse.adventura.logika.Prostor;

/**
 * Třída PrikazJdi
 * Zpracovává příkaz "Jdi", který umožňuje hráči pohyb mezi místnostmi.
 * Příkaz vyžaduje jako parametr název místnosti, do kterého se hráč chce přesunout.
 * Validita cílového prostoru a jeho dostupnost z aktuálního prostoru je ověřována před provedením přesunu.
 *
 * Autoři: Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Michael Cerny
 * Verze: pro školní rok 2016/2017, LS2024, 4IT110
 */
public class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "Jdi";
    private HerniPlan herniPlan;
    private Casovac casovac;

    /**
     * Konstruktor třídy PrikazJdi.
     * Inicializuje herní plán, ve kterém se bude příkaz "jdi" provádět.
     */

    public PrikazJdi(HerniPlan herniPlan, Casovac casovac) {
        this.herniPlan = herniPlan;
        this.casovac = casovac;
    }

    /**
     * Provádí příkaz "Jdi". Zkoumá, zda byl zadán správně,
     * a ověřuje, zda je možné do prostoru jít z aktuálního umístění hráče.
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Kam mám jít? Musíš zadat název východu.";
        }

        String smer = parametry[0];
        Prostor sousedniProstor = herniPlan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }

        herniPlan.setAktualniProstor(sousedniProstor);
        casovac.provedAkci();
        return sousedniProstor.dlouhyPopis();
    }

    /**
     * Vrací název příkazu - Jdi
     *
     * @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    /**
     * Vrací popis příkazu - co příkaz dělá a jak se používá.
     *
     * @return popis příkazu
     */
    @Override
    public String getPopis() {
        return "Příkaz 'Jdi' [název místnosti] umožňuje hráči pohyb mezi místnostmi.";
    }
}
