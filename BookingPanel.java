/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 13.05.12
****Beskrivelse:
* BookingPanel.java inneholder klassene BookingPanel, BookingLytter og Muselytter.
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//Klassen definerer BookingPanel som er en tab i TabVindu sin JTabbedPane. Denne delen av vinduet utfører bookingen.
public class BookingPanel extends Box{
    private JPanel venstreTopp, venstre, hoyre, bunn, vh, vv, hv, hh, b1, b2, topp, t, nederst;
    private JTextField personnummer, navn, adresse, postnummer, poststed, telefon,
            gjester, enkeltrom, dobbeltrom, familierom, suiter, inndato, utdato,
            antHalv, antHel, sok;
    private JButton beregn, bestill, sokeKnapp;
    private JTextArea onsker, status;
    private BookingLytter bookingLytter;
    private Muselytter mLytter;
    private PrisListe prisListe;
    private Gjesteregister gregister;
    private Gjest valgtGjest; //Blir satt i FlereNavnDialogVindu når man velger en gjest.
    private Booking valgtBooking; //Blir satt i FlereBookingerDialogVindu når man velger en booking.
    private Bookingregister bregister;
    private Romregister rregister;
    private BookingDialogVindu bdialogvindu;
    private FlereBookingerDialogVindu fbdialogvindu;
    private TabVindu hovedvindu;
    private FlereNavnDialogVindu navneDialog;
    private boolean avbrytelseDialogVindu; //Blir satt til true i BookingDialogVindu om man avbryter innlegging av gjester.
    private String personNrRegex;
    private String navnet;
    private String adr;
    private String postNrRegex;
    private String postSted;
    private String tlfRegex;
    private String onske;
    private String fraDato;
    private String tilDato;
    private String antGjesterRegex;
    private String antHelpensjonRegex;
    private String antHalvpensjonRegex;
    private String antEnkeltromRegex;
    private String antDobbeltromRegex;
    private String antFamilieromRegex;
    private String antSuiteRegex;
    private long personNr;
    private int postNr;
    private int tlf;
    private int antGjester;
    private int antallHel;
    private int antallHalv;
    private int antEnkeltrom;
    private int antDobbeltrom;
    private int antFamilierom;
    private int antSuiter;
    private final int VALIDER_BOOKING=1;
    private final int VALIDER_PRIS=2;
    
