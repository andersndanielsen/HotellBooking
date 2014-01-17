/* Laget av
****Anders Nødland Danielsen, s180475
****Magnus Jårem Moltzau, s180473
****siste oppdatering: 08.05.12
****Beskrivelse:
* Romregister.java inneholder klassen Romregister
*/

import java.io.Serializable;
import java.util.*;

/*Klassen definerer Romregister-objekter som inneholder flere hashset med alle hotellets rom fordelt etter romtype.
 * Når programmet startes opp første gang opprettes alle etasjene.
 * I tillegg inneholder klassen forskjellige metoder som søker, setter inn og sletter objekter i hashsetene.*/
public class Romregister implements Serializable{
    private static final long serialVersionUID = 660L;
    private Set<Rom> enerom;
    private Set<Rom> dobbeltrom;
    private Set<Rom> familierom;
    private Set<Rom> suite;
    private Set<Rom> seminarrom;
    public static final int MAXPERSONERENEROM=1;
    public static final int MAXPERSONERDOBBELTROM=2;
    public static final int MAXPERSONERFAMILIEROM=4;
    public static final int MAXPERSONERSUITE=7;
    public static final int MAXPERSONERGRUPPEROM=8;
    public static final int MAXPERSONERMOTEROM=40;
    public static final int MAXPERSONERAUD1=80;
    public static final int MAXPERSONERAUD2=120;
    public static final int MAXPERSONERAUD3=200;
    private final int KAPASITET1=64;
    private final int KAPASITET2=128;
    private final int ROMPRETG=30;
    private final int ROM1ETG=18;
    private int antSattInn=0;
    Random generator=new Random();
    
    /*Konstruktør som oppretter objekt av typen Romregister.
     * Hashsetene blir opprettet med froskjellig kapasitet, og metodene som oppretter etasjene blir kalt opp.*/
    public Romregister(){
        enerom=new HashSet<>(KAPASITET1);
        dobbeltrom=new HashSet<>(KAPASITET2);
        familierom=new HashSet<>(KAPASITET1);
        suite=new HashSet<>();
        seminarrom=new HashSet<>(KAPASITET1);
        opprett1etg();
        opprett2etg();
        opprett3etg();
        opprett4etg();
    }
    
