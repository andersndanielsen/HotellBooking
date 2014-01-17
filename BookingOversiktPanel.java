/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 13.05.12
****Beskrivelse:
* BookingOversiktPanel.java inneholder klassen BookingOversiktPanel,
* BookingOversiktLytter, Muselytter og GjesteListeRendrer.
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

/*Klassen definerer bookingoversiktpanelet som er en tab i TabVindu.
 * Panelet viser oversikt over hvor ledige rom og innsjekkede gjester.*/
public class BookingOversiktPanel extends JPanel{
    private JPanel venstre, hoyre, hoyre2, hoyre3, v1, vv, vh, vhh, venstre2, 
            vv2, vh2, vv3, vh3;
    private Box venstreBox, hoyreBox, hovedBox;
    private JTextField inndato, utdato, enkeltrom, dobbeltrom, familierom, suiter,
            enkeltromNaa, dobbeltromNaa, familieromNaa, suiterNaa, finnGjest,
            grupperom, moterom, auditorier, grupperomNaa, moteromNaa, auditorierNaa;
    private JButton sokDato, oppdater, innsjekkede, sok;
    private JList<Gjest> gjestevelger;
    private DefaultListModel<Gjest> gjesteModell;
    private JTextArea status;
    private BookingOversiktLytter lytter;
    private Muselytter mLytter;
    private Gjesteregister gregister;
    private Romregister rregister;
    private FlereNavnDialogVindu navneDialog;
    private TabVindu hovedvindu;
    private Gjest valgtGjest;
    
