package cz.vse.adventura.main;

/**
 * Rozhraní Pozorovatel
 * Rozhraní pro objekty, které chtějí být informovány o změnách v herním plánu nebo hře.
 * Implementují ho třídy, které potřebují být aktualizovány na základě určitých změn ve hře.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public interface Pozorovatel {
    /**
     * Metoda aktualizuj
     * Slouží k aktualizaci stavu pozorovatele na základě změn ve hře.
     */
    void aktualizuj();
}