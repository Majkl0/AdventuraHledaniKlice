package cz.vse.adventura.logika;

/**
 *  Třída implementující toto rozhraní bude ve hře zpracovávat jeden konkrétní příkaz.
 *
 *@author     Jarmila Pavlickova, Michael Cerny(přidal jsem jen getPopis a dal interface public)
 *@version    pro školní rok 2016/2017, LS2024, 4IT110
 *
 */
public interface IPrikaz {

    /**
     *  Metoda pro provedení příkazu ve hře.
     *  Počet parametrů je závislý na konkrétním příkazu,
     *  např. příkazy konec a napoveda nemají parametry
     *  příkazy jdi, seber, polož mají jeden parametr
     *  příkaz pouzij může mít dva parametry.
     *
     *  @param parametry počet parametrů závisí na konkrétním příkazu.
     *
     */
    String provedPrikaz(String... parametry);

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     *  @return nazev prikazu
     */
    String getNazev();

    /**
     *  Metoda vrací popis nápovědy od příkazu.
     *
     *  @return popis nápovědy k příkazu
     */
    String getPopis();

}
