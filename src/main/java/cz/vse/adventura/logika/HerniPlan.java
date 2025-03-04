package cz.vse.adventura.logika;

import cz.vse.adventura.main.Pozorovatel;
import cz.vse.adventura.main.PredmetPozorovani;
import cz.vse.adventura.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Třída HerniPlan
 * Představuje mapu (byt) a rozložení objektů (místností a věcí) v něm.
 * Slouží k přidání nových prostorů, věcí a zadání jejich vlastností.
 * Třída propojuje jednotlivé prostory a sleduje, kde se hráč nachází.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public class HerniPlan implements PredmetPozorovani {
    private Prostor aktualniProstor;  // Aktuální prostor, kde se hráč nachází
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>(); // Mapa změn hry a jejich pozorovatelů
    private Kapsa kapsa; // Kapsa hráče
    private Casovac casovac; // Časovač hry

    /**
     * Konstruktor třídy HerniPlan. Vytváří místnosti ve hře.
     */
    public HerniPlan() {
        kapsa = new Kapsa(); // Inicializace kapsy hráče
        vytvorMistnostiHry(); // Inicializace herního plánu
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>()); // Inicializace seznamu pozorovatelů pro každou změnu hry
        }
    }

    /**
     * Metoda pro vytvoření místností a jejich propojení.
     */
    private void vytvorMistnostiHry() {
        Prostor chodba = new Prostor("Chodba");
        Prostor obyvaciPokoj = new Prostor("Obývák");
        Prostor loznice = new Prostor("Ložnice");
        Prostor kuchyn = new Prostor("Kuchyň");

        chodba.setVychod(obyvaciPokoj);
        chodba.setVychod(loznice);
        chodba.setVychod(kuchyn);
        obyvaciPokoj.setVychod(chodba);
        loznice.setVychod(chodba);
        kuchyn.setVychod(chodba);

        aktualniProstor = chodba;

        // Přidání věcí na chodbu
        Vec kompas = new Vec("Kompas", true);
        Vec sroubovak = new Vec("Šroubovák", true);
        Vec boty = new Vec("Boty", true);
        Vec zamceneVchodoveDvere = new Vec("Zamčené dveře", false);
        chodba.vlozVec(kompas);
        chodba.vlozVec(sroubovak);
        chodba.vlozVec(boty);
        chodba.vlozVec(zamceneVchodoveDvere);

        // Přidání věcí do kuchyně
        Vec sul = new Vec("Sůl", true);
        Vec spinavyTalir = new Vec("Špinavý talíř", true);
        Vec zapalky = new Vec("Zápalky", true);
        kuchyn.vlozVec(sul);
        kuchyn.vlozVec(spinavyTalir);
        kuchyn.vlozVec(zapalky);

        // Přidání věcí do obývacího pokoje
        Vec dalkoveOvladani = new Vec("Dálkové ovládání", true);
        Vec kniha = new Vec("Knížka", true);
        Vec klicOdSkrine = new Vec("Klíč od skříně", true);
        obyvaciPokoj.vlozVec(dalkoveOvladani);
        obyvaciPokoj.vlozVec(kniha);
        obyvaciPokoj.vlozVec(klicOdSkrine);

        // Přidání věcí do ložnice
        Vec polstar = new Vec("Polštář", true);
        Vec perina = new Vec("Peřina", true);
        Vec zamcenaSkrin = new Vec("Zamčená skříň", false);
        loznice.vlozVec(polstar);
        loznice.vlozVec(perina);
        loznice.vlozVec(zamcenaSkrin);
    }

    /**
     * Vrátí aktuální prostor, ve kterém se hráč nachází.
     * @return aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     * Nastaví nový aktuální prostor.
     * @param novyProstor prostor, který se stane novým aktuálním prostorem - když hráč jde
     * do jiné místnosti
     */
    public void setAktualniProstor(Prostor novyProstor) {
        this.aktualniProstor = novyProstor;
        upozorniPozorovatele(ZmenaHry.ZMENA_MISTNOSTI); // Upozorní pozorovatele na změnu místnosti
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
    private void upozorniPozorovatele(ZmenaHry zmenaHry) {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj(); // Aktualizuje všechny registrované pozorovatele
        }
    }

    /**
     * Vrátí kapsu hráče.
     * @return kapsa hráče
     */
    public Kapsa getKapsa() {
        return kapsa;
    }

    /**
     * Vrátí časovač hry.
     * @return časovač hry
     */
    public Casovac getCasovac() {
        return casovac;
    }
}