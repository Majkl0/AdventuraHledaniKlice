package cz.vse.adventura.logika;

/**
 * Třída Casovac
 * Slouží k počítání zbývajících akcí hráče ve hře.
 * Počet akcí hráče je omezen(v Hra.java) každá akce snižuje tento počet.
 * Když počet akcí klesne na 0, hra končí.
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */
public class Casovac {
    private int zbyvajiciAkce;

    /**
     * Konstruktor zadává časovač s počátečním počtem akcí.
     *
     * @param pocatecniPocetAkci Počáteční počet akcí dostupných hráči.
     */
    public Casovac(int pocatecniPocetAkci)
    {
        this.zbyvajiciAkce = pocatecniPocetAkci;
    }

    /**
     * Sníží počet zbývajících akcí hráče o jednu. Také kontroluje zda není počet zbývajících akcí
     * Roven 10/20, poté vypíše varování
     */

    public int provedAkci() {
        zbyvajiciAkce--;

    if (zbyvajiciAkce == 20) {
        System.out.println("Pozor, máš už jen 20 minut!");
    } else if (zbyvajiciAkce == 10) {
        System.out.println("Pozor, máš už jen 10 minut!");
    }
    return zbyvajiciAkce;

    }

    /**
     * Vrátí aktuální počet zbývajících akcí hráče.
     *
     * @return Počet zbývajících akcí.
     */
    public int getZbyvajiciAkce() {
        return zbyvajiciAkce;
    }

    /**
     * Zjistí, zda hráči již došly akce.
     *
     * @return true, pokud hráči došly akce, jinak false.
     */

    public boolean doselCas() {
        return zbyvajiciAkce == 0;
    }

}