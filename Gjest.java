/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 07.05.12
****Beskrivelse:
* Gjest.java inneholder klassene Gjest, Person, Ansatt, Kontaktperson og Firma.
*/

import java.io.Serializable;

//Klassen definerer Gjest-objekter og inneholder navn.
public class Gjest implements Serializable{
    private static final long serialVersionUID = 300L;
    private String navn;
    
    //Konstruktør som oppretter objekt av typen Gjest, og setter navnet lik n i parameteren
    public Gjest(String n){
        navn=n;
    }
    
    public String getNavn(){ //Returnerer navnet til gjesten.
        return navn;
    }
    
    public void setNavn(String n){ //Setter navnet på gjesten lik n i parameteren.
        navn=n;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){ 
        String varstr = navn;
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Gjest){
            Gjest g=(Gjest) o;
            return navn.equals(g.navn);
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om gjesten
    @Override
    public String toString(){
        StringBuilder bygger=new StringBuilder();
        bygger.append("Navn: ").append(navn);
        return bygger.toString();
    }
} //Slutt på klassen Gjest

/*Klassen definerer Person-objekter som arver Gjest. Et Person-objekt er en gjest som kun er gjest på et rom, og ikke den
 * som utfører bookingen. Inneholder i tillegg personnummer.*/
class Person extends Gjest{
    private static final long serialVersionUID = 350L;
    private long personNr;
    
    /*Konstruktør som oppretter objekt av typen Person. 
     * Parameter n sendes videre til Gjest sin konstruktør. p er personens personnummer.*/
    public Person(String n, long p){
        super(n);
        personNr=p;
    }
    
    //Returnerer personens personnummer.
    public long getPersonNr(){
        return personNr;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = personNr+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Person){
            Person p=(Person) o;
            return personNr==p.getPersonNr();
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om personen
    @Override
    public String toString(){
        StringBuilder bygger=new StringBuilder();
        bygger.append(super.toString());
        bygger.append("\nPersonnummer: ").append(personNr);
        return bygger.toString();
    }
} //end og class Person

//Klassen definerer Ansatt-objekter som arver Person. I tillegg inneholder Ansatt et hashet passord til bruk for innlogging.
class Ansatt extends Person{
    private static final long serialVersionUID = 365L;
    private int passordHash;
    
    /*Konstruktør som oppretter objekt av typen Ansatt. 
     * Parametrene n og p sendes videre til Person sin konstruktør. pass er passordet til den ansatte.*/
    public Ansatt(String n, long p, String pass){
        super(n, p);
        passordHash=pass.hashCode();
    }
    
    //Returnerer Ansatt sitt passord som en hashkode.
    public int getPassordHash(){
        return passordHash;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = this.getPersonNr()+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Person){
            Person p=(Person) o;
            return this.getPersonNr()==p.getPersonNr();
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om den ansatte
    @Override
    public String toString(){
        return super.toString();
    }
} //Slutt på klassen Ansatt

/*Klassen definerer Kontaktperson-objekter og arver Person. I tillegg inneholder den adresse og telefonnummer.
 * Kontaktperson er gjesten som utfører bookingen.*/
class Kontaktperson extends Person{
    private static final long serialVersionUID = 370L;
    private String adresse;
    private int postNummer;
    private String postSted;
    private int tlfNr;
    
    /*Konstruktør som oppretter objekt av typen Kontaktperson. 
     * Parametrene name og pNr sendes videre til Person sin konstruktør. De resterende er adresse og telefonnummer.*/
    public Kontaktperson(String name, long pNr, String adr, int postNr, String pSted, int tlf){
        super(name, pNr);
        adresse=adr;
        postNummer=postNr;
        postSted=pSted;
        tlfNr=tlf;
    }
    
    //Returnerer gateadressen
    public String getAdresse(){
        return adresse;
    }
    
    //Returnerer postnummeret
    public int getPostNr(){
        return postNummer;
    }
    
    //Returnerer poststedet
    public String getPostSted(){
        return postSted;
    }
    
    //Returnerer telefonnummeret
    public int getTlfNr(){
        return tlfNr;
    }
    
    //Setter adressen lik parameter a
    public void setAdresse(String a){
        adresse=a;
    }
    
    //Setter postnummer lik parameter p
    public void setPostNr(int p){
        postNummer=p;
    }
    
    //Setter poststed lik parameter p
    public void setPostSted(String p){
        postSted=p;
    }
    
    //Setter telefonnummer lik t
    public void setTlf(int t){
        tlfNr=t;
    }
    
    //Oppdaterer kontaktpersonens personalia med parametrene
    public void oppdater(String n, String a, int pNr, String pSted, int tlf){
        setNavn(n);
        setAdresse(a);
        setPostNr(pNr);
        setPostSted(pSted);
        setTlf(tlf);
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = getPersonNr()+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Kontaktperson){
            Kontaktperson k=(Kontaktperson) o;
            return getPersonNr()==k.getPersonNr();
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om kontaktpersonen
    @Override
    public String toString(){
        StringBuilder bygger=new StringBuilder();
        bygger.append(super.toString());
        bygger.append("\nAdresse: ").append(adresse);
        bygger.append("\nPostnummer: ").append(postNummer);
        bygger.append("\nPoststed: ").append(postSted);
        bygger.append("\nTelefonnummer: ").append(tlfNr);
        return bygger.toString();
    }
} //Slutt på klassen Kontaktperson

//Klassen definerer Firma-objekter og arver Gjest. I tillegg inneholder den organisasjonsnummer.
class Firma extends Gjest{
    private static final long serialVersionUID = 360L;
    private int orgNr;
    
    /*Konstruktør som oppretter objekt av typen Firma. 
     * Parameteren n sendes videre til Gjest sin konstruktør. o er organisasjonsnummer.*/
    public Firma(String n, int o){
        super(n);
        orgNr=o;
    }
    
    //Returnerer organisasjonsnummeret
    public int getOrgNr(){
        return orgNr;
    }
    
    //Redefinerer hashkoden for objektet, som brukes for å bestemme om to objekter er like.
    @Override
    public int hashCode(){
        String varstr = orgNr+"";
        return varstr.hashCode();
    }
    
    /*Redefinerer equalsmetoden for objektet, som returnerer om objektet mottat som
     * parameter er likt dette objektet eller ikke.*/
    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o instanceof Firma){
            Firma f=(Firma) o;
            return orgNr==f.getOrgNr();
        }
        return false;
    }
    
    //Returnerer en tekststreng med informasjon om firmaet
    @Override
    public String toString(){
        StringBuilder bygger=new StringBuilder();
        bygger.append(super.toString());
        bygger.append("\nOrganinsasjonsnummer: ").append(orgNr);
        return bygger.toString();
    }
}//end of class Firma
