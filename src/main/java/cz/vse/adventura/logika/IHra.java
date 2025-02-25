
package cz.vse.adventura.logika;

import cz.vse.adventura.main.PredmetPozorovani;

/**
 *  Rozhraní které musí implementovat hra, je na ně navázáno uživatelské rozhraní
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017, LS2024, 4IT110
 */
public interface IHra extends PredmetPozorovani
{
    /**
     *  Vrátí úvodní zprávu pro hráče.
     *
     *  @return  vrací se řetězec, který se má vypsat na obrazovku
     */
    public String vratUvitani();

    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     *
     *  @return  vrací se řetězec, který se má vypsat na obrazovku
     */
    public String vratEpilog();

    /**
     * Vrací informaci o tom, zda hra již skončila, je jedno zda výhrou, prohrou nebo příkazem konec.
     *
     * @return   vrací true, pokud hra skončila
     */
    public boolean konecHry();

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek);

    public HerniPlan getHerniPlan();
}
