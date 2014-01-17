/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 13.05.12
****Beskrivelse:
* InnsjekkingPanel.java inneholder klassene InnsjekkingPanel, InnsjekkingLytter, Muselytter, PersonListeRendere og 
* RomListeRenderer.
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer InnsjekkingPanel-objekter som er en tab i TabVindu sin JTabbedPane. Denne delen av vinduet utfører 
 * innsjekkingen.*/
public class InnsjekkingPanel extends Box{
    private JPanel venstreTopp, venstre, hoyre, bunn, vv, vh, hv, hh, b1, b2, topp, t, nederst, bunnJList;
    private JTextField sok, personnummer, navn, adresse, postnummer, poststed, telefon,
            gjester, enkeltrom, dobbeltrom, familierom, suiter, inndato, utdato,
            antHalv, antHel;
    private JButton sokeKnapp, sjekkInn, sjekkInnAlle;
    private JTextArea onsker,status;
    private JList<Rom> romvelger;
    private JList<Person> personvelger;
    private DefaultListModel<Rom> romModell;
    private DefaultListModel<Person> navneModell;
    private InnsjekkingLytter lytter;
    private Muselytter mLytter;
    private Gjesteregister gregister;
    private Bookingregister bregister;                       
    private Romregister rregister;
    private GregorianCalendar iDag;
    private FlereNavnDialogVindu navneDialog;
    private BookingDialogVindu bookingDialog;
    private TabVindu hovedvindu;
    private Gjest valgtGjest; //Gjest som blir søkt opp.
    private boolean avbrytelseDialogVindu; //Blir satt til true i BookingDialogVindu om man avbryter innlegging av gjester.
    
    /*Konstruktør som oppretter et objekt av typen InsjekkingPanel.
     * Parametrene angir hotellets gjesteregister, bookingregister og romregister samt tabens forelder.*/
    public InnsjekkingPanel(Gjesteregister g, Bookingregister b, Romregister r,TabVindu tv){
        super(BoxLayout.PAGE_AXIS);
        gregister=g;
        bregister=b;
        rregister=r;
        hovedvindu=tv;
        avbrytelseDialogVindu=false;
        lytter=new InnsjekkingLytter();
        mLytter=new Muselytter();
        navneModell=new DefaultListModel<>();
        personvelger= new JList<>(navneModell);
        personvelger.setVisibleRowCount(13);
        personvelger.setFixedCellWidth (180);
        personvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personvelger.setCellRenderer( new PersonListeRenderer() );
        personvelger.addMouseListener(mLytter);
        romModell=new DefaultListModel<>();
        romvelger = new JList<>(romModell);
        romvelger.setVisibleRowCount(13);
        romvelger.setFixedCellWidth(180);
        romvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        romvelger.setCellRenderer( new RomListeRenderer() );
        romvelger.addMouseListener(mLytter);
        
        sok=new JTextField(TabVindu.TEKSTFELTSTR);
        sok.addMouseListener(mLytter);
        personnummer=new JTextField(TabVindu.TEKSTFELTSTR);
        personnummer.setEditable(false);
        navn=new JTextField(TabVindu.TEKSTFELTSTR);
        navn.setEditable(false);
        adresse=new JTextField(TabVindu.TEKSTFELTSTR);
        adresse.setEditable(false);
        postnummer=new JTextField(TabVindu.TEKSTFELTSTR);
        postnummer.setEditable(false);
        poststed=new JTextField(TabVindu.TEKSTFELTSTR);
        poststed.setEditable(false);
        telefon=new JTextField(TabVindu.TEKSTFELTSTR);
        telefon.setEditable(false);
        gjester=new JTextField(TabVindu.ROMFELTSTR);
        gjester.setEditable(false);
        antHalv=new JTextField(TabVindu.ROMFELTSTR);
        antHalv.setEditable(false);
        antHel=new JTextField(TabVindu.ROMFELTSTR);
        antHel.setEditable(false);
        enkeltrom=new JTextField(TabVindu.ROMFELTSTR);
        enkeltrom.setEditable(false);
        dobbeltrom=new JTextField(TabVindu.ROMFELTSTR);
        dobbeltrom.setEditable(false);
        familierom=new JTextField(TabVindu.ROMFELTSTR);
        familierom.setEditable(false);
        suiter=new JTextField(TabVindu.ROMFELTSTR);
        suiter.setEditable(false);
        inndato=new JTextField(TabVindu.DATOFELTSTR);
        inndato.setEditable(false);
        utdato=new JTextField(TabVindu.DATOFELTSTR);
        utdato.setEditable(false);
        
        sokeKnapp=new JButton("Søk");
        sokeKnapp.addActionListener(lytter);
        sjekkInn=new JButton("Sjekk inn");
        sjekkInn.addActionListener(lytter);
        sjekkInnAlle=new JButton("Sjekk alle");
        sjekkInnAlle.addActionListener(lytter);
        
        onsker=new JTextArea(6, 28);
        onsker.setLineWrap(true);
        onsker.setWrapStyleWord(true);
        onsker.addMouseListener(mLytter);
        onsker.setText("Spesielle ønsker vises her");
        onsker.setEditable(false);
        status=new JTextArea(13, 28);
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        status.setEditable(false);
        
        venstreTopp=new JPanel(new FlowLayout(FlowLayout.LEFT)); //Øverste linje (med søkefelt) i panelet topp
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
        
        topp=new JPanel(new BorderLayout());
        t=new JPanel(new FlowLayout(10));
        t.add(venstre);
        t.add(hoyre);
        t.setBackground(TabVindu.BAKGRUNNSFARGE);
        topp.add(venstreTopp, BorderLayout.PAGE_START);
        topp.add(t, BorderLayout.LINE_START); //Legger høyre og venstre i LINE_START, så de ikke flytter seg
        topp.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        b1=new JPanel(new GridLayout(3, 0, 10, 10)); //Datolabeler, -felt og -knapper i panelet bunn
        b1.setBorder(new EmptyBorder(4, 10, 20, 0));
        b2=new JPanel(new GridLayout(1, 0)); //Ønskefelt i panelet bunn
        b2.setBorder(new EmptyBorder(4, 0, 15, 10));
        b1.add(new JLabel("Ønsket innsjekking"));
        b1.add(inndato);
        b1.add(new JLabel("(dd-mm-åååå)"));
        b1.add(new JLabel("Ønsket utsjekking"));
        b1.add(utdato);
        b1.add(new JLabel("(dd-mm-åååå)"));
        b1.add(sjekkInn);
        b1.add(sjekkInnAlle);
        b2.add(new JScrollPane(onsker));
        b1.setBackground(TabVindu.BAKGRUNNSFARGE);
        b2.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        bunnJList=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bunnJList.add(new JScrollPane(romvelger));
        bunnJList.add(new JScrollPane(personvelger));
        bunnJList.add(new JScrollPane(status));
        bunnJList.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        bunn=new JPanel(new BorderLayout()); //Panel i panel nederst med datoer, knapper og ønskefelt
        bunn.add(b1, BorderLayout.LINE_START);
        bunn.add(b2, BorderLayout.LINE_END);
        bunn.add(bunnJList, BorderLayout.PAGE_END);
        bunn.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        nederst=new JPanel(new BorderLayout());
        nederst.add(bunn, BorderLayout.LINE_START);
        nederst.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        add(topp);
        add(nederst);
    }// Slutt på konstruktør
    
