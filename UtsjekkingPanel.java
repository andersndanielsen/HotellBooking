/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 11.05.12
****Beskrivelse:
* UtsjekkingPanel.java inneholder klassene UtsjekkingPanel,
* UtsjekkingLytter, Muselytter, KontaktListeRendrer og RomListeRendrer.
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//Klassen definerer UtsjekkingPanel som er en tab i TabVindu sin JTabbedPane. Denne delen av vinduet utfører utsjekkingen.
public class UtsjekkingPanel extends JPanel{
    private Box venstre, hoyre, hovedBox;
    private JPanel venstreTopp, venstreKnapper, betalPanel, hoyreGrid, hoyreLabel;
    private JTextField sok;
    private JButton sokeKnapp, sjekkUt, sjekkUtAlle, fakturer, betal, oppdater;
    private JTextArea faktura;
    private JList<Rom> romvelger;
    private DefaultListModel<Rom> romModell;
    private JList<Kontaktperson> kontaktvelger;
    private DefaultListModel<Kontaktperson> kontaktModell;
    private PrisListe prisListe;
    private UtsjekkingLytter lytter;
    private Muselytter mLytter;
    private Gjesteregister gregister;
    private Bookingregister bregister;                       
    private Romregister rregister;
    private GregorianCalendar iDag;
    private Booking valgtBooking;
    private FlereNavnDialogVindu navneDialog;
    private TabVindu hovedvindu;
    private Gjest valgtGjest; // blir valgt i FlereNavnDialogVindu
    
    /*Konstruktør som oppretter et objekt av typen UtsjekkingPanel.
     * Parametrene angir gjesteregister, bookingregister og romregister samt TabVindu.*/
    public UtsjekkingPanel(Gjesteregister g, Bookingregister b, Romregister r, TabVindu tv){
        valgtBooking=null;
        gregister=g;
        bregister=b;
        rregister=r;
        hovedvindu=tv;
        
        lytter=new UtsjekkingLytter();
        mLytter=new Muselytter();
        prisListe=new PrisListe();
        romModell=new DefaultListModel<>(); 
        romvelger = new JList<>(romModell); //Oppretter JList som viser innsjekkede rom for valgt gjest/booking.
        romvelger.setVisibleRowCount(9);
        romvelger.setFixedCellWidth(260);
        romvelger.setCellRenderer(new RomListeRendrer());
        romvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        romvelger.addMouseListener(mLytter);
        kontaktModell=new DefaultListModel<>();
        kontaktvelger = new JList<>(kontaktModell); //Oppretter JList som viser kontaktpersonene til alle innsjekkede rom.
        kontaktvelger.setVisibleRowCount(34);
        kontaktvelger.setFixedCellWidth(70);
        kontaktvelger.setCellRenderer(new KontaktListeRendrer());
        kontaktvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        kontaktvelger.addMouseListener(mLytter);

        sok=new JTextField(15);
        sok.addMouseListener(mLytter);
        
        sokeKnapp=new JButton("Søk");
        sokeKnapp.addActionListener(lytter);
        sjekkUt=new JButton("Sjekk ut");
        sjekkUt.addActionListener(lytter);
        sjekkUtAlle=new JButton("Sjekk ut alle");
        sjekkUtAlle.addActionListener(lytter);
        fakturer=new JButton("Vis faktura");
        fakturer.addActionListener(lytter);
        betal=new JButton("Betal");
        betal.addActionListener(lytter);
        oppdater=new JButton("Oppdater");
        oppdater.addActionListener(lytter);
        
        faktura=new JTextArea(23, 48);
        faktura.setLineWrap(true);
        faktura.setWrapStyleWord(true);
        faktura.setEditable(false);
        faktura.setTabSize(7);
        
        //Oppretter panel med label, søkefelt og -knapp
        venstreTopp=new JPanel(new FlowLayout(FlowLayout.LEFT));
        venstreTopp.add(new JLabel("Søk etter pers.nr. eller navn:"));
        venstreTopp.add(sok);
        venstreTopp.add(sokeKnapp);
        venstreTopp.setBorder(new EmptyBorder(3, 0, 5, 0));
        venstreTopp.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Oppretter panel med utsjekkingsknapper
        venstreKnapper=new JPanel(new GridLayout(1, 0, 10, 10));
        venstreKnapper.add(sjekkUt);
        venstreKnapper.add(sjekkUtAlle);
        venstreKnapper.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Oppretter panel med fakturer- og betalknapp
        betalPanel=new JPanel(new GridLayout(1, 0));
        betalPanel.add(fakturer);
        betalPanel.add(betal);
        betalPanel.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        venstre=new Box(BoxLayout.PAGE_AXIS); //Inneholder alt på venstre side
        venstre.add(venstreTopp);
        venstre.add(new JScrollPane(romvelger));
        venstre.add(venstreKnapper);
        venstre.add(faktura);
        venstre.add(betalPanel);
        venstre.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyreLabel=new JPanel(new FlowLayout(FlowLayout.LEFT)); //Panel som viser label øverst til høyre
        hoyreLabel.add(new JLabel("Skal sjekke ut i dag:"));
        hoyreLabel.setBorder(new EmptyBorder(8, 0, 13, 0));
        hoyreLabel.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyreGrid=new JPanel(new GridLayout()); //Panel med oppdater-knapp på høyre side
        hoyreGrid.add(oppdater);
        hoyreGrid.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyre=new Box(BoxLayout.PAGE_AXIS); //Inneholder alt på høyre side
        hoyre.add(hoyreLabel);
        hoyre.add(new JScrollPane(kontaktvelger));
        hoyre.add(hoyreGrid);
        hoyre.setBorder(new EmptyBorder(0, 10, 0, 0));
        hoyre.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hovedBox=new Box(BoxLayout.LINE_AXIS); //Setter høyre og venstre i en box
        hovedBox.add(venstre);
        hovedBox.add(hoyre);
        hovedBox.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        add(hovedBox, BorderLayout.LINE_START);
        
        iDag=new GregorianCalendar();
        finnKontaktpersoner(iDag);
    } //Slutt på konstruktør
    
