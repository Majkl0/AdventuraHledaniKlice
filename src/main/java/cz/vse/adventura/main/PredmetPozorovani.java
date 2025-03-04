package cz.vse.adventura.main;

/**
 * Rozhraní PredmetPozorovani
 * Rozhraní pro objekty, které mohou být pozorovány pozorovateli.
 * Poskytuje metodu pro registraci pozorovatelů, kteří budou informováni o změnách.
 *
 * Autoři: Michael Cerny
 * Verze: LS2025, 4IT115
 */
public interface PredmetPozorovani {
    /**
     * Registruje pozorovatele pro danou změnu hry.
     * @param zmenaHry typ změny hry
     * @param pozorovatel pozorovatel, který bude registrován
     */
    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}