    //Setter valgtGjest lik paramter g
    public void setValgtGjest(Gjest g){
        valgtGjest=g;
    }
    
    //Setter datafeltet som angir om vi har avbrutt BookingDialogVinduene som "popper" opp med verdien i parameteret.
    public void setAvbrytelseDialogVindu(boolean t){
        avbrytelseDialogVindu=t;
    }
    
    /*Finner alle rom som valgtGjest har reservert i dag, og returnerer true hvis det finnes noen reservasjoner eller false
     * i motsatt tilfelle. Metoden fyller klassens JList med disse rommene.*/
    private boolean finnRom(){
        iDag=new GregorianCalendar();
        if(valgtGjest instanceof Kontaktperson){ // Kontaktperson kan ha flere rom reservert samme dag
            Kontaktperson k=(Kontaktperson) valgtGjest;
            Set<Rom> reserverteRom=rregister.finnAlleReserverteRom(iDag, k.getPersonNr());
            Iterator<Rom> iter=reserverteRom.iterator();
            while(iter.hasNext()){
                romModell.addElement(iter.next());
            }
        }
        else{ //Da er det max et rom reservert på denne personen.
            Person p=(Person) valgtGjest;
            Rom ettRom=rregister.finnReserverteRom(iDag, p.getPersonNr());
            romModell.addElement(ettRom);
        }
        return !romModell.isEmpty(); //Returnerer true hvis lista ikke er tom, og false hvis den er tom.
    }
    
