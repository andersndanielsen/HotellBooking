/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 09.05.12
****Beskrivelse:
* Rom.java inneholder klassen Rom og subklassen Seminarrom
*/

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*Klassen definerer Rom-objekter som inneholder informasjon om romnummer,
 * maksimalt antall personer og HashSet med alle reservasjoner som tilhører rommet.*/
public class Rom implements Serializable{
    private static final long serialVersionUID = 600L;
    private final int KAPASITET=512;
    private Set<Reservasjon> reservasjon;
    private int romNr;
    private int maxPersoner;
    
    /*Konstruktøren oppretter Rom-objekt. Parametrene brukes til å sette romnummer
     * og maksimalt antall personer. Samtidig opprettes HashSetet som skal inneholde reservasjonene.*/
    public Rom(int nr, int max){
        reservasjon = new HashSet<>(KAPASITET);
        romNr=nr;
        maxPersoner=max;
    }
    
    public int getRomNr(){ //Returnerer romnummeret.
        return romNr;
    }
    
    public int getMaxPersoner(){ //Returnerer maksimalt antall personer som kan bo på rommet.
        return maxPersoner;
    }
    //Prøver å slette mottatt reservasjon fra rommets HashSet og returnerer om det gikk bra eller ikke.
    public boolean fjernReservasjon(Reservasjon r){
        return reservasjon.remove(r);
    }
    
    //Returnerer om rommet er ledig eller ikke i tidsperioden angitt av mottatte parametre.
    public boolean erLedig(GregorianCalendar fra, GregorianCalendar til){
        if(reservasjon.isEmpty())
            return true;
        Iterator<Reservasjon> iter=reservasjon.iterator();
        int fraDag=fra.get(GregorianCalendar.DAY_OF_YEAR);
        int tilDag=til.get(GregorianCalendar.DAY_OF_YEAR);
        int fraFunnet;
        int tilFunnet;
        boolean ledig=true;
        while(iter.hasNext()){
            Reservasjon funnet=iter.next();            
            fraFunnet=funnet.getFraDato().get(GregorianCalendar.DAY_OF_YEAR);
            tilFunnet=funnet.getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            if((fraDag>=fraFunnet && fraDag<tilFunnet) ||
                (tilDag>fraFunnet && tilDag<=tilFunnet) ||
                (fraDag<=fraFunnet && tilDag>=tilFunnet))
                ledig=false;
        }
        return ledig;      
    }
    
