/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 11.05.12
****Beskrivelse:
* Bookingregister.java inneholder klassen Bookingregister
*/

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Klassen definerer bookingregisteret som inneholder alle bookinger og metoder for å finne bookinger og lignende.
public class Bookingregister implements Serializable{
    private static final long serialVersionUID = 250L;
    Set<Booking> bookinger;
    private final int KAPASITET=1024;
    Romregister rregister;
    
    //Konstruktøren oppretter nytt bookingregister, og mottar romregister som parameter.
    public Bookingregister(Romregister r){
        rregister=r;
        bookinger=new HashSet<>(KAPASITET);
    }
    
    //Returnerer HashSet med alle kontaktpersoner med booking til mottatt dato.
    public Set<Kontaktperson> finnKontaktpersoner(GregorianCalendar til){
        Iterator<Booking> iter=bookinger.iterator();
        Set<Kontaktperson> kontaktpersoner=new HashSet<>();
        while(iter.hasNext()){
            Booking booking=iter.next();
            int bookingDag=booking.getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            int bookingAar=booking.getTilDato().get(GregorianCalendar.YEAR);
            int sjekkDag=til.get(GregorianCalendar.DAY_OF_YEAR);
            int sjekkAar=til.get(GregorianCalendar.YEAR);
            if(bookingDag==sjekkDag && bookingAar==sjekkAar){
                kontaktpersoner.add(booking.getKontaktperson());
            }
        }
        return kontaktpersoner;
    }
    
    /*Returnerer Booking, dersom det finnes, hvor kontaktpersonen har personnummer lik det mottatte og 
     * bookingen skal være innsjekket på mottatt dato.*/
    public Booking finnBooketNaa(GregorianCalendar naa, long personNr){
        Iterator<Booking> iter=bookinger.iterator();
        while(iter.hasNext()){
            Booking booking=iter.next();
            int tilDag=booking.getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            int tilAar=booking.getTilDato().get(GregorianCalendar.YEAR);
            int fraDag=booking.getFraDato().get(GregorianCalendar.DAY_OF_YEAR);
            int fraAar=booking.getTilDato().get(GregorianCalendar.YEAR);
            int naaDag=naa.get(GregorianCalendar.DAY_OF_YEAR);
            int naaAar=naa.get(GregorianCalendar.YEAR);
            if(booking.getKontaktperson().getPersonNr()==personNr && 
                tilDag>=naaDag && fraDag<=naaDag && (naaAar==tilAar || naaAar==fraAar))
                return booking;
        }
        return null;
    }
    
    //Returnerer HashSet med bookinger hvor kontaktpersonen har personnummer lik det mottatte.
    public Set<Booking> finnBooking(long personNr){
        Iterator<Booking> iter=bookinger.iterator();
        Set<Booking> privatBookinger=new HashSet<>();
        while(iter.hasNext()){
            Booking booking=iter.next();
            Gjest gjest=booking.getKontaktperson();
                if(gjest instanceof Kontaktperson){
                    Kontaktperson funnet=(Kontaktperson)gjest;
                    if(funnet.getPersonNr()==personNr)
                        privatBookinger.add(booking);
                }
        }
        return privatBookinger;
    }
    
    //Returnerer HashSet med firmabookinger hvor firmaet har organisasjonsnummer lik det mottatte.
    public Set<Booking> finnBooking(int orgNr){
        Iterator<Booking> iter=bookinger.iterator();
        Set<Booking> firmaBookinger=new HashSet<>();
        while(iter.hasNext()){
            Booking booking=iter.next();
            Gjest gjest=booking.getKontaktperson();
                if(gjest instanceof Firma){
                    Firma funnet=(Firma)gjest;
                    if(funnet.getOrgNr()==orgNr)
                        firmaBookinger.add(booking);
                }
        }
        return firmaBookinger;
    }
    
    //Returnerer booking dersom det finnes en som er lik mottat booking.
    public Booking finnBooking(Booking b){
        Iterator<Booking> iter=bookinger.iterator();
        while(iter.hasNext()){
            Booking funnet=iter.next();
            if(funnet.equals(b))
                return funnet;
        }
        return null;
    }
    
    //Forsøker å sette inn booking i registeret og returnerer om det gikk eller ikke.
    public boolean settInn(Booking b){
        return bookinger.add(b);
    }
    
    //Fjerner mottatt booking fra bookingregisteret og reservasjoner med samme fradato og personnummer.
    public boolean fjern(Booking b){
        GregorianCalendar fra=b.getFraDato();
        long pNr=b.getKontaktperson().getPersonNr();
        try{
            Set<Rom> romliste=rregister.finnAlleReserverteRom(fra, pNr);
            Iterator<Rom> iter=romliste.iterator();
            while(iter.hasNext()){
                Rom funnet=iter.next();
                PrivatReservasjon reservasjon=funnet.getKontaktReservasjon(fra, pNr);
                boolean ok=funnet.fjernReservasjon(reservasjon); //Lagring av reservasjon til eventuelt register må gjøres før dette.
                if(!ok)                  
                    return false;
            }   
        }
        catch(NullPointerException n){}
        return bookinger.remove(b);
    }
    
    //Kontrollerer om mottatte datoer er godkjente verdier.
    public static String datoKontroll(GregorianCalendar fra, GregorianCalendar til){
        StringBuilder bygger=new StringBuilder();
        GregorianCalendar naa=new GregorianCalendar();
        GregorianCalendar ettAar=new GregorianCalendar();
        ettAar.add(GregorianCalendar.YEAR, 1); //1 år frem i tid
        int fraDato=fra.get(GregorianCalendar.DAY_OF_YEAR);
        int tilDato=til.get(GregorianCalendar.DAY_OF_YEAR);
        int iDag=naa.get(GregorianCalendar.DAY_OF_YEAR);
        int aar=ettAar.get(GregorianCalendar.DAY_OF_YEAR);

        if(til.get(GregorianCalendar.YEAR)==(fra.get(GregorianCalendar.YEAR)) && tilDato<=fraDato)
            bygger.append("Ønsket utsjekking må være seinere enn ønsket innsjekking!\n");
        if(fra.get(GregorianCalendar.YEAR)<naa.get(GregorianCalendar.YEAR) ||
            (naa.get(GregorianCalendar.YEAR)==(fra.get(GregorianCalendar.YEAR)) && fraDato<iDag))
            bygger.append("Ønsket innsjekking er dato som har vært!\n");
        if(til.get(GregorianCalendar.YEAR)>ettAar.get(GregorianCalendar.YEAR) ||
            (til.get(GregorianCalendar.YEAR)==ettAar.get(GregorianCalendar.YEAR) && tilDato>aar))
            bygger.append("Ønsket utsjekking kan senest være om ett år!\n");
        return bygger.toString();
    }
} //Slutt på klasse Bookingregister
