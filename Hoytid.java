/* Laget av
****Magnus J�rem Moltzau, s180473
****Anders N�dland Danielsen, s180475
****siste oppdatering: 11.04.12
****Beskrivelse:
* Hoytid.java inneholder klassen Hoytid.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;

//Klassen definerer Hoytid-objekter, med fra- og tildato, samt h�ytidsnavn.
public class Hoytid implements Serializable{
    private static final long serialVersionUID = 400L;
    GregorianCalendar fraDato;
    GregorianCalendar tilDato;
    String navn;
    
    //Konstrukt�ren oppretter nytt Hoytid-objekt med mottatt fra- og tildate og navn.
    public Hoytid(GregorianCalendar f, GregorianCalendar t, String n){
        fraDato=f;
        tilDato=t;
        navn=n;
    }
    
    public String getNavn(){ //Returnerer navnet p� h�ytiden.
        return navn;
    }
    
    public GregorianCalendar getFraDato(){ //Returnerer starten p� h�ytiden.
        return fraDato;
    }
    
    public GregorianCalendar getTilDato(){ //Returnerer slutten p� h�ytiden.
        return tilDato;
    }
    
    /*Definerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    public boolean equals(Hoytid h){
        return navn.equals(h.getNavn());
    }
    
    //Returnerer en tekststreng med informasjon om h�ytiden.
    @Override
    public String toString(){
        return navn.toUpperCase() + "\n" +
               "Fra: " + fraDato + " Til: " + tilDato + "\n";
    }
} //Slutt p� klassen Hoytid.