    public void setValgtGjest(Gjest g){ //Setter valgt gjest til mottatt gjest
        valgtGjest=g;
    }
    
    //Finner kontaktpersoner som skal sjekke ut idag, og legger de i JListen kontaktvelger.
    private void finnKontaktpersoner(GregorianCalendar til){
        kontaktModell.clear();
        Set<Kontaktperson> kontaktpersoner=bregister.finnKontaktpersoner(til);
        Iterator iter=kontaktpersoner.iterator();
        while(iter.hasNext()){
            Kontaktperson k=(Kontaktperson)iter.next();
            kontaktModell.addElement(k);
        }
    }
    
    //Finner gjest etter personnummer eller navn mottatt som parameter som streng
    private void finnGjest(String s){
        kontaktvelger.clearSelection();
        if(s.length()==0){
            faktura.setText("Søkefeltet må fylles med navn eller personnummer!");
            return;
        }
        if(!s.matches("\\d{11}") && 
            !s.matches("[a-zæøåA-ZÆØÅ -.'`´]{2,}")){
            faktura.setText("Personnummer må fylles inn med nøyaktig 11 siffer,\n"
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
                faktura.setText("Finner ikke gjest");
                return;
            }
            else if(g instanceof Person){
                Person p=(Person) g;
                finnRom(p);
            }
        }
    } //Slutt på metoden finnGjest
    