    /*Konstruktør som oppretter objekt av typen BookingOversiktPanel. 
     * Parametrene angir gjesteregisteret, romregisteret og tabvinduet.*/
    public BookingOversiktPanel(Gjesteregister g, Romregister r, TabVindu tv){
        gregister=g;
        rregister=r;
        hovedvindu=tv;
        mLytter=new Muselytter();
        lytter=new BookingOversiktLytter();
        
        inndato=new JTextField(TabVindu.DATOFELTSTR);
        inndato.addMouseListener(mLytter);
        utdato=new JTextField(TabVindu.DATOFELTSTR);
        utdato.addMouseListener(mLytter);
        enkeltrom=new JTextField(TabVindu.ROMFELTSTR);
        enkeltrom.setEditable(false);
        dobbeltrom=new JTextField(TabVindu.ROMFELTSTR);
        dobbeltrom.setEditable(false);
        familierom=new JTextField(TabVindu.ROMFELTSTR);
        familierom.setEditable(false);
        suiter=new JTextField(TabVindu.ROMFELTSTR);
        suiter.setEditable(false);
        grupperom=new JTextField(TabVindu.ROMFELTSTR);
        grupperom.setEditable(false);
        moterom=new JTextField(TabVindu.ROMFELTSTR);
        moterom.setEditable(false);
        auditorier=new JTextField(TabVindu.ROMFELTSTR);
        auditorier.setEditable(false);
        
        enkeltromNaa=new JTextField(TabVindu.ROMFELTSTR);
        enkeltromNaa.setEditable(false);
        dobbeltromNaa=new JTextField(TabVindu.ROMFELTSTR);
        dobbeltromNaa.setEditable(false);
        familieromNaa=new JTextField(TabVindu.ROMFELTSTR);
        familieromNaa.setEditable(false);
        suiterNaa=new JTextField(TabVindu.ROMFELTSTR);
        suiterNaa.setEditable(false);
        grupperomNaa=new JTextField(TabVindu.ROMFELTSTR);
        grupperomNaa.setEditable(false);
        moteromNaa=new JTextField(TabVindu.ROMFELTSTR);
        moteromNaa.setEditable(false);
        auditorierNaa=new JTextField(TabVindu.ROMFELTSTR);
        auditorierNaa.setEditable(false);
        finnGjest=new JTextField(TabVindu.DATOFELTSTR+2);
        finnGjest.addMouseListener(mLytter);
        
        sokDato=new JButton("Søk");
        sokDato.addActionListener(lytter);
        oppdater=new JButton("Oppdater");
        oppdater.addActionListener(lytter);
        innsjekkede=new JButton("Vis alle innsjekkede gjester");
        innsjekkede.addActionListener(lytter);
        sok=new JButton("Søk");
        sok.addActionListener(lytter);
        sok.addMouseListener(mLytter);
        
        gjesteModell=new DefaultListModel<>();
        gjestevelger=new JList<>(gjesteModell); //Definerer JList som inneholder innsjekkede gjester.
        gjestevelger.setVisibleRowCount(17);
        gjestevelger.setFixedCellWidth(100);
        gjestevelger.setCellRenderer(new GjesteListeRendrer());
        gjestevelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gjestevelger.addMouseListener(mLytter);
        
        status=new JTextArea(15, 25); //Statusfeltet.
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        status.setEditable(false);
        
        //Definerer panelet som viser antall ledige rom etter angitt dato.
        venstre=new JPanel(new BorderLayout());
        venstre.setBorder(BorderFactory.createTitledBorder("Ledige rom etter dato"));
        venstre.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        vv=new JPanel(new GridLayout(0, 1)); //Venstre kolonne i panelet venstre.
        vv.setBorder(new EmptyBorder(10, 10, 0, 10));
        vh=new JPanel(new GridLayout(0, 1)); //Midtre kolonne i panelet venstre.
        vh.setBorder(new EmptyBorder(10, 0, 0, 10));
        vhh=new JPanel(new GridLayout(0, 1)); //Høyre kolonne i panelet venstre.
        vhh.setBorder(new EmptyBorder(10, 0, 0, 10));
        vv2=new JPanel(new GridLayout(0, 1)); //Forts. venstre kolonne i panelet venstre.
        vv2.setBorder(new EmptyBorder(0, 10, 10, 0));
        vh2=new JPanel(new GridLayout(0, 1)); //Forts. høyre kolonne i panelet venstre.
        vh2.setBorder(new EmptyBorder(0, 0, 10, 10));
        
        vv.add(new JLabel("Ønsket innsjekking"));
        vh.add(inndato);
        vhh.add(new JLabel("(dd-mm-åååå)"));
        vv.add(new JLabel("Ønsket utsjekking"));
        vv.add(new JLabel(""));
        vh.add(utdato);
        vh.add(new JLabel(""));
        vhh.add(new JLabel("(dd-mm-åååå)"));
        vhh.add(sokDato);
        vv2.add(new JLabel("Antall enkeltrom"));
        vh2.add(enkeltrom);
        vv2.add(new JLabel("Antall dobbeltrom"));
        vh2.add(dobbeltrom);
        vv2.add(new JLabel("Antall familierom"));
        vh2.add(familierom);
        vv2.add(new JLabel("Antall suiter"));
        vh2.add(suiter);
        vv2.add(new JLabel("Antall grupperom"));
        vh2.add(grupperom);
        vv2.add(new JLabel("Antall møterom"));
        vh2.add(moterom);
        vv2.add(new JLabel("Antall auditorier"));
        vh2.add(auditorier);
        vv.setBackground(TabVindu.BAKGRUNNSFARGE);
        vh.setBackground(TabVindu.BAKGRUNNSFARGE);
        vhh.setBackground(TabVindu.BAKGRUNNSFARGE);
        vv2.setBackground(TabVindu.BAKGRUNNSFARGE);
        vh2.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        v1=new JPanel(new BorderLayout()); //Samler de øvre kolonnene i venstre.
        v1.add(vv, BorderLayout.LINE_START);
        v1.add(vh, BorderLayout.CENTER);
        v1.add(vhh, BorderLayout.LINE_END);
        v1.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        venstre.add(v1, BorderLayout.PAGE_START);
        venstre.add(vv2, BorderLayout.LINE_START);
        venstre.add(vh2, BorderLayout.LINE_END);
        
        //Definerer panelet som viser antall ledige rom akkurat nå.
        venstre2=new JPanel(new BorderLayout());
        venstre2.setBorder(BorderFactory.createTitledBorder("Ledige rom akkurat nå"));
        vv3=new JPanel(new GridLayout(0, 1)); //Venstre kolonne i panelet venstre2.
        vv3.setBorder(new EmptyBorder(10, 10, 10, 0));
        vh3=new JPanel(new GridLayout(0, 1)); //Høyre kolonne i panelet venstre2.
        vh3.setBorder(new EmptyBorder(10, 0, 10, 10));
        vv3.add(new JLabel("Antall enkeltrom"));
        vh3.add(enkeltromNaa);
        vv3.add(new JLabel("Antall dobbeltrom"));
        vh3.add(dobbeltromNaa);
        vv3.add(new JLabel("Antall familierom"));
        vh3.add(familieromNaa);
        vv3.add(new JLabel("Antall suiter"));
        vh3.add(suiterNaa);
        vv3.add(new JLabel("Antall grupperom"));
        vh3.add(grupperomNaa);
        vv3.add(new JLabel("Antall møterom"));
        vh3.add(moteromNaa);
        vv3.add(new JLabel("Antall auditorier"));
        vh3.add(auditorierNaa);
        venstre2.add(vv3, BorderLayout.LINE_START);
        venstre2.add(vh3, BorderLayout.LINE_END);
        venstre2.add(oppdater, BorderLayout.PAGE_END);
        venstre2.setBackground(TabVindu.BAKGRUNNSFARGE);
        vv3.setBackground(TabVindu.BAKGRUNNSFARGE);
        vh3.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Definerer box som inneholder panelene venstreog venstre2
        venstreBox=new Box(BoxLayout.PAGE_AXIS);
        venstreBox.add(venstre);
        venstreBox.add(Box.createRigidArea(new Dimension(0, 10)));
        venstreBox.add(venstre2);
        venstreBox.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Definerer panelet som viser label, søkefelt og -knapp på høyre side.
        hoyre=new JPanel();
        hoyre.add(new JLabel("P.nr/navn:"));
        hoyre.add(finnGjest);
        hoyre.add(sok);
        hoyre.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Definerer panelet som viser knappen for å vise alle innsjekkede gjester.
        hoyre2=new JPanel(new GridLayout(1, 0));
        hoyre2.add(innsjekkede);
        hoyre2.setBorder(new EmptyBorder(0, 10, 5, 10));
        hoyre2.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyre3=new JPanel(new GridLayout(2, 0));
        hoyre3.add(hoyre);
        hoyre3.add(hoyre2);
        hoyre3.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        hoyreBox=new Box(BoxLayout.PAGE_AXIS);
        hoyreBox.setBorder(new EmptyBorder(0, 15, 0, 0));
        hoyreBox.add(hoyre3);
        hoyreBox.add(new JScrollPane(gjestevelger)); //Legger til JListen
        hoyreBox.add(Box.createRigidArea(new Dimension(0, 13)));
        hoyreBox.add(new JScrollPane(status));
        hoyreBox.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        //Legger høyre og venstre side i en box.
        hovedBox=new Box(BoxLayout.LINE_AXIS);
        hovedBox.add(venstreBox);
        hovedBox.add(hoyreBox);
        hovedBox.setBackground(TabVindu.BAKGRUNNSFARGE);
        
        add(hovedBox, BorderLayout.LINE_START);
        
        finnLedigeRomNaa();
        finnInnsjekkede();
    } //Slutt på konstruktør.
    
