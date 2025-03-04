package cz.vse.adventura.main;

/**
 * Výčtový typ ZmenaHry
 * Definuje různé druhy změn, které mohou nastat ve hře a které mohou být pozorovány pozorovateli.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public enum ZmenaHry {
    ZMENA_MISTNOSTI,  // Změna aktuální místnosti
    ZMENA_INVENTARE,  // Změna inventáře hráče
    KONEC_HRY,        // Konec hry
    ZMENA_CASU        // Změna zbývajícího času
}