    /*Finner innsjekket/innsjekkede rom hvor mottatt person er registrert, som gjest 
     * eller kontaktperson og legger det/de til i JListen romvelger.*/
    private void finnRom(Person p){
        romModell.clear();
        Set<Rom> rom=new HashSet<>();
        long kontakt;
        if(p instanceof Kontaktperson){
            Kontaktperson k=(Kontaktperson) p;
            kontakt=k.getPersonNr();
            rom=rregister.finnAlleInnsjekkedeRom(kontakt);
        }
        else{
            Rom r=rregister.finnInnsjekketRom(p.getPersonNr());
            if(r!=null)
                rom.add(r);
            try{
                PrivatReservasjon res=r.getInnsjekketReservasjon(p.getPersonNr());
                kontakt=res.getPersonNr();
            }
            catch(NullPointerException np){
                faktura.setText("FINNER INGEN INNSJEKKEDE ROM");
                return;
            }
        }
        valgtBooking=bregister.finnBooketNaa(iDag, kontakt); //Finner tilhørende booking
        if(rom.isEmpty()){
            if(valgtBooking==null){
                faktura.setText("BOOKING IKKE VALGT");
                return;
            }
            else if(!valgtBooking.getBetalt())
                visFaktura();
            else{
                faktura.setText("FINNER INGEN INNSJEKKEDE ROM");
                return;
            }
        }
        Iterator iter=rom.iterator();
        while(iter.hasNext()){
            Rom r=(Rom)iter.next();
            romModell.addElement(r);
        }
        visFaktura();
    } //Slutt på metoden finnRom
    