    public void setValgtGjest(Gjest g){ //Setter valgt gjest lik mottatt parameter
        valgtGjest=g;
    }
    
    private void finnInnsjekkede(){ //Finner alle innsjekkede gjester og legger dem i JListen.
        gjesteModell.clear();
        Set<Rom> innsjekkedeRom=rregister.finnAlleInnsjekkedeRom();
        Iterator iter=innsjekkedeRom.iterator();
        while(iter.hasNext()){
            Rom r=(Rom)iter.next();
            Reservasjon res=r.getInnsjekketReservasjon();
            if(res!=null && res instanceof PrivatReservasjon){
                PrivatReservasjon pres=(PrivatReservasjon) res;
                Set<Person> gjester=pres.getGjester();
                if(!gjester.isEmpty()){
                    Iterator iter2=gjester.iterator();
                    while(iter2.hasNext()){
                        Person p=(Person)iter2.next();
                        if(p instanceof Kontaktperson){
                            Kontaktperson k=(Kontaktperson) p;
                            gjesteModell.addElement(k);
                        }
                        else
                            gjesteModell.addElement(p);
                    }
                }
            }
        }
    }
    
    private void finnGjest(String s){ //Finner gjest etter personnummer eller navn.
        gjestevelger.clearSelection();
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
                    navneDialog=new FlereNavnDialogVindu(hovedvindu, gjesteListe,this); //Om det finnes flere med samme navn
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
            visGjest(g);
        }
    } //Slutt på metoden finnGjest
    
    private void visGjest(Gjest g){ //Viser informasjon om mottatt gjest i tekstfelt.
        status.setText("");
        if(g==null){
            status.setText("Finner ikke gjest!");
            return;
        }
        StringBuilder bygger=new StringBuilder();
        bygger.append(g.toString());
        if(g instanceof Person){
            Person person=(Person) g;
            Rom rom=rregister.finnInnsjekketRom(person.getPersonNr());
            if(rom==null){
                bygger.append("\n\nEr ikke innsjekket"); //Om gjesten er i registeret, men ikke r innsjekket nå.
            }
            else
                bygger.append("\n\nBor på rom nr ").append(rom.getRomNr());
            if(person instanceof Kontaktperson){
                bygger.insert(0, "KONTAKTPERSON\n");
                Set<Rom> romListe=rregister.finnAlleInnsjekkedeRom(person.getPersonNr());
                if(romListe.size()>1){ //Hvis det er flere rom registrert på kontaktpersonen.
                    bygger.append("\nAndre rom:");
                    Iterator iter=romListe.iterator();
                    while(iter.hasNext()){
                        Rom annetRom=(Rom)iter.next();
                        if(annetRom.getRomNr()!=rom.getRomNr())
                            bygger.append("\n       ").append(annetRom.getRomNr());
                    }
                }
            }
        }
        status.setText(bygger.toString());
    } //Slutt på metoden visGjest
    
    //Finner antall ledige rom etter datoer angitt i inputfelter og angir dette i felter.
    private void finnLedigeRomEtterDato(){
        String fraDato=inndato.getText().trim();
        String tilDato=utdato.getText().trim();
        
        StringBuilder bygger=new StringBuilder();
        
        if(!fraDato.matches("\\d{2}-\\d{2}-20[\\d]{2}")){
            bygger.append("'Ønsket innsjekking' må velges, og være lik dd-mm-åååå!\n");
        }
        if(!tilDato.matches("\\d{2}-\\d{2}-20[\\d]{2}")){
            bygger.append("'Ønsket utsjekking' må velges, og være lik dd-mm-åååå!\n");
        }
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            blankUtDatoRom();
            return;
        }
        
        //Deler opp og gjør om input til GregorianCalendar-objekter
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
        bygger.append(Bookingregister.datoKontroll(fra, til));
        
        if(!"".equals(bygger.toString())){ //Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            blankUtDatoRom();
            return;
        }
        
        //Finner ledige rom og oppdaterer feltene med antall.
        Set<Rom> ledigeEnkeltRom=rregister.finnLedigeEnkeltrom(fra, til);
        enkeltrom.setText(ledigeEnkeltRom.size()+"");
        
        Set<Rom> ledigeDobbeltRom=rregister.finnLedigeDobbeltrom(fra, til);
        dobbeltrom.setText(ledigeDobbeltRom.size()+"");
        
        Set<Rom> ledigeFamilieRom=rregister.finnLedigeFamilierom(fra, til);
        familierom.setText(ledigeFamilieRom.size()+"");
        
        Set<Rom> ledigeSuiter=rregister.finnLedigeSuiter(fra, til);
        suiter.setText(ledigeSuiter.size()+"");
        
        Set<Rom> ledigeGrupperom=rregister.finnLedigeGrupperom(fra, til);
        grupperom.setText(ledigeGrupperom.size()+"");
        
        Set<Rom> ledigeMoterom=rregister.finnLedigeMoterom(fra, til);
        moterom.setText(ledigeMoterom.size()+"");
        
        Set<Rom> ledigeAuditorier1=rregister.finnLedigeAuditorier1(fra, til);
        Set<Rom> ledigeAuditorier2=rregister.finnLedigeAuditorier2(fra, til);
        Set<Rom> ledigeAuditorier3=rregister.finnLedigeAuditorier3(fra, til);
        int antAud=ledigeAuditorier1.size()+ledigeAuditorier2.size()+ledigeAuditorier3.size();
        auditorier.setText(antAud+"");
        //Definerer tooltiptekst for auditorier-feltet, som viser antallet ledige av de forskjellige auditoriene.
        auditorier.setToolTipText("<html>"+ledigeAuditorier1.size()+" ledig med "+Romregister.MAXPERSONERAUD1+" plasser<br/>"+
            ledigeAuditorier2.size()+" ledig med "+Romregister.MAXPERSONERAUD2+" plasser<br/>"+
            ledigeAuditorier3.size()+" ledig med "+Romregister.MAXPERSONERAUD3+" plasser</html>");
    } //Slutt på metoden finnLedigeRomEtterDato.
    
    //Finner antall ledige rom akkurat nå og angir dette i felter.
    private void finnLedigeRomNaa(){
        GregorianCalendar fra=new GregorianCalendar();
        GregorianCalendar til=new GregorianCalendar();
        
        Set<Rom> ledigeEnkeltRom=rregister.finnLedigeEnkeltrom(fra, til);
        enkeltromNaa.setText(ledigeEnkeltRom.size()+"");
        
        Set<Rom> ledigeDobbeltRom=rregister.finnLedigeDobbeltrom(fra, til);
        dobbeltromNaa.setText(ledigeDobbeltRom.size()+"");
        
        Set<Rom> ledigeFamilieRom=rregister.finnLedigeFamilierom(fra, til);
        familieromNaa.setText(ledigeFamilieRom.size()+"");
        
        Set<Rom> ledigeSuiter=rregister.finnLedigeSuiter(fra, til);
        suiterNaa.setText(ledigeSuiter.size()+"");
        
        Set<Rom> ledigeGrupperom=rregister.finnLedigeGrupperom(fra, til);
        grupperomNaa.setText(ledigeGrupperom.size()+"");
        
        Set<Rom> ledigeMoterom=rregister.finnLedigeMoterom(fra, til);
        moteromNaa.setText(ledigeMoterom.size()+"");
        
        Set<Rom> ledigeAuditorier1=rregister.finnLedigeAuditorier1(fra, til);
        Set<Rom> ledigeAuditorier2=rregister.finnLedigeAuditorier2(fra, til);
        Set<Rom> ledigeAuditorier3=rregister.finnLedigeAuditorier3(fra, til);
        int antAud=ledigeAuditorier1.size()+ledigeAuditorier2.size()+ledigeAuditorier3.size();
        auditorierNaa.setText(antAud+"");
        //Definerer tooltiptekst for auditorier-feltet, som viser antallet ledige av de forskjellige auditoriene.
        auditorierNaa.setToolTipText("<html>"+ledigeAuditorier1.size()+" ledig med "+Romregister.MAXPERSONERAUD1+" plasser<br/>"+
            ledigeAuditorier2.size()+" ledig med "+Romregister.MAXPERSONERAUD2+" plasser<br/>"+
            ledigeAuditorier3.size()+" ledig med "+Romregister.MAXPERSONERAUD3+" plasser</html>");
    }
    
    private void blankUtDatoRom(){ //Tømmer feltene som viser antall ledige rom etter dato.
        enkeltrom.setText("");
        dobbeltrom.setText("");
        familierom.setText("");
        suiter.setText("");
        grupperom.setText("");
        moterom.setText("");
        auditorier.setText("");
    } 
    
    //Indre klasse som lytter etter knappetrykk.
    private class BookingOversiktLytter implements ActionListener{
        @Override
    	public void actionPerformed( ActionEvent ae ){
            if(ae.getSource()==sokDato)
                finnLedigeRomEtterDato();
            else if(ae.getSource()==oppdater)
                finnLedigeRomNaa();
            else if(ae.getSource()==innsjekkede){
                status.setText("");
                finnInnsjekkede();
            }
            else if(ae.getSource()==sok){
                status.setText("");
                finnGjest(finnGjest.getText().trim());
            }
  	}
    } //Slutt på klassen BookingOversiktLytter.
    
    //Indre klasse som lytter etter museetrykk.
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getSource()==finnGjest){
                if(!finnGjest.getText().trim().matches("")){
                    finnGjest.setText("");
                    status.setText("");
                }
            }
            else if(me.getSource()==inndato){
                if(!inndato.getText().trim().matches("")){
                    inndato.setText("");
                    blankUtDatoRom();
                }
            }
            else if(me.getSource()==utdato){
                if(!utdato.getText().trim().matches("")){
                    utdato.setText("");
                    blankUtDatoRom();
                }
            }
            if(me.getSource()==finnGjest){
                status.setText("");
                if(!finnGjest.getText().trim().matches("")){
                    finnGjest.setText("");
                    status.setText("");
                }
            }
            
            if (me.getClickCount() == 2) { //Ved dobbeltklikk
                if(me.getSource()==gjestevelger){
                    Gjest valgt=gjestevelger.getSelectedValue();
                    if(valgt!=null)
                        visGjest(valgt);
                }
            }
        } 
    } //Slutt på klassen Muselytter.
    
    //Indre klasse som definerer rendrer for JListen gjestevalger.
    private class GjesteListeRendrer extends JLabel implements ListCellRenderer{
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        public GjesteListeRendrer(){
            setOpaque(true);
            setIconTextGap(12);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            Gjest gjest=(Gjest) value;
            setText(gjest.getNavn());
            
            if(isSelected){
                setBackground(HIGHLIGHT_COLOR);
                setForeground(Color.white);
            } 
            else{
                setBackground(Color.white);
                setForeground(Color.black);
            }
            return this;
        }
    } //Slutt på klassen GjesteListeRendrer.
} //Slutt på klassen BookingOversiktPanel.