    /*Konstruktør som oppretter et objekt av typen BookingPanel.
     * Parametrene angir hotellets gjesteregister, bookingregister og romregister samt tabens forelder.*/
    public BookingPanel(Gjesteregister g, Bookingregister b, Romregister r, TabVindu tv){
        super(BoxLayout.PAGE_AXIS);
        gregister=g;
        bregister=b;
        rregister=r;
        hovedvindu=tv;
        mLytter=new Muselytter();
        bookingLytter=new BookingLytter();
        prisListe=new PrisListe();
        avbrytelseDialogVindu=false;

        sok=new JTextField(TabVindu.TEKSTFELTSTR);
        sok.addMouseListener(mLytter);
        personnummer=new JTextField(TabVindu.TEKSTFELTSTR);
        navn=new JTextField(TabVindu.TEKSTFELTSTR);
        adresse=new JTextField(TabVindu.TEKSTFELTSTR);
        postnummer=new JTextField(TabVindu.TEKSTFELTSTR);
        poststed=new JTextField(TabVindu.TEKSTFELTSTR);
        telefon=new JTextField(TabVindu.TEKSTFELTSTR);
        gjester=new JTextField(TabVindu.ROMFELTSTR);
        antHalv=new JTextField(TabVindu.ROMFELTSTR);
        antHel=new JTextField(TabVindu.ROMFELTSTR);
        enkeltrom=new JTextField(TabVindu.ROMFELTSTR);
        dobbeltrom=new JTextField(TabVindu.ROMFELTSTR);
        familierom=new JTextField(TabVindu.ROMFELTSTR);
        suiter=new JTextField(TabVindu.ROMFELTSTR);
        inndato=new JTextField(TabVindu.DATOFELTSTR);
        utdato=new JTextField(TabVindu.DATOFELTSTR);

        sokeKnapp=new JButton("Søk");
        sokeKnapp.addActionListener(bookingLytter);
        beregn=new JButton("Beregn pris");
        beregn.addActionListener(bookingLytter);
        bestill=new JButton("Bestill");
        bestill.addActionListener(bookingLytter);

        onsker=new JTextArea(6, 29);
        onsker.addMouseListener(mLytter);
        onsker.setLineWrap(true);
        onsker.setWrapStyleWord(true);
        onsker.setText("Har du spesielle ønsker, kan du skrive dem her");
        status=new JTextArea(13, 49);
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        status.setEditable(false);

        venstreTopp=new JPanel(new FlowLayout(FlowLayout.LEFT)); //Øverste linje (med søkefelt)
        venstreTopp.add(new JLabel("Søk etter pers.nr. eller navn:"));
        venstreTopp.setBorder(new EmptyBorder(10, 0, 0, 0));
        venstreTopp.add(sok);
        venstreTopp.add(sokeKnapp);
        venstreTopp.setBackground(TabVindu.BAKGRUNNSFARGE);

        vv=new JPanel(new GridLayout(0, 1)); //Venstre kolonne i panelet venstre, med labeler
        vh=new JPanel(new GridLayout(0, 1)); //Høyre kolonne i panelet venstre, med tekstfelt
        vv.setBorder(new EmptyBorder(10, 10, 10, 0));
        vh.setBorder(new EmptyBorder(10, 0, 10, 10));
        vv.add(new JLabel("Personnummer"));
        vh.add(personnummer);
        vv.add(new JLabel("Navn"));
        vh.add(navn);
        vv.add(new JLabel("Adresse"));
        vh.add(adresse);
        vv.add(new JLabel("Postnummer"));
        vh.add(postnummer);
        vv.add(new JLabel("Poststed"));
        vh.add(poststed);
        vv.add(new JLabel("Telefonnummer"));
        vh.add(telefon);
        vv.setBackground(TabVindu.BAKGRUNNSFARGE);
        vh.setBackground(TabVindu.BAKGRUNNSFARGE);

        venstre=new JPanel(new BorderLayout(11, 0)); //"Boks" med kontaktperson-info
        venstre.setBorder(BorderFactory.createTitledBorder("Kontaktperson"));
        venstre.add(vv, BorderLayout.LINE_START);
        venstre.add(vh, BorderLayout.LINE_END);
        venstre.setBackground(TabVindu.BAKGRUNNSFARGE);

        hv=new JPanel(new GridLayout(0, 1)); //Venstre kolonne i panelet hoyre, med labeler
        hh=new JPanel(new GridLayout(0, 1)); //Høyre kolonne i panelet hoyre, med tekstfelt
        hv.setBorder(new EmptyBorder(10, 18, 10, 0));
        hh.setBorder(new EmptyBorder(10, 10, 10, 10));
        hv.add(new JLabel("Antall gjester"));
        hh.add(gjester);
        hv.add(new JLabel("Antall halvpensjon"));
        hh.add(antHalv);
        hv.add(new JLabel("Antall helpensjon"));
        hh.add(antHel);
        hv.add(new JLabel("Antall enkeltrom"));
        hh.add(enkeltrom);
        hv.add(new JLabel("Antall dobbeltrom"));
        hh.add(dobbeltrom);
        hv.add(new JLabel("Antall familierom"));
        hh.add(familierom);
        hv.add(new JLabel("Antall suiter"));
        hh.add(suiter);
        hv.setBackground(TabVindu.BAKGRUNNSFARGE);
        hh.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyre=new JPanel(new BorderLayout()); //Panel til høyre med antall gjester, pensjon og rom
        hoyre.add(hv, BorderLayout.LINE_START);
        hoyre.add(hh, BorderLayout.LINE_END);
        hoyre.setBackground(TabVindu.BAKGRUNNSFARGE);

        b1=new JPanel(new GridLayout(3, 0, 10, 10)); //Datolabeler, -felt og -knapper i panelet bunn
        b1.setBorder(new EmptyBorder(0, 10, 20, 0));
        b2=new JPanel(new GridLayout(1, 0)); //Ønskefelt i panelet bunn
        b2.setBorder(new EmptyBorder(0, 0, 15, 0));
        b1.add(new JLabel("Ønsket innsjekking"));
        b1.add(inndato);
        b1.add(new JLabel("(dd-mm-åååå)"));
        b1.add(new JLabel("Ønsket utsjekking"));
        b1.add(utdato);
        b1.add(new JLabel("(dd-mm-åååå)"));
        b1.add(beregn);
        b1.add(bestill);
        b2.add(new JScrollPane(onsker));
        b1.setBackground(TabVindu.BAKGRUNNSFARGE);
        b2.setBackground(TabVindu.BAKGRUNNSFARGE);

        bunn=new JPanel(new BorderLayout()); //Panel i panel nederst med datoer, knapper og ønskefelt
        bunn.add(b1, BorderLayout.LINE_START);
        bunn.add(b2, BorderLayout.LINE_END);
        bunn.add(new JScrollPane(status), BorderLayout.PAGE_END); //Legger til statusfelt
        bunn.setBackground(TabVindu.BAKGRUNNSFARGE);

        t=new JPanel(new FlowLayout(10));
        t.add(venstre);
        t.add(hoyre);
        t.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        topp=new JPanel(new BorderLayout());
        topp.add(t, BorderLayout.LINE_START); //Legger høyre og venstre i LINE_START, så de ikke flytter seg
        topp.add(venstreTopp, BorderLayout.PAGE_START);
        topp.setBackground(TabVindu.BAKGRUNNSFARGE);

        nederst=new JPanel(new BorderLayout());
        nederst.add(bunn, BorderLayout.LINE_START);
        nederst.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        add(topp);
        add(nederst);
    }
    
    public JTextArea getStatus(){ //returnerer statusfeltet.
        return status;
    }
    
    //setter datafeltet som angir om vi har avbrutt BookingDialogVinduene som "popper" opp med verdien i parameteret.
    public void setAvbrytelseDialogVindu(boolean t){
        avbrytelseDialogVindu=t;
    }
    
    public void setValgtGjest(Gjest g){ //Setter valgtGjest lik g i parameteren.
        valgtGjest=g;
    }
    
