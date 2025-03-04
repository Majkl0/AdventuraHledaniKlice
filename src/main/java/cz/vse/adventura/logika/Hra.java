package cz.vse.adventura.logika;

import cz.vse.adventura.Prikazy.*;
import cz.vse.adventura.main.Pozorovatel;
import cz.vse.adventura.main.PredmetPozorovani;
import cz.vse.adventura.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Třída Hra
 * Třída vytváří seznam platných příkazů, nastavuje zbývající čas a vypisuje uvítací
 * a ukončovací text hry. Dále kontroluje, zda hra skončila - true/false.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy; // Seznam platných příkazů ve hře
    private HerniPlan herniPlan; // Herní plán obsahující místnosti a předměty
    private boolean konecHry = false; // Stav, zda hra skončila
    private Casovac casovac; // Časovač hry
    private Kapsa kapsa; // Inventář hráče

    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>(); // Mapa změn hry a jejich pozorovatelů

    /**
     * Konstruktor třídy Hra.
     * Volá herní plán, časovač, inventář a seznam platných příkazů.
     * Zde si nastavujeme počet kroků hry.
     * Do seznamu platných příkazů vkládá instance příkazů, které mohou být ve hře použity,
     * což umožňuje hráči používat příkazy.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        casovac = new Casovac(30); // Inicializace časovače s 30 minutami
        kapsa = new Kapsa(); // Inicializace inventáře hráče
        platnePrikazy = new SeznamPrikazu(); // Inicializace seznamu platných příkazů

        // Vložení příkazů do seznamu platných příkazů
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, casovac));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(herniPlan, kapsa, casovac));
        platnePrikazy.vlozPrikaz(new PrikazVyhod(kapsa, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(herniPlan, kapsa, casovac, this));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan, casovac));

        // Inicializace seznamu pozorovatelů pro každou změnu hry
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     * Vrací uvítací text hry.
     * @return Uvítací text.
     */
    @Override
    public String vratUvitani() {
        return "Vítejte ve hře!\nSpěcháš na zkoušku, ale zjistil si, že vchodové dveře jsou zamčené. " +
                "Rychle najdi klíč a dostaň se ven aby si stihl zkoušku.\n"
                + herniPlan.getAktualniProstor().dlouhyPopis() + "\nZbývající čas: "
                + casovac.getZbyvajiciAkce() + " minut";
    }

    /**
     * Vrací ukončovací text hry, když hráč vyčerpal všechny kroky nebo když vyhrál + kolik minut
     * mu zbývalo.
     * @return Ukončovací text.
     */
    @Override
    public String vratEpilog() {
        if (casovac.doselCas()) {
            return "Čas vypršel. Nestihl si dojít na zkoušku včas. \nHra skončila.";
        } else {
            return "Gratuluji, vyhrál jsi! Zvládl si odejít z bytu včas a dojít na zkoušku. \nZbývalo ti "
                    + casovac.getZbyvajiciAkce() + " minut.";
        }
    }

    /**
     * Kontroluje, zda hra skončila.
     * @return true pokud hra skončila, jinak false.
     */
    @Override
    public boolean konecHry() {
        return (casovac.doselCas() || konecHry);
    }

    /**
     * Zpracovává příkazy hráče.
     * @param radek Vstup od hráče.
     * @return Výsledek zpracování příkazu.
     */
    @Override
    public String zpracujPrikaz(String radek) {
        String[] slova = radek.split("[ \\t]+");
        String slovoPrikazu = slova[0];
        String[] parametry = new String[slova.length - 1];
        for (int i = 0; i < parametry.length; i++) {
            parametry[i] = slova[i + 1];
        }
        String textKVypsani = " .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
            if (casovac.getZbyvajiciAkce() <= 0) {
                setKonecHry(true);
                textKVypsani += "\n" + vratEpilog();
            }
        } else {
            textKVypsani = "Nevím co tím myslíš? Tento příkaz neznám.";
        }
        return textKVypsani;
    }

    /**
     * Vrací herní plán.
     * @return herní plán
     */
    @Override
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

    /**
     * Nastaví stav hry na konečný.
     * @param konecHry true pokud hra končí, jinak false.
     */
    @Override
    public void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
        upozorniPozorovatele(ZmenaHry.KONEC_HRY); // Upozorní pozorovatele na konec hry
    }

    /**
     * Registruje pozorovatele pro danou změnu hry.
     * @param zmenaHry typ změny hry
     * @param pozorovatel pozorovatel, který bude registrován
     */
    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel); // Přidá pozorovatele do seznamu pro danou změnu hry
    }

    /**
     * Upozorní všechny pozorovatele na danou změnu hry.
     * @param zmenaHry typ změny hry
     */
    public void upozorniPozorovatele(ZmenaHry zmenaHry) {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj(); // Aktualizuje všechny registrované pozorovatele
        }
    }
}