    /*Søker etter gjest i hotellets gjesteregister med tekststrengen angitt i parameteren. Ved treff sjekker metoden om
     * gjest er Kontaktperson eller Person. Hvis Kontaktperson kalles metoden finnBooking() opp. Hvis Person, 
     * kalles metoden finnRom() opp.*/
    private void finnGjest(String s){
        if(!sokeKontroll(s))
            return;
        valgtGjest=null;
        if(s.matches("\\d{11}")){ //Hvis det er søkt med personnummer
            long pNr=Long.parseLong(s);
            valgtGjest=gregister.finnGjest(pNr);
        }
        else{ //Hvis det er søkt med navn
            Set<Gjest> gjesteListe=gregister.finnGjest(s);
            Iterator<Gjest> iter=gjesteListe.iterator();
            if(gjesteListe.size()==1)
                valgtGjest=iter.next();
            else if(gjesteListe.size()>1){
                navneDialog=new FlereNavnDialogVindu(hovedvindu, gjesteListe,this);
                navneDialog.setLocationRelativeTo(this);
                navneDialog.setVisible(true);
            }
        }        
        if(valgtGjest==null){
            status.setText("Finner ikke gjest");
            return;
        }
        if(valgtGjest instanceof Kontaktperson)
            finnBooking();
        else if(valgtGjest instanceof Person){
            if(!finnRom())
                status.setText("Fant ingen rom som skal sjekkes inn i dag");
        }
    } //Slutt på metoden finnGjest(String s)
    
    //Metoden søker etter booking som skal sjekkes inn i dag, og fyller ut tekstfeltene med info hvis treff.
    private void finnBooking(){
        iDag=new GregorianCalendar();
        Kontaktperson kontakt=(Kontaktperson) valgtGjest;
        Booking booking=bregister.finnBooketNaa(iDag, kontakt.getPersonNr());
        if(booking==null || booking.getFraDato().get(GregorianCalendar.DAY_OF_YEAR)!=iDag.get(GregorianCalendar.DAY_OF_YEAR)){
            status.setText("Fant ingen bookinger på denne personen");
            return;   
        }
        personnummer.setText(kontakt.getPersonNr()+"");
        navn.setText(kontakt.getNavn());
        adresse.setText(kontakt.getAdresse());
        postnummer.setText(kontakt.getPostNr()+"");
        poststed.setText(kontakt.getPostSted());
        telefon.setText(kontakt.getTlfNr()+"");
        gjester.setText(booking.getAntGjester()+"");
        enkeltrom.setText(booking.getAntEnerom()+"");
        dobbeltrom.setText(booking.getAntDobbeltrom()+"");
        familierom.setText(booking.getAntFamilierom()+"");
        suiter.setText(booking.getAntSuite()+"");
        antHalv.setText(booking.getAntHalvpensjon()+"");
        antHel.setText(booking.getAntHelpensjon()+"");
        
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
        onsker.setText(booking.getOnsker());
        
        finnRom();
    } //Slutt på metode finnBooking()
    
    /*Finner alle gjester som er booka inn i et rom, og fyller navneModell(modellen til personvelger) med disse personenen.
     * Hvis gjester ikke er booka inn må det gjøres nå.*/
    private void finnGjester(Rom r, PrivatReservasjon res){        
        navneModell.clear(); //Nuller ut lista
        Set<Person> rommetsGjester=res.getGjester(); 
        if(!rommetsGjester.isEmpty()){
            Iterator<Person> iter=rommetsGjester.iterator();
            while(iter.hasNext()){
                Person neste=iter.next();
                navneModell.addElement(neste); 
            }
        }
        else{ //Hvis ingen er booka inn i rommet åpnes BookingDialogVindu.
            visBookingDialog(r.getMaxPersoner(), res);
        }
    }
    
    //Oppretter BookingDialogVindu
    private void visBookingDialog(int max, PrivatReservasjon r){
        bookingDialog=new BookingDialogVindu(hovedvindu, max, r, this, status, gregister);
        bookingDialog.setLocationRelativeTo(this);
        bookingDialog.setVisible(true);
    }
    
    // Kontrollerer inntasting ved søking, og returnerer true ved godkjent inntasting og false i motsatt tilfelle.
    private boolean sokeKontroll(String s){
        if(s.length()==0){
            status.setText("Søkefeltet må fylles med \nnavn eller personnummer!");
            return false;
        }
        if(!s.matches("\\d{11}")){
            status.setText("Personnummer må fylles inn med \nnøyaktig 11 siffer,\n");
        }
        else
            return true;
        if(!s.matches("[a-zæøåA-ZÆØÅ -.'`´]{2,}")){
            status.append("Navn må fylles inn med kun \nbokstaver!\n");
            return false;
        }
        else{
            status.setText("");
            return true;
        }
    }
    
