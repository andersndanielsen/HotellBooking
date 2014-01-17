/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 05.05.12
****Beskrivelse:
* Gjesteregister.java inneholder klassen Gjesteregister.
*/

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*Klassen definerer Gjesteregister-objekter som inneholder et hashset med alle hotellets gjester, og et hashset med ansatte.
 * I tillegg inneholder klassen forskjellige metoder som søker, setter inn og sletter objekter i hashsetene.*/
public class Gjesteregister implements Serializable{
    private static final long serialVersionUID = 380L;
    private final int KAPASITET=1024;
    private Set<Gjest> gjester;
    private Set<Ansatt> ansatte;
    
    /*Konstruktør som oppretter objekt av typen Gjesteregister.
     * Hashsetene blir opprettet med størrelse lik KAPASITET, og metoden opprettAnsatte() blir kalt opp.*/
    public Gjesteregister(){
        gjester=new HashSet<>(KAPASITET);
        ansatte=new HashSet<>(KAPASITET);
        opprettAnsatte();
    }
    
    //Legger inn en ansatt i ansattlisten. Hardkodes her som eksempel, vil vanligvis gjøres via administratorvindu.
    private void opprettAnsatte(){
        Ansatt admin=new Ansatt("Admin", 12345678901L, "Admin1");
        ansatte.add(admin);
    }
    
    //Kontrollerer om inntastede data ved innlogging er riktig. Returnerer true hvis godkjent, og false om ikke.
    public boolean innlogging(long pNr, String pass){
        Iterator iter=ansatte.iterator();
        while(iter.hasNext()){
            Ansatt neste=(Ansatt)iter.next();
            if(neste.getPersonNr()==(pNr) && neste.getPassordHash()==pass.hashCode())
                return true;
        }
        return false;
    }
    
    //Søker i gjestelista etter gjest med navn lik parameter s. Returnerer en hashset med gjester eller null om ingen treff.
    public Set<Gjest> finnGjest(String s){
        Set<Gjest> funnet=new HashSet<>();
        Iterator<Gjest> iter=gjester.iterator();
        while(iter.hasNext()){
            Gjest neste=iter.next();
            if(neste.getNavn().equals(s))
                funnet.add(neste); 
        }
        return funnet;
    }
    
    /*Søker i gjestelista etter gjest med personnummer lik parameter personNr. Returnerer funnet gjest eller null om 
     * ingen treff.*/
    public Gjest finnGjest(long personNr){
        Iterator<Gjest> iter=gjester.iterator();
        while(iter.hasNext()){
            Gjest neste=iter.next();
            if(neste instanceof Person){
                Person funn=(Person)neste;
                if(funn.getPersonNr()==personNr){
                    return funn;
                }
            } 
        }
        return null;
    }
    
    /*Søker i gjestelista etter firma med organisasjonsnummer lik parameter orgNr. Returnerer funnet firma eller null om 
     * ingen treff.*/
    public Gjest finnGjest(int orgNr){
        Iterator<Gjest> iter=gjester.iterator();
        while(iter.hasNext()){
            Gjest neste=iter.next();
            if(neste instanceof Firma){
                Firma funn=(Firma)neste;
                if(funn.getOrgNr()==orgNr){
                    return funn;
                }
            } 
        }
        return null;
    }
    
    //Setter inn gjest angitt med parameter p inn i hashset gjester, og returnerer true om innsettingen var vellykket.
    public boolean settInn(Gjest p){
        return gjester.add(p);
    }
    
    //Sletter gjest angitt med parameter p fra hashset gjester, og returnerer true om slettingen var vellykket.
    public boolean slettGjest(Gjest p){
        return gjester.remove(p);
    }
} //Slutt på klassen Gjesteregister
