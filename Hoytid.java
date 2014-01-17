/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 11.04.12
****Beskrivelse:
* Hoytid.java inneholder klassen Hoytid.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;

//Klassen definerer Hoytid-objekter, med fra- og tildato, samt høytidsnavn.
public class Hoytid implements Serializable{
    private static final long serialVersionUID = 400L;
    GregorianCalendar fraDato;
    GregorianCalendar tilDato;
    String navn;
    
    //Konstruktøren oppretter nytt Hoytid-objekt med mottatt fra- og tildate og navn.
    public Hoytid(GregorianCalendar f, GregorianCalendar t, String n){
        fraDato=f;
        tilDato=t;
        navn=n;
    }
    
    public String getNavn(){ //Returnerer navnet på høytiden.
        return navn;
    }
    
    public GregorianCalendar getFraDato(){ //Returnerer starten på høytiden.
        return fraDato;
    }
    
    public GregorianCalendar getTilDato(){ //Returnerer slutten på høytiden.
        return tilDato;
    }
    
    /*Definerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    public boolean equals(Hoytid h){
        return navn.equals(h.getNavn());
    }
    
    //Returnerer en tekststreng med informasjon om høytiden.
    @Override
    public String toString(){
        return navn.toUpperCase() + "\n" +
               "Fra: " + fraDato + " Til: " + tilDato + "\n";
    }
} //Slutt på klassen Hoytid.