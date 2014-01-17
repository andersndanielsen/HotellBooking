/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 08.05.12
****Beskrivelse:
* Reservasjon.java inneholder den abstrakte superklassen Reservasjon 
* og subklassene PrivatReservasjon og FirmaReservasjon.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Klassen bestemmer hva subklassene skal inneholde.
abstract public class Reservasjon implements Serializable{
    private static final long serialVersionUID = 500L;
    private GregorianCalendar fraDato;
    private GregorianCalendar tilDato;
    private boolean innsjekket;
    
    //Konstruktøren oppretter objekt med inn- og utsjekkingsdato og setter innsjekket lik false.
    public Reservasjon(GregorianCalendar fra, GregorianCalendar til){
        fraDato=fra;
        tilDato=til;
        innsjekket=false;
    }
    
    public boolean getInnsjekket(){ //Returnerer om reservasjonen er innsjekket eller ikke.
        return innsjekket;
    }
    
    public GregorianCalendar getFraDato(){ //Returnerer innsjekkingsdato.
        return fraDato;
    }
    
    public GregorianCalendar getTilDato(){ //Returnerer utsjekkingsdato.
        return tilDato;
    }
    
    //Setter boolean innsjekket lik mottatt verdi hvis den ikke har lik verdi fra før av og returnerer om det gikk bra eller ikke.
    public boolean setInnsjekket(boolean i){ 
        if(innsjekket==i)
            return false;
        innsjekket=i;
        return true;
    }
    
    public void setFraDato(GregorianCalendar fra){ //Setter innsjekkingsdato lik mottatt parameter.
        fraDato=fra;
    }
    
    public void setTilDato(GregorianCalendar til){ //Setter utsjekkingsdato lik mottatt parameter.
        tilDato=til;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode() {
        String varstr = getFraDato().get(GregorianCalendar.DAY_OF_YEAR) + getTilDato().get(GregorianCalendar.DAY_OF_YEAR) + "";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public abstract boolean equals(Object o);
    
}//Slutt på klassen Reservasjon.

/*Klassen definerer PrivatReservasjon-objekter, som inneholder informasjon
 * om hvem som har reservert rommet når, og hvem som skal bo der.*/
class PrivatReservasjon extends Reservasjon{
    private static final long serialVersionUID = 550L;
    private long personNr;
    private Set<Person> gjester;
    
    /*Konstruktøren oppretter PrivatReservasjon-objekt med mottatt 
     * innsjekking- og utsjekkingsdato og kontaktpersonens personnummer, 
     * samt oppretter HashSet for rommets gjester.*/
    public PrivatReservasjon(GregorianCalendar fra, GregorianCalendar til, long p){
        super(fra, til);
        personNr=p;
        gjester=new HashSet<>();
    }
    
    public long getPersonNr(){ //Returnerer personnummeret til reservasjonens kontaktperson.
        return personNr;
    }
    
    public Set<Person> getGjester(){ //Returnerer HashSet med rommets gjester.
        return gjester;
    }
    
    //Prøver å sette inn gjest i reservasjonens HashSet og returnerer om det gikk bra eller ikke.
    public boolean settInnGjest(Person p){ 
        return gjester.add(p);
    }
    
    //Prøver å slette gjest fra reservasjonens HashSet og returnerer om det gikk bra eller ikke.
    public boolean slettGjest(long personNr){
        Person p=finnGjest(personNr);
        if(p!=null)
            return gjester.remove(p);
        return false;
    }
    
    //Returnerer gjest fra reservasjonens HashSet om det finnes en med personnummer lik mottatt parameter.
    public Person finnGjest(long personNr){
        Iterator<Person> iter=gjester.iterator();
        Person neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste.getPersonNr()==personNr)
                return neste;
        }
        return null;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = personNr + getFraDato().get(GregorianCalendar.DAY_OF_YEAR) + getTilDato().get(GregorianCalendar.DAY_OF_YEAR) + "";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke. Er like dersom kontaktpersonens
     * personnummer, fra- og tildato er like.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof PrivatReservasjon){
            PrivatReservasjon res=(PrivatReservasjon) o;
            return res.getPersonNr()==getPersonNr() &&
                res.getFraDato().get(GregorianCalendar.DAY_OF_YEAR)==getFraDato().get(GregorianCalendar.DAY_OF_YEAR) && 
                res.getTilDato().get(GregorianCalendar.DAY_OF_YEAR)==getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
        }
        return false;
    }
} //Slutt på klassen PrivatReservasjon

/*Klassen definerer FirmaReservasjon-objekter, som inneholder informasjon
 * om hvem som har reservert rommet når, og hvilke bestillinger som er gjort.*/
class FirmaReservasjon extends Reservasjon{
    private static final long serialVersionUID = 560L;
    private int orgNr;
    private Set<Bestilling> bestillinger;
    private final int KAPASITET=128;
    
    /*Konstruktøren oppretter FirmaReservasjon-objekt med mottatt 
     * innsjekking- og utsjekkingsdato og firmaets organisasjonsnummer, 
     * samt oppretter HashSet for reservasjonens mat- og drikkebestillinger.*/
    public FirmaReservasjon(GregorianCalendar fra, GregorianCalendar til, int o){
        super(fra, til);
        orgNr=o;
        bestillinger=new HashSet<>(KAPASITET);
    }
    
    public int getOrgNr(){ //Returnerer organisasjonsnummeret til firmaet som har reservert rommet.
        return orgNr;
    }
    
    //Prøver å sette inn bestilling i reservasjonens HashSet og returnerer om det gikk bra eller ikke.
    public boolean settInnBestilling(Bestilling b){
        return bestillinger.add(b);
    }
    
    //Prøver å slette bestilling fra reservasjonens HashSet og returnerer om det gikk bra eller ikke.
    public boolean slettBestilling(GregorianCalendar tid, int rom){
        Bestilling b=finnBestilling(tid, rom);
        if(b!=null)
            return bestillinger.remove(b);
        return false;
    }
    
    //Returnerer gjest fra reservasjonens HashSet om det finnes en med leveringstid og rom lik mottatte parametre.
    public Bestilling finnBestilling(GregorianCalendar tid, int rom){
        Iterator<Bestilling> iter=bestillinger.iterator();
        Bestilling neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste.getLeveringsTid().equals(tid) && neste.getLeveringsRom()==rom)
                return neste;
        }
        return null;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = orgNr + getFraDato().get(GregorianCalendar.DAY_OF_YEAR) + getTilDato().get(GregorianCalendar.DAY_OF_YEAR) + "";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke. Er like dersom firmaets
     * organisasjonsnummer, fra- og tildato er like.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof FirmaReservasjon){
            FirmaReservasjon res=(FirmaReservasjon) o;
            return res.getOrgNr()==getOrgNr() &&
                res.getFraDato().get(GregorianCalendar.DAY_OF_YEAR)==getFraDato().get(GregorianCalendar.DAY_OF_YEAR) && 
                res.getTilDato().get(GregorianCalendar.DAY_OF_YEAR)==getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
        }
        return false;
    }
} //Slutt på klassen FirmaReservasjon