    //Oppretter hotellets 1. etasje, og gir rommene et tilfeldig romnummer mellom 101 og 118
    private void opprett1etg(){
        final int STARTNR1ETG=101;
        final int ANTGRUPPEROM1ETG=10;
        final int ANTMOTEROM1ETG=5;
        final int ANTAUD1ETG=3;
        
        antSattInn=0;
        while(antSattInn<ANTGRUPPEROM1ETG){
            int romNr=STARTNR1ETG+generator.nextInt(ROM1ETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERGRUPPEROM,true,true,true));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTMOTEROM1ETG){
            int romNr=STARTNR1ETG+generator.nextInt(ROM1ETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERMOTEROM,true,true,true));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTAUD1ETG){
            int romNr=STARTNR1ETG+generator.nextInt(ROM1ETG);
            boolean sattInn=false;
            while(!sattInn){
                if(antSattInn%ANTAUD1ETG==0)
                    sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERAUD1,true,true,true));
                if(antSattInn%ANTAUD1ETG==1)
                    sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERAUD2,true,true,true));
                if(antSattInn%ANTAUD1ETG==2)
                    sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERAUD3,true,true,true));
            }
            antSattInn++;
        }
    }
    
    //Oppretter hotellets 2. etasje, og gir rommene et tilfeldig romnummer mellom 201 og 231
    private void opprett2etg(){
        final int STARTNR2ETG=201;
        final int ANTENKELROM2ETG=5;
        final int ANTDOBBELTROM2ETG=15;
        final int ANTFAMILIEROM2ETG=5;
        final int ANTGRUPPEROM2ETG=5;
        
        antSattInn=0;        
        while(antSattInn<ANTENKELROM2ETG){
            int romNr=STARTNR2ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=enerom.add(new Rom(romNr,MAXPERSONERENEROM));
            antSattInn++;
        }
        antSattInn=0;
        while(antSattInn<ANTDOBBELTROM2ETG){
            int romNr=STARTNR2ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=dobbeltrom.add(new Rom(romNr,MAXPERSONERDOBBELTROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTFAMILIEROM2ETG){
            int romNr=STARTNR2ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=familierom.add(new Rom(romNr,MAXPERSONERFAMILIEROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTGRUPPEROM2ETG){
            int romNr=STARTNR2ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=seminarrom.add(new Seminarrom(romNr,MAXPERSONERGRUPPEROM,true,true,true));
            antSattInn++;
        }
    }
    
    //Oppretter hotellets 3. etasje, og gir rommene et tilfeldig romnummer mellom 301 og 331
    private void opprett3etg(){
        final int STARTNR3ETG=301;
        final int ANTENKELROM3ETG=10;
        final int ANTDOBBELTROM3ETG=15;
        final int ANTFAMILIEROM3ETG=5;
        
        antSattInn=0;
        while(antSattInn<ANTENKELROM3ETG){
            int romNr=STARTNR3ETG+generator.nextInt(ROMPRETG);    
            boolean sattInn=false;
            while(!sattInn)
                sattInn=enerom.add(new Rom(romNr,MAXPERSONERENEROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTDOBBELTROM3ETG){
            int romNr=STARTNR3ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=dobbeltrom.add(new Rom(romNr,MAXPERSONERDOBBELTROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTFAMILIEROM3ETG){
            int romNr=STARTNR3ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=familierom.add(new Rom(romNr,MAXPERSONERFAMILIEROM));
            antSattInn++;
        }
    }
    
    //Oppretter hotellets 4. etasje, og gir rommene et tilfeldig romnummer mellom 401 og 431
    private void opprett4etg(){
        final int STARTNR4ETG=401;
        final int ANTENKELROM4ETG=5;
        final int ANTDOBBELTROM4ETG=10;
        final int ANTFAMILIEROM4ETG=10;
        final int ANTSUITE4ETG=5;
        
        antSattInn=0;        
        while(antSattInn<ANTENKELROM4ETG){
            int romNr=STARTNR4ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=enerom.add(new Rom(romNr,MAXPERSONERENEROM));
            antSattInn++;
        }
        antSattInn=0;
        while(antSattInn<ANTDOBBELTROM4ETG){
            int romNr=STARTNR4ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=dobbeltrom.add(new Rom(romNr,MAXPERSONERDOBBELTROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTFAMILIEROM4ETG){
            int romNr=STARTNR4ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=familierom.add(new Rom(romNr,MAXPERSONERFAMILIEROM));
            antSattInn++;
        }
        
        antSattInn=0;
        while(antSattInn<ANTSUITE4ETG){
            int romNr=STARTNR4ETG+generator.nextInt(ROMPRETG);
            boolean sattInn=false;
            while(!sattInn)
                sattInn=suite.add(new Rom(romNr,MAXPERSONERSUITE));
            antSattInn++;
        }
    }
    
    //Finner alle ledige enkeltrom i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeEnkeltrom(GregorianCalendar fra, GregorianCalendar til){
        Set<Rom> ledigeEnkeltRom=new HashSet<>();
        Iterator<Rom> iter=enerom.iterator();
        while(iter.hasNext()){
            Rom funnet=iter.next();
            if(funnet.erLedig(fra,til))
                ledigeEnkeltRom.add(funnet);
        }
        return ledigeEnkeltRom;
    }
    
    //Finner alle ledige dobbeltrom i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeDobbeltrom(GregorianCalendar fra, GregorianCalendar til){
        Set<Rom> ledigeDobbeltRom=new HashSet<>();
        Iterator<Rom> iter=dobbeltrom.iterator();
        while(iter.hasNext()){
            Rom funnet=iter.next();
            if(funnet.erLedig(fra,til))
                ledigeDobbeltRom.add(funnet);
        }
        return ledigeDobbeltRom;
    }
    
    //Finner alle ledige familierom i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeFamilierom(GregorianCalendar fra, GregorianCalendar til){
        Set<Rom> ledigeFamilieRom=new HashSet<>();
        Iterator<Rom> iter=familierom.iterator();
        while(iter.hasNext()){
            Rom funnet=iter.next();
            if(funnet.erLedig(fra,til))
                ledigeFamilieRom.add(funnet);
        }
        return ledigeFamilieRom;
    }
    
    //Finner alle ledige suiter i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeSuiter(GregorianCalendar fra, GregorianCalendar til){
        Set<Rom> ledigeSuiter=new HashSet<>();
        Iterator<Rom> iter=suite.iterator();
        while(iter.hasNext()){
            Rom funnet=iter.next();
            if(funnet.erLedig(fra,til))
                ledigeSuiter.add(funnet);
        }
        return ledigeSuiter;
    }
    
    //Finner alle ledige grupperom i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeGrupperom(GregorianCalendar fra, GregorianCalendar til){
        return finnLedigeSeminarrom(fra, til, MAXPERSONERGRUPPEROM);
    }
    
    //Finner alle ledige møterom i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeMoterom(GregorianCalendar fra, GregorianCalendar til){
        return finnLedigeSeminarrom(fra, til, MAXPERSONERMOTEROM);
    }
    
    //Finner alle ledige auditorier av str 1 i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeAuditorier1(GregorianCalendar fra, GregorianCalendar til){
        return finnLedigeSeminarrom(fra, til, MAXPERSONERAUD1);
    }
    
    //Finner alle ledige auditorier av str 2 i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeAuditorier2(GregorianCalendar fra, GregorianCalendar til){
        return finnLedigeSeminarrom(fra, til, MAXPERSONERAUD2);
    }
    
    //Finner alle ledige auditorier av str 3 i oppgitt periode angitt av parametrene fra og til, og returnerer et hashset.
    public Set<Rom> finnLedigeAuditorier3(GregorianCalendar fra, GregorianCalendar til){
        return finnLedigeSeminarrom(fra, til, MAXPERSONERAUD3);
    }
    
    /*Finner alle ledige seminarrom i oppgitt periode angitt av parametrene fra og til med str angitt av parametren max
     * og returnerer et hashset.*/
    public Set<Rom> finnLedigeSeminarrom(GregorianCalendar fra, GregorianCalendar til, int max){
        Set<Rom> ledigeSeminarrom=new HashSet<>();
        Iterator<Rom> iter=seminarrom.iterator();
        while(iter.hasNext()){
            Rom funnet=iter.next();
            if(funnet.erLedig(fra,til) && funnet.getMaxPersoner()==max)
                ledigeSeminarrom.add(funnet);
        }
        return ledigeSeminarrom;
    }
    
    /*Returnerer alle reserverte Rom som er reservert fra dato angitt av parameteren 'fra', og som inneholder personnummeret 
     * personNr. Denne metoden søker kun på personNr i Reservasjon, altså kontaktpersonen, og returnerer et hashset*/
    public Set<Rom> finnAlleReserverteRom(GregorianCalendar fra, long personNr){ 
        Iterator<Rom> iter=enerom.iterator();
        Iterator<Rom> iter2=dobbeltrom.iterator();
        Iterator<Rom> iter3=familierom.iterator();
        Iterator<Rom> iter4=suite.iterator();
        Set<Rom> reserverteRom=new HashSet<>();
        while(iter.hasNext()){
            Rom reservert=iter.next();
            if(reservert.getKontaktReservasjon(fra, personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter2.hasNext()){
            Rom reservert=iter2.next();
            if(reservert.getKontaktReservasjon(fra, personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter3.hasNext()){
            Rom reservert=iter3.next();
            if(reservert.getKontaktReservasjon(fra, personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter4.hasNext()){
            Rom reservert=iter4.next();
            if(reservert.getKontaktReservasjon(fra, personNr)!=null)
                reserverteRom.add(reservert);
        }
        return reserverteRom;
    }
    
    // Denne metoden søker i gjestelista på hvert rom, og finner altså kun et Rom. Dette rommet returneres.
    public Rom finnReserverteRom(GregorianCalendar fra, long personNr){
        Iterator<Rom> iter=enerom.iterator();
        Iterator<Rom> iter2=dobbeltrom.iterator();
        Iterator<Rom> iter3=familierom.iterator();
        Iterator<Rom> iter4=suite.iterator();
        while(iter.hasNext()){
            Rom reservert=iter.next();
            if(reservert.getReservasjon(fra, personNr)!=null)
                return reservert;
        }
        while(iter2.hasNext()){
            Rom reservert=iter2.next();
            if(reservert.getReservasjon(fra, personNr)!=null)
                return reservert;
        }
        while(iter3.hasNext()){
            Rom reservert=iter3.next();
            if(reservert.getReservasjon(fra, personNr)!=null)
                return reservert;
        }
        while(iter4.hasNext()){
            Rom reservert=iter4.next();
            if(reservert.getReservasjon(fra, personNr)!=null)
                return reservert;
        }
        return null;
    }
    
    /*Finner alle innsjekkede rom som er reservert av person med personnummer lik parameteren personNr, og returnerer disse
    som et hashset*/
    public Set<Rom> finnAlleInnsjekkedeRom(long personNr){ 
        Iterator<Rom> iter=enerom.iterator();
        Iterator<Rom> iter2=dobbeltrom.iterator();
        Iterator<Rom> iter3=familierom.iterator();
        Iterator<Rom> iter4=suite.iterator();
        Set<Rom> reserverteRom=new HashSet<>();
        while(iter.hasNext()){
            Rom reservert=iter.next();
            if(reservert.getInnsjekketKontaktReservasjon(personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter2.hasNext()){
            Rom reservert=iter2.next();
            if(reservert.getInnsjekketKontaktReservasjon(personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter3.hasNext()){
            Rom reservert=iter3.next();
            if(reservert.getInnsjekketKontaktReservasjon(personNr)!=null)
                reserverteRom.add(reservert);
        }
        while(iter4.hasNext()){
            Rom reservert=iter4.next();
            if(reservert.getInnsjekketKontaktReservasjon(personNr)!=null)
                reserverteRom.add(reservert);
        }
        return reserverteRom;
    }
    
    //Finner alle rom som er innsjekket på hotellet, og returnerer disse som et hashset
    public Set<Rom> finnAlleInnsjekkedeRom(){ 
        Iterator<Rom> iter=enerom.iterator();
        Iterator<Rom> iter2=dobbeltrom.iterator();
        Iterator<Rom> iter3=familierom.iterator();
        Iterator<Rom> iter4=suite.iterator();
        Set<Rom> reserverteRom=new HashSet<>();
        while(iter.hasNext()){
            Rom reservert=iter.next();
            if(reservert.getInnsjekketReservasjon()!=null)
                reserverteRom.add(reservert);
        }
        while(iter2.hasNext()){
            Rom reservert=iter2.next();
            if(reservert.getInnsjekketReservasjon()!=null)
                reserverteRom.add(reservert);
        }
        while(iter3.hasNext()){
            Rom reservert=iter3.next();
            if(reservert.getInnsjekketReservasjon()!=null)
                reserverteRom.add(reservert);
        }
        while(iter4.hasNext()){
            Rom reservert=iter4.next();
            if(reservert.getInnsjekketReservasjon()!=null)
                reserverteRom.add(reservert);
        }
        return reserverteRom;
    }
    
    // Denne metoden søker etter et innsjekket rom ut i fra rommets gjesteliste, og returnerer altså kun et Rom.
    public Rom finnInnsjekketRom(long personNr){
        Iterator<Rom> iter=enerom.iterator();
        Iterator<Rom> iter2=dobbeltrom.iterator();
        Iterator<Rom> iter3=familierom.iterator();
        Iterator<Rom> iter4=suite.iterator();
        while(iter.hasNext()){
            Rom reservert=iter.next();
            if(reservert.getInnsjekketReservasjon(personNr)!=null)
                return reservert;
        }
        while(iter2.hasNext()){
            Rom reservert=iter2.next();
            if(reservert.getInnsjekketReservasjon(personNr)!=null)
                return reservert;
        }
        while(iter3.hasNext()){
            Rom reservert=iter3.next();
            if(reservert.getInnsjekketReservasjon(personNr)!=null)
                return reservert;
        }
        while(iter4.hasNext()){
            Rom reservert=iter4.next();
            if(reservert.getInnsjekketReservasjon(personNr)!=null)
                return reservert;
        }
        return null;
    }    
} //Slutt på klassen Romregister
