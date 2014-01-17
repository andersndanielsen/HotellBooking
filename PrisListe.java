/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 08.05.12
****Beskrivelse:
* PrisListe.java inneholder klassen PrisListe.
*/

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Klassen inneholder priser per natt for rommene og en liste med høytider.
public class PrisListe implements Serializable{
    private static final long serialVersionUID = 450L;
    private int enkeltromPrisPrNatt=799;
    private int dobbeltromPrisPrNatt=1399;
    private int familieromPrisPrNatt=1999;
    private int suitePrisPrNatt=5999;
    private int helPensjonPrisPrDag=240;
    private int halvPensjonPrisPrDag=100;
    public static final double HOYTIDSPAASLAG=1.25;
    private Set<Hoytid> hoytider;
    
    public PrisListe(){ //Konstruktøren kaller på metode som oppretter høytider.
        hoytider=new HashSet<>();
        opprettHoytider();
    }
    
    public int getHelPensjonPrisPrDag(){ //Returnerer pris for helpensjon per dag.
        return helPensjonPrisPrDag;
    }
    
    public int getHalvPensjonPrisPrDag(){ //Returnerer pris for halvpensjon per dag.
        return halvPensjonPrisPrDag;
    }
    
    public int getEnkeltromPrisPrNatt(){ //Returnerer pris for enkeltrom per natt.
        return enkeltromPrisPrNatt;
    }
    
    public int getDobbeltromPrisPrNatt(){ //Returnerer pris for dobbeltrom per natt.
        return dobbeltromPrisPrNatt;
    }
    
    public int getFamilieromPrisPrNatt(){ //Returnerer pris for familierom per natt.
        return familieromPrisPrNatt;
    }
    
    public int getSuitePrisPrNatt(){ //Returnerer pris for suite per natt.
        return suitePrisPrNatt;
    }
    
    public void setHelPensjonPrisPrDag(int hel){ //Setter helpensjonsprisen lik mottatt pris.
        helPensjonPrisPrDag=hel;
    }
    
    public void setHalvPensjonPrisPrDag(int halv){ //Setter halvpensjonsprisen lik mottatt pris.
        halvPensjonPrisPrDag=halv;
    }
    
    public void setEnkeltromPrisPrNatt(int epn){ //Setter prisen for enkeltrom lik mottatt pris.
        enkeltromPrisPrNatt=epn;
    }
    
    public void setDobbeltromPrisPrNatt(int dpn){ //Setter prisen for dobbeltrom lik mottatt pris.
        dobbeltromPrisPrNatt=dpn;
    }
    
    public void setFamilieromPrisPrNatt(int fpn){ //Setter prisen for familierom lik mottatt pris.
        familieromPrisPrNatt=fpn;
    }
    
    public void setSuitePrisPrNatt(int spn){ //Setter prisen for suite lik mottatt pris.
        suitePrisPrNatt=spn;
    }
    
    // Finner antall eventuelle høytidsdager i perioden mellom mottatte parametre.
    public int finnAntallHoytidsdager(GregorianCalendar fra, GregorianCalendar til){
        if(hoytider.isEmpty())
            return 0;
        int antHoytidsdager=0;
        Iterator<Hoytid> iter=hoytider.iterator();
        int fraDag=fra.get(GregorianCalendar.DAY_OF_YEAR);
        int tilDag=til.get(GregorianCalendar.DAY_OF_YEAR);
        while(iter.hasNext()){
            Hoytid funnet=iter.next();
            int hoytidsFraDag=funnet.getFraDato().get(GregorianCalendar.DAY_OF_YEAR);
            int hoytidsTilDag=funnet.getTilDato().get(GregorianCalendar.DAY_OF_YEAR);
            if(fraDag<hoytidsFraDag && tilDag>hoytidsTilDag)
                antHoytidsdager=(hoytidsTilDag-hoytidsFraDag);
            else if(fraDag>hoytidsFraDag && tilDag<hoytidsTilDag)
                antHoytidsdager=(tilDag-fraDag);
            else if(fraDag<hoytidsFraDag && tilDag>hoytidsFraDag && tilDag<hoytidsTilDag)
                antHoytidsdager=(tilDag-hoytidsFraDag);
            else if(fraDag>hoytidsFraDag && fraDag<hoytidsTilDag && tilDag>hoytidsTilDag)
                antHoytidsdager=(hoytidsTilDag-fraDag);            
        }
        return antHoytidsdager;
    }
    
    private void opprettHoytider(){ //Oppretter høytider. Dette ville normalt skjedd via administratorvindu.
        GregorianCalendar fra=new GregorianCalendar(2012,5,15);
        GregorianCalendar til=new GregorianCalendar(2012,7,15);
        String tittel="Sommerferie";
        leggTilHoytid(fra,til,tittel);
        fra=new GregorianCalendar(2012,9,20);
        til=new GregorianCalendar(2012,9,27);
        tittel="Høstferie";
        leggTilHoytid(fra,til,tittel);
    }
    
    //Prøver å legge til høytid i HashSetet hoytider og returnerer om det gikk bra eller ikke.
    public boolean leggTilHoytid(GregorianCalendar fra, GregorianCalendar til, String n){
        return hoytider.add(new Hoytid(fra, til, n));
    }
    
    //Prøver å slette høytid fra HasSetet hoytider og returnerer om det gikk bra eller ikke.
    public boolean slettHoytid(String n){
        if(hoytider.isEmpty())
            return false;
        Iterator<Hoytid> iter=hoytider.iterator();
        while(iter.hasNext()){
            Hoytid funnet=iter.next();
            if(funnet.getNavn().equals(n)){
                return hoytider.remove(funnet);
            }
        }
        return false;
    }
} //Slutt på klasse PrisListe