/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 13.05.12
****Beskrivelse:
* Booking.java inneholder klassen Booking.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;

/*Klassen definerer Booking-objekter som inneholder informasjon om hvem som har 
 * booket hva, når og om det er betalt for eller ikke.*/
public class Booking implements Serializable{
    private static final long serialVersionUID = 200L;
    private int antGjester;
    private int antEnerom;
    private int antDobbeltrom;
    private int antFamilierom;
    private int antSuite;
    private int antHelpensjon;
    private int antHalvpensjon;
    private GregorianCalendar registrert;
    private GregorianCalendar fraDato;
    private GregorianCalendar tilDato;    
    private String onsker;
    private Kontaktperson kontaktperson;
    private boolean betalt;
    
    /*Konstruktør som oppretter objekt av typen Booking. 
     * Parametrene angir antall gjester, antall av de forskjellige romtypene, 
     * inn- og utsjekkingsdato, antall hel-  og halvpensjon, spesielle ønsker
     * og kontaktperson. Datoen for registrering av bookingen settes til dagens dato.*/
    public Booking(int antG, int antE, int antD, int antF, int antS, 
        GregorianCalendar fra, GregorianCalendar til, int antHel, int antHalv, String o, Kontaktperson k){
        registrert=new GregorianCalendar();
        antGjester=antG;
        antEnerom=antE;
        antDobbeltrom=antD;
        antFamilierom=antF;
        antSuite=antS;
        fraDato=fra;
        tilDato=til;
        antHelpensjon=antHel;
        antHalvpensjon=antHalv;
        onsker=o;
        kontaktperson=k;
        betalt=false;
    }
    
    public int getAntGjester(){ //Returnerer antall gjester bookingen omfatter.
        return antGjester;
    }
    
    public int getAntEnerom(){ //Returnerer antall enerom bookingen omfatter.
        return antEnerom;
    }
    
    public int getAntDobbeltrom(){ //Returnerer antall dobbeltrom bookingen omfatter.
        return antDobbeltrom;
    }
    
    public int getAntFamilierom(){ //Returnerer antall familierom bookingen omfatter.
        return antFamilierom;
    }
    
    public int getAntSuite(){ //Returnerer antall suiter bookingen omfatter.
        return antSuite;
    }
    
    public int getAntHelpensjon(){ //Returnerer antall helpensjoner bookingen omfatter.
        return antHelpensjon;
    }
    
    public int getAntHalvpensjon(){ //Returnerer antall halvpensjoner bookingen omfatter.
        return antHalvpensjon;
    }
    
    public String getOnsker(){ //Returnerer spesielle ønsker registrert på bookingen.
        return onsker;
    }
    
    public Kontaktperson getKontaktperson(){ //Returnerer bookingens kontaktperson.
        return kontaktperson;
    }
    
    public GregorianCalendar getFraDato(){ //Returnerer innsjekkingsdato.
        return fraDato;
    }
    
    public GregorianCalendar getTilDato(){ //Returnerer utsjekkingsdato.
        return tilDato;
    }
    
    public boolean getBetalt(){ //Returnerer om bookingen er betalt for eller ikke.
        return betalt;
    }
    
    public void setBetalt(boolean i){ //Setter bookingen som betalt eller ikke.
        betalt=i;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = fraDato.get(GregorianCalendar.DAY_OF_YEAR)+fraDato.get(GregorianCalendar.YEAR)+kontaktperson.getPersonNr()+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Booking){
            Booking b=(Booking) o;
            int fraDag=getFraDato().get(GregorianCalendar.DAY_OF_YEAR);
            int tilDag=getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            int fraFunnet=b.getFraDato().get(GregorianCalendar.DAY_OF_YEAR);
            int tilFunnet=b.getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            return ((fraDag>=fraFunnet && fraDag<tilFunnet) ||
                (tilDag>fraFunnet && tilDag<=tilFunnet) ||
                (fraDag<=fraFunnet && tilDag>=tilFunnet)) &&
                b.getKontaktperson().equals(kontaktperson);
            
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om bookingen
    @Override
    public String toString(){
        int regMndTall=registrert.get(GregorianCalendar.MONTH)+1;
        String regMnd=regMndTall+"";
        if(regMnd.length()==1)
            regMnd="0"+regMnd;
        int fraMndTall=fraDato.get(GregorianCalendar.MONTH)+1;
        String fraMnd=fraMndTall+"";
        if(fraMnd.length()==1)
            fraMnd="0"+fraMnd;
        int tilMndTall=tilDato.get(GregorianCalendar.MONTH)+1;
        String tilMnd=tilMndTall+"";
        if(tilMnd.length()==1)
            tilMnd="0"+tilMnd;
        
        StringBuilder bygger=new StringBuilder();
        bygger.append("Navn: ").append(kontaktperson.getNavn());
        bygger.append("\nPersonnummer: ").append(kontaktperson.getPersonNr());
        bygger.append("\nBooking registrert: ").append(registrert.get(GregorianCalendar.YEAR));
        bygger.append("-").append(regMnd);
        bygger.append("-").append(registrert.get(GregorianCalendar.DATE));
        bygger.append("\nØnsket innsjekking: ").append(fraDato.get(GregorianCalendar.YEAR));
        bygger.append("-").append(fraMnd);
        bygger.append("-").append(fraDato.get(GregorianCalendar.DATE));
        bygger.append("\nØnsket utsjekking: ").append(tilDato.get(GregorianCalendar.YEAR));
        bygger.append("-").append(tilMnd);
        bygger.append("-").append(tilDato.get(GregorianCalendar.DATE));
        bygger.append("\nAntall gjester: ").append(antGjester);
        bygger.append("\nAntall halvpensjon: ").append(antHalvpensjon);
        bygger.append("\nAntall helpensjon: ").append(antHelpensjon);
        bygger.append("\nAntall enerom: ").append(antEnerom);
        bygger.append("\nAntall dobbeltrom: ").append(antDobbeltrom);
        bygger.append("\nAntall familierom: ").append(antFamilierom);
        bygger.append("\nAntall suiter: ").append(antSuite);
        bygger.append("\nSpesielle ønsker: ").append(onsker);
        return bygger.toString();
    }
} //Slutt på klasse Booking