    private void visFaktura(){ //Viser faktura for valgt booking dersom den ikke er betalt.
        if(valgtBooking==null){
            faktura.setText("FINNER IKKE BOOKING");
            return;
        }
        if(valgtBooking.getBetalt()){
            faktura.setText("OPPHOLDET ER BETALT");
            return;
        }
        GregorianCalendar inn=valgtBooking.getFraDato();
        GregorianCalendar ut=valgtBooking.getTilDato();
        int antEnkeltrom=0;
        int antDobbeltrom=0;
        int antFamilierom=0;
        int antSuiter=0;
        double enkeltromPris;
        double dobbeltromPris;
        double familieromPris;
        double suitePris;
        int antallHel=valgtBooking.getAntHelpensjon();
        int antallHalv=valgtBooking.getAntHalvpensjon();
        double helpensjonPris;
        double halvpensjonPris;
        int antNetter;
        double totalpris;
        
        //Regner ut antall netter
        if(ut.get(GregorianCalendar.YEAR)==inn.get(GregorianCalendar.YEAR))
            antNetter=ut.get(GregorianCalendar.DAY_OF_YEAR)-inn.get(GregorianCalendar.DAY_OF_YEAR);
        else{ //ved årsskifte
            GregorianCalendar aarSlutt=new GregorianCalendar(inn.get(GregorianCalendar.YEAR), 11, 31);
            antNetter=aarSlutt.get(GregorianCalendar.DAY_OF_YEAR)-inn.get(GregorianCalendar.DAY_OF_YEAR);
            GregorianCalendar aarStart=new GregorianCalendar(ut.get(GregorianCalendar.YEAR), 0, 1);
            antNetter+=ut.get(GregorianCalendar.DAY_OF_YEAR)-aarStart.get(GregorianCalendar.DAY_OF_YEAR)+1;
        }
        
        int antHoytidsdager=prisListe.finnAntallHoytidsdager(inn, ut);
        int antVanligedager=(antNetter-antHoytidsdager);
            
        Set<Rom> reserverteRom=rregister.finnAlleReserverteRom(inn, valgtBooking.getKontaktperson().getPersonNr());
        Iterator iter=reserverteRom.iterator();
        while(iter.hasNext()){
            Rom neste=(Rom)iter.next();
            if(neste.getMaxPersoner()==Romregister.MAXPERSONERENEROM)
                antEnkeltrom++;
            else if(neste.getMaxPersoner()==Romregister.MAXPERSONERDOBBELTROM)
                antDobbeltrom++;
            else if(neste.getMaxPersoner()==Romregister.MAXPERSONERFAMILIEROM)
                antFamilierom++;
            else if(neste.getMaxPersoner()==Romregister.MAXPERSONERSUITE)
                antSuiter++;
        }

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

        // Skriver ut faktura.
        DecimalFormat form=new DecimalFormat("0.00");
        Kontaktperson kontakt=valgtBooking.getKontaktperson();
        StringBuilder bygger=new StringBuilder();
        bygger.append("Faktura for opphold fra ");
        bygger.append(inn.get(GregorianCalendar.DAY_OF_MONTH));
        bygger.append(".").append(inn.get(GregorianCalendar.MONTH)+1);
        bygger.append(".").append(inn.get(GregorianCalendar.YEAR)).append(" til ");
        bygger.append(ut.get(GregorianCalendar.DAY_OF_MONTH));
        bygger.append(".").append(ut.get(GregorianCalendar.MONTH)+1);
        bygger.append(".").append(ut.get(GregorianCalendar.YEAR)).append("\n\n");
        bygger.append(kontakt.toString()).append("\n\n");
        bygger.append("Type\tAntall\tAnt. netter\tPr. natt");
        if(antHoytidsdager>0)
            bygger.append("\tAnt. h.tid\tH.tidstillegg");
        bygger.append("\tTotalt\n");
        if(antEnkeltrom>0){
            bygger.append("Enkeltrom\t").append(antEnkeltrom).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getEnkeltromPrisPrNatt()));
            if(antHoytidsdager>0)
                bygger.append("\t").append(antHoytidsdager).append("\t").append(form.format(prisListe.getEnkeltromPrisPrNatt()*(PrisListe.HOYTIDSPAASLAG-1)));
            bygger.append("\t").append(form.format(enkeltromPris)).append("\n");
        }
        if(antDobbeltrom>0){
            bygger.append("Dobbeltrom\t").append(antDobbeltrom).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getDobbeltromPrisPrNatt()));
            if(antHoytidsdager>0)
                bygger.append("\t").append(antHoytidsdager).append("\t").append(form.format(prisListe.getDobbeltromPrisPrNatt()*(PrisListe.HOYTIDSPAASLAG-1)));
            bygger.append("\t").append(form.format(dobbeltromPris)).append("\n");
        }
        if(antFamilierom>0){
            bygger.append("Familierom\t").append(antFamilierom).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getFamilieromPrisPrNatt()));
            if(antHoytidsdager>0)
                bygger.append("\t").append(antHoytidsdager).append("\t").append(form.format(prisListe.getFamilieromPrisPrNatt()*(PrisListe.HOYTIDSPAASLAG-1)));
            bygger.append("\t").append(form.format(familieromPris)).append("\n");
        }
        if(antSuiter>0){
            bygger.append("Suite\t").append(antSuiter).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getSuitePrisPrNatt()));
            if(antHoytidsdager>0)
                bygger.append("\t").append(antHoytidsdager).append("\t").append(form.format(prisListe.getSuitePrisPrNatt()*(PrisListe.HOYTIDSPAASLAG-1)));
            bygger.append("\t").append(form.format(suitePris)).append("\n");
        }
        if(antallHel>0){
            bygger.append("Helpensjon\t").append(antallHel).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getHelPensjonPrisPrDag()));
            if(antHoytidsdager>0)
                bygger.append("\t\t");
            bygger.append("\t").append(form.format(helpensjonPris)).append("\n");
        }
        if(antallHalv>0){
            bygger.append("Halvpensjon\t").append(antallHalv).append("\t").append(antNetter).append("\t").append(form.format(prisListe.getHalvPensjonPrisPrDag()));
            if(antHoytidsdager>0)
                bygger.append("\t\t");
            bygger.append("\t").append(form.format(halvpensjonPris)).append("\n");
        }
        bygger.append("\nÅ betale\t\t\t\t");
        if(antHoytidsdager>0)
            bygger.append("\t\t");
        bygger.append(form.format(totalpris));
        bygger.append("\n\n\nAlle priser er i NOK inkl. moms.");
        faktura.setText(bygger.toString());
    } //Slutt på metoden visFaktura.
    
    private void betal(){ //Setter bookingen til betalt om den ikke allerede er det
        if(valgtBooking==null){
            faktura.setText("BOOKING IKKE VALGT");
            return;
        }
        if(!valgtBooking.getBetalt()){
            String ut=("Velg ønsket betalingsmåte");
            String[] valg={"Kontanter", "Visa/kreditt", "Faktura", "Avbryt"};
            int valgt=JOptionPane.showOptionDialog(this, ut, "Advarsel!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
            if(valgt==3){
                faktura.setText("Betalingen ble avbrutt\n");
                return;
            }
            valgtBooking.setBetalt(true);
            faktura.setText("BETALINGEN VAR VELLYKKET, TAKK FOR BESØKET!\n");
        }
        else
            faktura.setText("OPPHOLDET ER BETALT\n");        
        if(romModell.isEmpty()){ //Hvis alle rommene er utsjekket.
            boolean ok=bregister.fjern(valgtBooking); //Sletter booking og tilhørende reservasjoner.
            if(!ok){
                faktura.setText("PROBLEMER MED SLETTING AV BOOKING ELLER RESERVASJONER");
            }
            else
                valgtBooking=null;
        }
        else
            faktura.append("HUSK Å SJEKKE UT AV DE RESTERENDE ROMMENE");
    } //Slutt på metoden betal
    
    private void sjekkUt(){ //Sjekker ut rommet som er valgt i romvelgeren
        if(romModell.isEmpty()){
            faktura.setText("Det er ingen rom å sjekke ut!");
            return;
        }
        Rom r=romvelger.getSelectedValue();
        if(r==null){
            faktura.setText("Velg et rom!");
            return;
        }
        PrivatReservasjon res=r.getInnsjekketKontaktReservasjon(valgtBooking.getKontaktperson().getPersonNr()); //Finner reservasjon
        if(iDag.before(res.getTilDato())){
            String ut=("Rommet er booket lengre enn til i dag.\nDet må likevel betales for hele den bookede perioden.\nØnsker du fortsatt å sjekke ut?");
            String[] valg={"Sjekk ut", "Avbryt" };
            int valgt=JOptionPane.showOptionDialog(this, ut, "Advarsel!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
            if(valgt==1){
                faktura.setText("Utsjekkingen ble avbrutt\n");
                return;
            }
        }    
        res.setInnsjekket(false);
        romModell.removeElement(r);
        faktura.setText("Utsjekkingen av rom nr "+r.getRomNr()+" var vellykket");
        if(romModell.isEmpty()){ //Hvis alle rom er utsjekket
            if(valgtBooking.getBetalt()){
                boolean ok=bregister.fjern(valgtBooking); //Sletter booking og reservasjoner om bookingen er betalt
                if(!ok){
                    faktura.setText("PROBLEMER MED SLETTING AV BOOKING ELLER RESERVASJONER");
                }
                else{
                    faktura.setText("TAKK FOR BESØKET!");
                    valgtBooking=null;
                }    
            }
            else
                betal();
        }
    } //Slutt på metoden sjekkUt
    
    private void sjekkUtAlle(){ //Sjekker ut alle rom som vises i romvelgeren
        if(romModell.isEmpty()){
            faktura.setText("Det er ingen rom å sjekke ut!");
            return;
        }
        if(iDag.before(valgtBooking.getTilDato())){
            String ut=("Rommene er booket lengre enn til i dag.\nDet må likevel betales for hele den bookede perioden.\nØnsker du fortsatt å sjekke ut?");
            String[] valg={"Sjekk ut", "Avbryt" };
            int valgt=JOptionPane.showOptionDialog(this, ut, "Advarsel!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
            if(valgt==1){
                faktura.setText("Utsjekkingen ble avbrutt\n");
                return;
            }
        }
        Object [] romliste=romModell.toArray();
        for(int i=0; i<romliste.length; i++){
            Rom r=(Rom) romliste[i];
            PrivatReservasjon res=r.getInnsjekketKontaktReservasjon(valgtBooking.getKontaktperson().getPersonNr());
            res.setInnsjekket(false);
        }
        romModell.clear();
        if(!valgtBooking.getBetalt())
            betal();
        else{
            boolean ok=bregister.fjern(valgtBooking); //Sletter booking og reservasjoner om bookingen er betalt
            if(!ok){
                faktura.setText("PROBLEMER MED SLETTING AV BOOKING ELLER RESERVASJONER");
            }
            else{
                faktura.setText("TAKK FOR BESØKET!");
                valgtBooking=null;
            }
        }
    } //Slutt på metoden sjekkUtAlle
    
    //Indre klasse som lytter på knappetrykk.
    private class UtsjekkingLytter implements ActionListener{
        @Override
    	public void actionPerformed( ActionEvent ae ){
            if(ae.getSource()==sjekkUt){
                sjekkUt();
            }
            else if(ae.getSource()==sjekkUtAlle){
                sjekkUtAlle();
            }
            else if(ae.getSource()==fakturer){
                visFaktura();
            }
            else if(ae.getSource()==betal){
                betal();
            }
            else if(ae.getSource()==sokeKnapp){
                romModell.clear();
                valgtBooking=null;
                finnGjest(sok.getText().trim());
            }
            else if(ae.getSource()==oppdater){
                finnKontaktpersoner(iDag);
            }
  	}
    } //Slutt på klassen InnsjekkingLytter
    
    //Indre klasse som lytter på musetrykk.
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getSource()==sok){
                if(!sok.getText().trim().matches("")){
                    sok.setText("");
                    faktura.setText("");
                    romModell.clear();
                }
            }
            if(me.getClickCount() == 2) { //Hvis dobbeltklikk
                if(me.getSource()==kontaktvelger && !kontaktModell.isEmpty()){
                    Kontaktperson valgt=kontaktvelger.getSelectedValue();
                    finnRom(valgt);
                }
            }
        } 
    }//Slutt på klassen Muselytter
    
    //Indre klasse som definerer hvordan objektene i kontaktvelgeren skal vises.
    private class KontaktListeRendrer extends JLabel implements ListCellRenderer{
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        public KontaktListeRendrer(){
            setOpaque(true);
            setIconTextGap(12);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            Kontaktperson kontakt=(Kontaktperson) value;
            setText(kontakt.getNavn());
            if (isSelected) {
                setBackground(HIGHLIGHT_COLOR);
                setForeground(Color.white);
            } 
            else {
                setBackground(Color.white);
                setForeground(Color.black);
            }
            return this;
        }
    } //Slutt på klassen KontaktListeRendrer
    
    //Indre klasse som definerer hvordan objektene i romvelgeren skal vises.
    private class RomListeRendrer extends JLabel implements ListCellRenderer{
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        public RomListeRendrer(){
            setOpaque(true);
            setIconTextGap(12);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            Rom rom=(Rom) value;
            int max=rom.getMaxPersoner();
            String type="";
            switch(max){
                case Romregister.MAXPERSONERENEROM:type="enerom";
                                                    break;
                case Romregister.MAXPERSONERDOBBELTROM:type="dobbeltrom";
                                                    break;
                case Romregister.MAXPERSONERFAMILIEROM:type="familierom";
                                                    break;
                case Romregister.MAXPERSONERSUITE:type="suite";
                                                    break;
            }
            setText(rom.getRomNr()+"     "+type);
            if (isSelected) {
                setBackground(HIGHLIGHT_COLOR);
                setForeground(Color.white);
            } 
            else {
                setBackground(Color.white);
                setForeground(Color.black);
            }
            return this;
        }
    } //Slutt på klassen RomListeRendrer
} // end of class InnsjekkingPanel