    public void setValgtBooking(Booking b){ //Setter valgtBooking lik b i parameteren.
        valgtBooking=b;
    }
    
    /*Søker etter gjest i hotellets gjesteregister med tekststrengen angitt i parameteren. Ved treff fylles tekstfelter
     * i vinduet. Ved flere treff opprettes et objekt av klassen FlereNavnDialogVindu.*/
    public void finnGjest(String s){
        if(s.length()==0){
            status.setText("Søkefeltet må fylles med navn eller personnummer!");
            return;
        }
        if(!s.matches("\\d{11}") &&
            !s.matches("[a-zæøåA-ZÆØÅ -.'`´]{2,}")){
            status.setText("Personnummer må fylles inn med nøyaktig 11 siffer,\n"
                +"eller navn må fylles inn med kun bokstaver!\n");
        }
        else{
            Gjest g=null;
            if(s.matches("\\d{11}")){
                long pNr=Long.parseLong(s);
                g=gregister.finnGjest(pNr);
            }
            else{
                Set<Gjest> gjesteListe=gregister.finnGjest(s);
                Iterator<Gjest> iter=gjesteListe.iterator();
                if(gjesteListe.size()==1)
                    g=iter.next();
                else if(gjesteListe.size()>1){
                    navneDialog=new FlereNavnDialogVindu(hovedvindu, gjesteListe,this);
                    navneDialog.setLocationRelativeTo(this);
                    navneDialog.setVisible(true);
                    if(valgtGjest!=null)
                        g=valgtGjest;              
                }
            }        
            if(g==null){
                status.setText("Finner ikke gjest");
                return;
            }
            if(g instanceof Kontaktperson){
                Kontaktperson kontakt=(Kontaktperson) g;
                adresse.setText(kontakt.getAdresse());
                postnummer.setText(kontakt.getPostNr()+"");
                poststed.setText(kontakt.getPostSted());
                telefon.setText(kontakt.getTlfNr()+"");
                finnBookinger(kontakt);
            }
            if(g instanceof Person){
                Person person=(Person) g;
                personnummer.setText(person.getPersonNr()+"");
                navn.setText(person.getNavn());
            }            
        }
    } //Slutt på finnGjest(String s).
    
    /*Søker etter booking i hotellets bookingregister med gjest angitt i parameteren. Ved treff fylles tekstfelter i
     * vinduet. Ved flere treff opprettes et objekt av klassen FlereBookingerDialogVindu.*/
    public void finnBookinger(Gjest g){
        if(g instanceof Kontaktperson){
            Kontaktperson kontakt=(Kontaktperson) g;
            Set<Booking> bookinger=bregister.finnBooking(kontakt.getPersonNr());
            if(bookinger.isEmpty())
                return;
            if(bookinger.size()==1){
                Iterator<Booking> iter=bookinger.iterator();
                Booking booking=iter.next();
                gjester.setText(booking.getAntGjester()+"");
                enkeltrom.setText(booking.getAntEnerom()+"");
                dobbeltrom.setText(booking.getAntDobbeltrom()+"");
                familierom.setText(booking.getAntFamilierom()+"");
                suiter.setText(booking.getAntSuite()+"");
                
                GregorianCalendar inn=booking.getFraDato();
                int innMndTall=inn.get(GregorianCalendar.MONTH)+1;
                String innMnd=innMndTall+"";
                if(innMnd.length()==1)
                    innMnd="0"+innMnd;
                String innDag=inn.get(GregorianCalendar.DATE)+"";
                if(innDag.length()==1)
                    innDag="0"+innDag;
                inndato.setText(innDag+"-"+innMnd+"-"+inn.get(GregorianCalendar.YEAR));
                GregorianCalendar ut=booking.getTilDato();
                int utMndTall=ut.get(GregorianCalendar.MONTH)+1;
                String utMnd=utMndTall+"";
                if(utMnd.length()==1)
                    utMnd="0"+utMnd;
                String utDag=ut.get(GregorianCalendar.DATE)+"";
                if(utDag.length()==1)
                    utDag="0"+utDag;
                
                utdato.setText(utDag+"-"+utMnd+"-"+ut.get(GregorianCalendar.YEAR));                    
                antHalv.setText(booking.getAntHalvpensjon()+"");
                antHel.setText(booking.getAntHelpensjon()+"");
                onsker.setText(booking.getOnsker());
            }
            else{
                fbdialogvindu=new FlereBookingerDialogVindu(hovedvindu,bookinger,this);
                fbdialogvindu.setLocationRelativeTo(this);
                fbdialogvindu.setVisible(true);
                if(valgtBooking!=null){
                    gjester.setText(valgtBooking.getAntGjester()+"");
                    enkeltrom.setText(valgtBooking.getAntEnerom()+"");
                    dobbeltrom.setText(valgtBooking.getAntDobbeltrom()+"");
                    familierom.setText(valgtBooking.getAntFamilierom()+"");
                    suiter.setText(valgtBooking.getAntSuite()+"");
                    
                    GregorianCalendar inn=valgtBooking.getFraDato();
                    int innMndTall=inn.get(GregorianCalendar.MONTH)+1;
                    String innMnd=innMndTall+"";
                    if(innMnd.length()==1)
                        innMnd="0"+innMnd;
                    String innDag=inn.get(GregorianCalendar.DATE)+"";
                    if(innDag.length()==1)
                        innDag="0"+innDag;
                    inndato.setText(innDag+"-"+innMnd+"-"+inn.get(GregorianCalendar.YEAR));
                    GregorianCalendar ut=valgtBooking.getTilDato();
                    int utMndTall=ut.get(GregorianCalendar.MONTH)+1;
                    String utMnd=utMndTall+"";
                    if(utMnd.length()==1)
                        utMnd="0"+utMnd;
                    String utDag=ut.get(GregorianCalendar.DATE)+"";
                    if(utDag.length()==1)
                        utDag="0"+utDag;
                    
                    utdato.setText(utDag+"-"+utMnd+"-"+ut.get(GregorianCalendar.YEAR));                    
                    antHalv.setText(valgtBooking.getAntHalvpensjon()+"");
                    antHel.setText(valgtBooking.getAntHelpensjon()+"");
                    onsker.setText(valgtBooking.getOnsker());
                }
            }
        } //Slutt på if(g instanceof Kontaktperson).
    } //Slutt på finnBookinger(Gjest g).
    