    //Sjekker inn valgt rom
    private void sjekkInn(){
        iDag=new GregorianCalendar();
        Rom valgtRom=romvelger.getSelectedValue();
        PrivatReservasjon reservasjon;
        if(valgtRom!=null){
            if(valgtGjest instanceof Kontaktperson){
                Kontaktperson ktype=(Kontaktperson) valgtGjest;
                reservasjon=(PrivatReservasjon) valgtRom.getKontaktReservasjon(iDag, ktype.getPersonNr()); 
            }
            else{
                Person ptype=(Person) valgtGjest;
                reservasjon=(PrivatReservasjon) valgtRom.getReservasjon(iDag, ptype.getPersonNr());
            }
            if(!reservasjon.getInnsjekket()){
                if(reservasjon.getGjester().isEmpty()){ //Hvis ingen gjester er booka inn på rommet
                    avbrytelseDialogVindu=false;
                    visBookingDialog(valgtRom.getMaxPersoner(), reservasjon);
                    if(avbrytelseDialogVindu){
                        status.setText("Innsjekkingen ble avbrutt!");
                        return;
                    }
                }
                if(reservasjon.setInnsjekket(true))
                    status.setText("Rom nr "+valgtRom.getRomNr()+" er nå innsjekket");
            }
            else
                status.setText("Rom nr "+valgtRom.getRomNr()+" er allerede innsjekket");
        }
        else
            status.setText("Det er ikke valgt noe rom \nå sjekke inn");
    } //Slutt på metoden sjekkInn()
    
    //Sjekker inn alle rom på en kontaktperson
    private void sjekkInnAlle(){
        iDag=new GregorianCalendar();
        status.setText("");
        if(valgtGjest instanceof Kontaktperson){
            Kontaktperson k=(Kontaktperson) valgtGjest;
            Set<Rom> romListe=rregister.finnAlleReserverteRom(iDag, k.getPersonNr());
            Iterator iter=romListe.iterator();
            while(iter.hasNext()){
                Rom neste=(Rom)iter.next();
                PrivatReservasjon reservasjon=neste.getKontaktReservasjon(iDag, k.getPersonNr()); 
                if(!reservasjon.getInnsjekket()){
                    if(reservasjon.getGjester().isEmpty()){
                        avbrytelseDialogVindu=false;
                        visBookingDialog(neste.getMaxPersoner(), reservasjon);
                        if(avbrytelseDialogVindu){
                            status.setText("Innsjekkingen ble avbrutt!");
                            return;
                        }
                    }
                    if(reservasjon.setInnsjekket(true))
                       status.append("Rom nr "+neste.getRomNr()+" er nå innsjekket\n");
                }
                else
                    status.append("Rom nr "+neste.getRomNr()+" er allerede innsjekket\n");
            }
        }
        else
            status.setText("Det er bare kontaktpersonen som kan sjekke inn alle bookede rom!");
    } //Slutt på metoden sjekkInnAlle()
    
    //Nuller ut alle tekstfelt
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
        navneModell.clear();
        romModell.clear();
    }
    
    //Indre klasse som lytter på knappetrykk
    private class InnsjekkingLytter implements ActionListener{
        @Override
    	public void actionPerformed( ActionEvent ae ){
            if(ae.getSource()==sjekkInn){
                sjekkInn();
            }
            else if(ae.getSource()==sjekkInnAlle){
                sjekkInnAlle();
            }
            else if(ae.getSource()==sokeKnapp){
                nullUtTekstfelt();
                finnGjest(sok.getText().trim());
            }
  	}
    } //Slutt på klassen InnsjekkingLytter
    
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me) {
            if(me.getSource() == sok){
                if(!sok.getText().trim().matches("")){
                    sok.setText("");
                    nullUtTekstfelt();
                }
            }
            if (me.getClickCount() == 2) {
                PrivatReservasjon reservasjon;
                Rom valgtRom;
                if(me.getSource()==romvelger){
                    valgtRom=romvelger.getSelectedValue();
                    if(valgtRom!=null){
                        if(valgtGjest instanceof Kontaktperson){                          
                            Kontaktperson ktype=(Kontaktperson) valgtGjest;
                            reservasjon=(PrivatReservasjon) valgtRom.getKontaktReservasjon(iDag, ktype.getPersonNr());
                        }
                        else{
                            Person ptype=(Person) valgtGjest;
                            reservasjon=(PrivatReservasjon) valgtRom.getReservasjon(iDag, ptype.getPersonNr());
                            
                        }
                        finnGjester(valgtRom, reservasjon);
                    }
                }
            }
        }
    } //Slutt på klassen Muselytter
} // Slutt på klassen InnsjekkingPanel

//Denne klassen gir farge til en JList og beskriver hvordan et objekt skal vises.
class PersonListeRenderer extends JLabel implements ListCellRenderer{
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    
    public PersonListeRenderer() {
    setOpaque(true);
    setIconTextGap(12);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Person person=(Person) value;
        setText(person.getNavn());
        
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
    
} //Slutt på klassen PersonListeRenderer

//Denne klassen gir farge til en JList og beskriver hvordan et objekt skal vises.
class RomListeRenderer extends JLabel implements ListCellRenderer{
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    
    public RomListeRenderer() {
    setOpaque(true);
    setIconTextGap(12);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Rom rom=(Rom) value;
        setText(rom.getRomNr() + "");
        
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
    
} //Slutt på klassen RomListeRenderer