    /*Finner og returnerer privat reservasjon med angitt fradato og personnummer. 
     * Denne metoden søker etter personnumre i gjestelista i hver reservasjon.*/
    public PrivatReservasjon getReservasjon(GregorianCalendar fra, long personNr){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        Reservasjon neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste instanceof PrivatReservasjon){
                PrivatReservasjon funnet=(PrivatReservasjon) neste;
                GregorianCalendar fraDato=funnet.getFraDato();
                if(funnet != null && funnet.finnGjest(personNr)!=null &&
                    fraDato.get(GregorianCalendar.DAY_OF_YEAR)==fra.get(GregorianCalendar.DAY_OF_YEAR))
                    return funnet;
            }
        }        
        return null;
    }
    
    /*Finner og returnerer privat reservasjon med angitt fradato og personnummer. 
     * Denne metoden søker etter kontaktpersonens personnummer i hver reservasjon.*/ 
    public PrivatReservasjon getKontaktReservasjon(GregorianCalendar fra, long personNr){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        while(iter.hasNext()){
            Reservasjon neste=iter.next();
            if(neste instanceof PrivatReservasjon){
                PrivatReservasjon funnet=(PrivatReservasjon) neste;
                GregorianCalendar fraDato=funnet.getFraDato();
                if(funnet.getPersonNr()==personNr &&
                    fraDato.get(GregorianCalendar.DAY_OF_YEAR)==fra.get(GregorianCalendar.DAY_OF_YEAR)
                    && fraDato.get(GregorianCalendar.YEAR)==fra.get(GregorianCalendar.YEAR))
                    return funnet;
            }
        }
        return null;
    }
    
    /*Finner og returnerer privat reservasjon med angitt personnummer som er innsjekket. 
     * Denne metoden søker etter personnumre i gjestelista i hver reservasjon.*/
    public PrivatReservasjon getInnsjekketReservasjon(long personNr){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        Reservasjon neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste instanceof PrivatReservasjon){
                PrivatReservasjon funnet=(PrivatReservasjon) neste;
                if(funnet != null && funnet.finnGjest(personNr)!=null && funnet.getInnsjekket())
                    return funnet;
            }
        }
        return null;
    }
    
    /*Finner og returnerer privat reservasjon med angitt personnummer som er innsjekket. 
     * Denne metoden søker etter kontaktpersonens personnummer i hver reservasjon.*/ 
    public PrivatReservasjon getInnsjekketKontaktReservasjon(long personNr){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        Reservasjon neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste instanceof PrivatReservasjon){
                PrivatReservasjon funnet=(PrivatReservasjon) neste;
                if(funnet.getInnsjekket() && funnet.getPersonNr()==personNr)
                    return funnet;
            }
        }
        return null;
    }
    
    //Finner og returnerer innsjekket reservasjon uansett person
    public PrivatReservasjon getInnsjekketReservasjon(){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        Reservasjon neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste instanceof PrivatReservasjon){
                PrivatReservasjon funnet=(PrivatReservasjon) neste;
                if(funnet.getInnsjekket())
                    return funnet;
            }
        }
        return null;
    }
    
    //Finner og returnerer firmareservasjon med angitt fradato og organisasjonsnummer.
    public Reservasjon getReservasjon(GregorianCalendar fra, int orgNr){
        Iterator<Reservasjon> iter=reservasjon.iterator();
        Reservasjon neste;
        while(iter.hasNext()){
            neste=iter.next();
            if(neste instanceof FirmaReservasjon){
                FirmaReservasjon funnet=(FirmaReservasjon) neste;
                GregorianCalendar fraDato=funnet.getFraDato();
                if(funnet.getOrgNr()==orgNr &&
                    fraDato.get(GregorianCalendar.DAY_OF_YEAR)==fra.get(GregorianCalendar.DAY_OF_YEAR)
                    && fraDato.get(GregorianCalendar.YEAR)==fra.get(GregorianCalendar.YEAR))
                    return neste;
            }
        }
        return null;
    }
    
    /*Prøver å opprette og legge til privat reservasjon med mottatt fra- og tildato og 
     * personnummer i HashSetet og returnerer om det gikk bra eller ikke.*/
    public boolean opprettPrivatReservasjon(GregorianCalendar fra, GregorianCalendar til, long p){
        PrivatReservasjon res=new PrivatReservasjon(fra, til, p);
        return reservasjon.add(res);
    }
    
    /*Prøver å opprette og legge til firmareservasjon med mottatt fra- og tildato og 
     * organisasjonsnummer i HashSetet og returnerer om det gikk bra eller ikke.*/
    public boolean opprettFirmaReservasjon(GregorianCalendar fra, GregorianCalendar til, int o){
        FirmaReservasjon res=new FirmaReservasjon(fra, til, o);
        return reservasjon.add(res);
    }
    
    /*Returnerer om mottatt rom er likt som dette rommet eller ikke
     * ved å kontrollerer om romnumrene er like.*/
    public boolean equals(Rom r){
        return r.getRomNr()==romNr;
    }
    
} //Slutt på klassen Rom.

//Klassen definerer Seminarrom-objekter, med informasjon som sier hva rommet inneholder.
class Seminarrom extends Rom{
    private static final long serialVersionUID = 650L;
    private boolean harTV;
    private boolean harMusikkanlegg;
    private boolean harProjektor;

    /*Konstruktøren oppretter Seminarrom-objekt, og bruker parametrene til 
     * å sette om rommet inneholder tv, musikkanlegg og projektor eller ikke.*/
    public Seminarrom(int nr, int max, boolean tv, boolean musikk, boolean projektor){
        super(nr, max);
        harTV=tv;
        harMusikkanlegg=musikk;
        harProjektor=projektor;
    }
    
    public boolean getHarTv(){ //Returnerer om rommet har tv eller ikke.
        return harTV;
    }
    
    public boolean getHarMusikkannlegg(){ //Returnerer om rommet har musikkanlegg eller ikke.
        return harMusikkanlegg;
    }
    
    public boolean getHarProjektor(){ //Returnerer om rommet har projektor eller ikke.
        return harProjektor;
    }
} //Slutt på klassen Seminarrom