    //Beregner pris for oppholdet før booking blir utført.
    public void beregnPris(){
        double totalpris;
        double enkeltromPris;
        double dobbeltromPris;
        double familieromPris;
        double suitePris;
        double helpensjonPris;
        double halvpensjonPris;

        StringBuilder bygger=new StringBuilder();
        bygger.append(validerInput(VALIDER_PRIS));
        
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }

        // Hvis programmet kommer hit har vi sikret at input fra bruker er korrekt.
        antGjester=Integer.parseInt(antGjesterRegex);
        antallHel=Integer.parseInt(antHelpensjonRegex);
        antallHalv=Integer.parseInt(antHalvpensjonRegex);
        antEnkeltrom=Integer.parseInt(antEnkeltromRegex);
        antDobbeltrom=Integer.parseInt(antDobbeltromRegex);
        antFamilierom=Integer.parseInt(antFamilieromRegex);
        antSuiter=Integer.parseInt(antSuiteRegex);

        //Får tak i oppgitte datoer ved å splitte opp teksten ved bindestreker.
        String[] fraDatoArray=fraDato.split("-");
        int fradag=Integer.parseInt(fraDatoArray[0]);
        int framnd=Integer.parseInt(fraDatoArray[1]);
        int fraaar=Integer.parseInt(fraDatoArray[2]);
        String[] tilDatoArray=tilDato.split("-");
        int tildag=Integer.parseInt(tilDatoArray[0]);
        int tilmnd=Integer.parseInt(tilDatoArray[1]);
        int tilaar=Integer.parseInt(tilDatoArray[2]);
        GregorianCalendar fra=new GregorianCalendar(fraaar, framnd-1, fradag);
        GregorianCalendar til=new GregorianCalendar(tilaar, tilmnd-1, tildag);
        bygger.append(Bookingregister.datoKontroll(fra, til)); //Kontrollerer om dato er godkjent for booking.

        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }

        int antNetter=til.get(GregorianCalendar.DAY_OF_YEAR)-fra.get(GregorianCalendar.DAY_OF_YEAR);
        int antHoytidsdager=prisListe.finnAntallHoytidsdager(fra, til);
        int antVanligedager=(antNetter-antHoytidsdager);

        // Regner ut totalprisen for de forskjellige rommene, og legger til prispåslag om det er høytid.
        enkeltromPris=(antEnkeltrom*(antHoytidsdager*prisListe.getEnkeltromPrisPrNatt())*PrisListe.HOYTIDSPAASLAG)+
                (antEnkeltrom*(antVanligedager*prisListe.getEnkeltromPrisPrNatt()));
        dobbeltromPris=(antDobbeltrom*(antHoytidsdager*prisListe.getDobbeltromPrisPrNatt()*PrisListe.HOYTIDSPAASLAG))+
                (antDobbeltrom*(antVanligedager*prisListe.getDobbeltromPrisPrNatt()));
        familieromPris=(antFamilierom*(antHoytidsdager*prisListe.getFamilieromPrisPrNatt()*PrisListe.HOYTIDSPAASLAG))+
                (antFamilierom*(antVanligedager*prisListe.getFamilieromPrisPrNatt()));
        suitePris=(antSuiter*(antHoytidsdager*prisListe.getSuitePrisPrNatt()*PrisListe.HOYTIDSPAASLAG))+
                (antSuiter*(antVanligedager*prisListe.getSuitePrisPrNatt()));

        helpensjonPris=antallHel*antNetter*prisListe.getHelPensjonPrisPrDag();
        halvpensjonPris=antallHalv*antNetter*prisListe.getHalvPensjonPrisPrDag();

        totalpris=enkeltromPris+dobbeltromPris+familieromPris+suitePris+helpensjonPris+halvpensjonPris;
        
        //Beregner om det er valgt nok sengeplasser i forhold til antall gjester.
        int antSenger=antEnkeltrom*Romregister.MAXPERSONERENEROM+
            antDobbeltrom*Romregister.MAXPERSONERDOBBELTROM+
            antFamilierom*Romregister.MAXPERSONERFAMILIEROM+
            antSuiter*Romregister.MAXPERSONERSUITE;
        if(antSenger<antGjester)
            bygger.append("OBS! Du har valgt for få sengeplasser til gjestene!\n")
                .append("Du trenger ").append(antGjester-antSenger).append(" sengeplasser til.\n");
        if(antallHel+antallHalv<antGjester)
            bygger.append("OBS! Du har valgt færre hel- og halvpensjoner enn gjester.\n");

        // Skriver ut totalpris ++.
        bygger.append("Totalpris: ").append(totalpris).append("kr for valgte:\n");
        if(antEnkeltrom>0)
            bygger.append(antEnkeltrom).append("stk enkeltrom\n");
        if(antDobbeltrom>0)
            bygger.append(antDobbeltrom).append("stk dobbeltrom\n");
        if(antFamilierom>0)
            bygger.append(antFamilierom).append("stk familierom\n");
        if(antSuiter>0)
            bygger.append(antSuiter).append("stk suiter\n");
        if(antallHel>0)
            bygger.append(antallHel).append("stk helpensjon\n");
        if(antallHalv>0)
            bygger.append(antallHalv).append("stk halvpensjon");
        status.setText(bygger.toString());
    }

    //Booker Rom, og oppretter Reservasjon i rommet.
    public void book(){
        StringBuilder bygger=new StringBuilder();
        bygger.append(validerInput(VALIDER_BOOKING));
        
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }

        //Kommer programmet hit har vi sikret oss at alle inntastede verdier er godkjente.
        personNr=Long.parseLong(personNrRegex);
        postNr=Integer.parseInt(postNrRegex);
        tlf=Integer.parseInt(tlfRegex);
        antGjester=Integer.parseInt(antGjesterRegex);
        antallHel=Integer.parseInt(antHelpensjonRegex);
        antallHalv=Integer.parseInt(antHalvpensjonRegex);
        antEnkeltrom=Integer.parseInt(antEnkeltromRegex);
        antDobbeltrom=Integer.parseInt(antDobbeltromRegex);
        antFamilierom=Integer.parseInt(antFamilieromRegex);
        antSuiter=Integer.parseInt(antSuiteRegex);
        
        //Beregner om det er valgt nok sengeplasser i forhold til antall gjester.
        int antSenger=antEnkeltrom*Romregister.MAXPERSONERENEROM+
            antDobbeltrom*Romregister.MAXPERSONERDOBBELTROM+
            antFamilierom*Romregister.MAXPERSONERFAMILIEROM+
            antSuiter*Romregister.MAXPERSONERSUITE;
        if(antSenger<antGjester)
            bygger.append("Du har valgt for få sengeplasser til gjestene!\n")
                .append("Du trenger ").append(antGjester-antSenger).append(" sengeplasser til.");
        if(antallHel+antallHalv<antGjester){
            int valgt=JOptionPane.showConfirmDialog(null,
                "Du har valgt færre hel- og halvpensjoner enn gjester. Vennligst bekreft om dette stemmer:", "Hel- og halvpensjon",
                JOptionPane.YES_NO_OPTION);
            if(valgt==JOptionPane.NO_OPTION || valgt==JOptionPane.CLOSED_OPTION)
                return;
        }

        //Får tak i oppgitte datoer.
        String[] fraDatoArray=fraDato.split("-");
        int fradag=Integer.parseInt(fraDatoArray[0]);
        int framnd=Integer.parseInt(fraDatoArray[1]);
        int fraaar=Integer.parseInt(fraDatoArray[2]);
        String[] tilDatoArray=tilDato.split("-");
        int tildag=Integer.parseInt(tilDatoArray[0]);
        int tilmnd=Integer.parseInt(tilDatoArray[1]);
        int tilaar=Integer.parseInt(tilDatoArray[2]);
        GregorianCalendar fra=new GregorianCalendar(fraaar, framnd-1, fradag);
        GregorianCalendar til=new GregorianCalendar(tilaar, tilmnd-1, tildag);
        bygger.append(Bookingregister.datoKontroll(fra, til)); //Kontrollerer om dato er godkjent for booking

        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }

        //Sjekker om det er nok ledige rom utifra kundens bestilling.
        Set<Rom> ledigeEnkeltRom = null;
        Set<Rom> ledigeDobbeltRom = null;
        Set<Rom> ledigeFamilieRom = null;
        Set<Rom> ledigeSuiter = null;
        if(antEnkeltrom>0){
            ledigeEnkeltRom=rregister.finnLedigeEnkeltrom(fra, til);
            int str=ledigeEnkeltRom.size();
            if(antEnkeltrom>str){
                bygger.append("Ikke nok ledige enkeltrom! Det er kun ").append(str).append(" ledige enkeltrom.\n");
            }
        }
        if(antDobbeltrom>0){
            ledigeDobbeltRom=rregister.finnLedigeDobbeltrom(fra, til);
            int str=ledigeDobbeltRom.size();
            if(antDobbeltrom>str){
                bygger.append("Ikke nok ledige dobbeltrom! Det er kun ").append(str).append(" ledige dobbelrom.\n");
            }
        }
        if(antFamilierom>0){
            ledigeFamilieRom=rregister.finnLedigeFamilierom(fra, til);
            int str=ledigeFamilieRom.size();
            if(antFamilierom>str){
                bygger.append("Ikke nok ledige familierom! Det er kun ").append(str).append(" ledige familierom.\n");
            }
        }
        if(antSuiter>0){
            ledigeSuiter=rregister.finnLedigeSuiter(fra, til);
            int str=ledigeSuiter.size();
            if(antSuiter>str){
                bygger.append("Ikke nok ledige suiter! Det er kun ").append(str).append(" ledige suiter.\n");
            }
        }

        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }

        //Kommer programmet hit, har vi sikret oss at det er nok ledige rom. Kontrollerer om gjest finnes fra før.
        Gjest gjest=gregister.finnGjest(personNr);
        Booking nyBooking = null;
        if(gjest==null){ // Da finnes ikke gjest fra før av i registeret!
            Kontaktperson nygjest=new Kontaktperson(navnet, personNr, adr, postNr, postSted, tlf);
            if(!gregister.settInn(nygjest)){
                status.setText("Kunne ikke registrere kontaktperson");
                return;
            }
            nyBooking=new Booking(antGjester, antEnkeltrom, antDobbeltrom, antFamilierom, antSuiter, fra, til, antallHel, antallHalv, onske, nygjest);
        }
        else if(gjest instanceof Kontaktperson){
            Kontaktperson kgjest=(Kontaktperson) gjest;
            Kontaktperson nyGjest=new Kontaktperson(navnet, personNr, adr, postNr, postSted, tlf);
            if(kgjest.equals(nyGjest)){ //Hvis opprinnelig gjest er lik inntastet personalia
                StringBuilder streng=new StringBuilder();
                streng.append("Gjest med dette personnummeret eksisterer allerede, vennligst kontroller:\n");
                streng.append("Opprinnelig informasjon:\n").append(kgjest.toString()).append("\n\n");
                streng.append("Ny informasjon:\n").append(nyGjest.toString());
                String[] valg = { "Behold opprinnelig informasjon", "Oppdater", "Avbryt" };
                int valgt=JOptionPane.showOptionDialog(this, streng.toString(), "Velg riktig informasjon",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
                if(valgt==0){
                    navn.setText(kgjest.getNavn());
                    adresse.setText(kgjest.getAdresse());
                    postnummer.setText(""+kgjest.getPostNr());
                    poststed.setText(kgjest.getPostSted());
                    telefon.setText(""+kgjest.getTlfNr());
                    status.setText("Beholder opprinnelig kontaktinformasjon\n");
                }
                if(valgt==1){
                    kgjest.oppdater(navnet, adr, postNr, postSted, tlf);
                    status.setText("Kontaktinformasjon oppdatert\n");
                }
                if(valgt==2){
                    status.setText("Bookingen ble avbrutt\n");
                    return;
                }
                nyBooking=new Booking(antGjester, antEnkeltrom, antDobbeltrom, antFamilierom, antSuiter, fra, til, antallHel, antallHalv, onske, kgjest);
            }
        }
        else if(gjest instanceof Person){ //Oppdatere i så fall gjesten fra Person til Kontaktperson.
            Kontaktperson nyKperson=new Kontaktperson(navnet, personNr, adr, postNr, postSted, tlf);
            gregister.slettGjest(gjest);
            if(!gregister.settInn(nyKperson)){
                status.setText("Kunne ikke registrere kontaktperson");
                return;
            }
            nyBooking=new Booking(antGjester, antEnkeltrom, antDobbeltrom, antFamilierom, antSuiter, fra, til, antallHel, antallHalv, onske, nyKperson);
        }
        Booking gammelBooking=bregister.finnBooking(nyBooking); //Sjekker om lignende booking finnes allerede
        if(gammelBooking==null){                                 //Hvis ikke, registreres bookingen
            if(!bregister.settInn(nyBooking)){
                status.setText("Problemer med booking!");
                return;
            }
        }
        else{ //Hvis booking finnes, kan man velge hvilken man vil beholde
             StringBuilder streng=new StringBuilder();
                streng.append("Lignende booking eksisterer allerede, vennligst kontroller:\n");
                streng.append("Opprinnelig informasjon:\n").append(gammelBooking.toString()).append("\n\n");
                streng.append("Ny informasjon:\n").append(nyBooking.toString());
            String[] valg = { "Behold opprinnelig booking", "Oppdater", "Slett begge", "Avbryt" };
                int valgt=JOptionPane.showOptionDialog(this, streng.toString(), "Bookingen finnes allerede!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
                if(valgt==0){ // Bruke konstanter?
                    status.setText("Beholder opprinnelig booking");
                    return;
                }
                if(valgt==1){
                    if(!rregister.finnAlleInnsjekkedeRom(gammelBooking.getKontaktperson().getPersonNr()).isEmpty()){
                        status.setText("Et eller flere rom i bookingen er allerede innsjekket!");
                        return;
                    }
                    if(!bregister.fjern(gammelBooking)){
                        status.setText("Problemer med sletting av opprinnelig booking!\nPrøv på nytt.");
                        return;
                    }
                    if(!bregister.settInn(nyBooking)){
                        status.setText("Problemer med registrering av ny booking!");
                        return;
                    }
                    status.setText("Bookingen er oppdatert\n");
                }
                if(valgt==2){
                    if(!rregister.finnAlleInnsjekkedeRom(gammelBooking.getKontaktperson().getPersonNr()).isEmpty()){
                        status.setText("Et eller flere rom i bookingen er allerede innsjekket!");
                        return;
                    }
                    if(!bregister.fjern(gammelBooking)){
                        status.setText("Problemer med sletting av opprinnelig booking!\nPrøv på nytt.");
                        return;
                    }
                    else{
                        status.setText("Slettet opprinnelig og ny booking");
                        return;
                    }
                }
                if(valgt==3){
                    status.setText("Bookingen ble avbrutt\n");
                    return;
                }
                
        } //Slutt på else
        
        //Oppretter reservasjoner i bestilte rom.
        int antallRom = 0;
        Set<Rom> reserverteRom=new HashSet<>();
        if(antEnkeltrom>0){
            Iterator<Rom> iter=ledigeEnkeltRom.iterator();
            while(antallRom<antEnkeltrom && iter.hasNext()){
                Rom funnet=iter.next();
                if(!funnet.opprettPrivatReservasjon(fra, til, personNr))
                    bygger.append("Kunne ikke opprette reservasjon av enkeltrom\n");
                reserverteRom.add(funnet);
                antallRom++;
            }
            antallRom=0;
        }

        if(antDobbeltrom>0){
            Iterator<Rom> iter2=ledigeDobbeltRom.iterator();
            while(antallRom<antDobbeltrom && iter2.hasNext()){
                Rom funnet=iter2.next();
                if(!funnet.opprettPrivatReservasjon(fra, til, personNr))
                    bygger.append("Kunne ikke opprette reservasjon av dobbeltrom\n");
                reserverteRom.add(funnet);
                antallRom++;
            }
            antallRom=0;
        }

        if(antFamilierom>0){
            Iterator<Rom> iter3=ledigeFamilieRom.iterator();
            while(antallRom<antFamilierom && iter3.hasNext()){
                Rom funnet=iter3.next();
                if(!funnet.opprettPrivatReservasjon(fra, til, personNr))
                    bygger.append("Kunne ikke opprette reservasjon av familierom\n");
                reserverteRom.add(funnet);
                antallRom++;
            }
            antallRom=0;
        }

        if(antSuiter>0){
            Iterator<Rom> iter4=ledigeSuiter.iterator();
            while(antallRom<antSuiter && iter4.hasNext()){
                Rom funnet=iter4.next();
                if(!funnet.opprettPrivatReservasjon(fra, til, personNr))
                    bygger.append("Kunne ikke opprette reservasjon av suite\n");
                reserverteRom.add(funnet);
                antallRom++;
            }
        }
        if(!"".equals(bygger.toString())){ //Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            if(!bregister.fjern(nyBooking)){
                bygger.append("Problemer med sletting av opprinnelig booking!\nPrøv på nytt.");
            }
            else
                bygger.append("Booking slettet med tilhørende reservasjoner");
            status.setText(bygger.toString());
        }
        else{
            Iterator<Rom> iter5=reserverteRom.iterator();
            while(iter5.hasNext()){
                Rom funnet=iter5.next();
                int senger=funnet.getMaxPersoner();
                
                PrivatReservasjon res=(PrivatReservasjon) funnet.getKontaktReservasjon(fra,personNr);
                bdialogvindu=new BookingDialogVindu(hovedvindu,senger,res,this, status, gregister);
                bdialogvindu.setLocationRelativeTo(this);
                bdialogvindu.setVisible(true);
                if(avbrytelseDialogVindu){ //Hvis "Hopp over alle" eller "Avbryt" er trykket inn.
                    status.append("Booking utført med tilhørende rom!");
                    return;
                }
            }
            status.append("Booking utført med tilhørende rom!");           
        }
    } //Slutt på book().
    
    //Nuller ut alle tekstfelter.
    public void nullUtTekstfelt(){
        personnummer.setText("");
        navn.setText("");
        adresse.setText("");
        postnummer.setText("");
        poststed.setText("");
        telefon.setText("");
        gjester.setText("");
        enkeltrom.setText("");
        dobbeltrom.setText("");
        familierom.setText("");
        suiter.setText("");
        inndato.setText("");
        utdato.setText("");
        antHalv.setText("");
        antHel.setText("");
        status.setText("");
    }
    
    //Validerer input fra bruker.
    private String validerInput(int i){ 
        personNrRegex=personnummer.getText().trim();
        navnet=navn.getText().trim();
        adr=adresse.getText().trim();
        postNrRegex=postnummer.getText().trim();
        postSted=poststed.getText().trim();
        tlfRegex=telefon.getText().trim();
        onske=onsker.getText().trim();
        fraDato=inndato.getText().trim();
        tilDato=utdato.getText().trim();
        antGjesterRegex=gjester.getText().trim();
        antHelpensjonRegex=antHel.getText().trim();
        antHalvpensjonRegex=antHalv.getText().trim();
        antEnkeltromRegex=enkeltrom.getText().trim();
        antDobbeltromRegex=dobbeltrom.getText().trim();
        antFamilieromRegex=familierom.getText().trim();
        antSuiteRegex=suiter.getText().trim();
        
        StringBuilder bygger=new StringBuilder();
        if(i==VALIDER_BOOKING){
            if(!personNrRegex.matches("\\d{11}")){
                bygger.append("'Personnummer' må fylles inn med nøyaktig 11 siffer!\n");
            }
            if(!navnet.matches("[a-zæøåA-ZÆØÅ -.'`´]{2,}")){
                bygger.append("'Navn' må fylles inn med minst 2 bokstaver!\n");
            }
            if(!adr.matches("[a-zæøåA-ZÆØÅ -.]+[\\d]+")){ //if(!adr.matches("[a-zæøåA-ZÆØÅ]+[-.]*[\\d]+")){
                bygger.append("'Adresse' må fylles inn med gatenavn og nummer!\n");
            }
            if(!postNrRegex.matches("\\d{4}")){
                bygger.append("'Postnummer' må fylles inn med nøyaktig 4 siffer!\n");
            }
            if(!postSted.matches("[a-zæøåA-ZÆØÅ]{2,}")){
                bygger.append("'Poststed' må fylles inn med kun bokstaver!\n");
            }
            if(!tlfRegex.matches("\\d{8}")){
                bygger.append("'Telefonnummer' må fylles inn med nøyaktig 8 siffer!\n");
            }
        }
        if(!fraDato.matches("\\d{2}-\\d{2}-20[\\d]{2}")){
            bygger.append("'Ønsket innsjekking' må velges, og være lik åååå-mm-dd!\n");
        }
        if(!tilDato.matches("\\d{2}-\\d{2}-20[\\d]{2}")){
            bygger.append("'Ønsket utsjekking' må velges, og lik åååå-mm-dd!\n");
        }
        if(antGjesterRegex.length()==0)
            bygger.append("'Antall gjester' må fylles inn!\n");
        else if(!antHelpensjonRegex.matches("\\d*")){
            bygger.append("'Antall gjester' må fylles inn med kun tall!\n");
        }
        if(antHelpensjonRegex.length()==0)
            antHelpensjonRegex="0";
        else if(!antHelpensjonRegex.matches("\\d*")){
            bygger.append("'Antall helpensjon' må fylles inn med kun tall!\n");
        }
        if(antHalvpensjonRegex.length()==0)
            antHalvpensjonRegex="0";
        else if(!antHalvpensjonRegex.matches("\\d*")){
            bygger.append("'Antall halvpensjon' må fylles inn med kun tall!\n");
        }
        if(antEnkeltromRegex.length()==0)
            antEnkeltromRegex="0";
        else if(!antEnkeltromRegex.matches("\\d*")){
            bygger.append("'Antall enkeltrom' må fylles inn med kun tall!\n");
        }
        if(antDobbeltromRegex.length()==0)
            antDobbeltromRegex="0";
        else if(!antDobbeltromRegex.matches("\\d*")){
            bygger.append("'Antall dobbeltrom' må fylles inn med kun tall!\n");
        }
        if(antFamilieromRegex.length()==0)
            antFamilieromRegex="0";
        else if(!antFamilieromRegex.matches("\\d*")){
            bygger.append("'Antall familierom' må fylles inn med kun tall!\n");
        }
        if(antSuiteRegex.length()==0)
            antSuiteRegex="0";
        else if(!antSuiteRegex.matches("\\d*")){
            bygger.append("'Antall suiter' må fylles inn med kun tall!\n");
        }
        if(antEnkeltromRegex.length()==0 &&
            antDobbeltromRegex.length()==0 &&
            antFamilieromRegex.length()==0 &&
            antSuiteRegex.length()==0)
            bygger.append("Type og antall rom må velges!\n");
        return bygger.toString();
    }
    
    //Indre klasse som lytter på knappetrykk.
    private class BookingLytter implements ActionListener{
        @Override
    	public void actionPerformed( ActionEvent ae ){
            if(ae.getSource()==sokeKnapp){
                nullUtTekstfelt();
                finnGjest(sok.getText().trim());
            }
            else if(ae.getSource()==beregn){
                status.setText("");
                beregnPris();
            }
            else if(ae.getSource()==bestill){
                status.setText("");
                book();
            }
  	}
    } //Slutt på klassen BookingLytter
    
    //Indre klasse som lytter på musetrykk.
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getSource() == onsker){
                if(onsker.getText().trim().matches("Har du spesielle ønsker, kan du skrive dem her"))
                    onsker.setText("");
            }
            else if(me.getSource()==sok)
                if(!sok.getText().trim().matches("")){
                    sok.setText("");
                    nullUtTekstfelt();
                }
        } 
        @Override
        public void mouseExited(MouseEvent me){
            if(me.getSource()==onsker){
                if(onsker.getText().trim().length()==0)
                    onsker.setText("Har du spesielle ønsker, kan du skrive dem her");
            }
        }
    } //Slutt på klassen Muselytter
} //Slutt på klassen BookingPanel