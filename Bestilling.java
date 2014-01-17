/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 07.05.12
****Beskrivelse:
* Bestilling.java inneholder klassen Bestilling.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;

/*Klassen definerer Bestilling-objekter som tilhører firmareservasjoner.
 * Inneholder informasjon om mat og drikke som er bestilt og hvor og når den ønskes levert.*/
public class Bestilling implements Serializable{
    private static final long serialVersionUID = 100L;
    private int antKaffe;
    private int antBrus;
    private int antFrukt;
    private int antSandwich;
    private int leveringsRom;
    private GregorianCalendar leveringsTid;
    
    /*Konstruktør som oppretter objekt av typen Bestilling. 
     * Parametrene angir antall av forskjellig mat og drikke, samt romnummer og dato for levering.*/
    public Bestilling(int kaffe, int brus, int frukt, int sand, int rom, GregorianCalendar tid){
        antKaffe=kaffe;
        antBrus=brus;
        antFrukt=frukt;
        antSandwich=sand;
        leveringsRom=rom;
        leveringsTid=tid;
    }
    
    public int getAntKaffe(){ //Returnerer antall kaffe i bestillingen.
        return antKaffe;
    }
    
    public int getAntBrus(){ //Returnerer antall brus i bestillingen.
        return antBrus;
    }
    
    public int getAntFrukt(){ //Returnerer antall frukt i bestillingen.
        return antFrukt;
    }
    
    public int getAntSandwich(){ //Returnerer antall sandwicher i bestillingen.
        return antSandwich;
    }
    
    public int getLeveringsRom(){ //Returnerer rommet bestillingen skal leveres til.
        return leveringsRom;
    }
    
    public GregorianCalendar getLeveringsTid(){ //Returnerer når bestillingen skal leveres.
        return leveringsTid;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){ 
        String varstr = leveringsTid.get(GregorianCalendar.DAY_OF_YEAR)+leveringsRom+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Bestilling){
            Bestilling b=(Bestilling) o;
            return b.getLeveringsTid().get(GregorianCalendar.DAY_OF_YEAR)==leveringsTid.get(GregorianCalendar.DAY_OF_YEAR)
                && b.leveringsRom==leveringsRom;
        }
        return false;
    }
} //Slutt på klasse Bestilling