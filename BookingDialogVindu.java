/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 11.05.12
****Beskrivelse:
* BookingDialogVindu.java inneholder klassene BookingDialogVindu, Muselytter og Knappelytter.
*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer BookingDialogVindu-objekter som er egendefinerte dialogvinduer, og her skal man fylle inn navn og person-
 *nummer på rommets gjester. Vinduet "popper" opp ved booking eller ved innsjekk om info ikke ble fyllt inn ved booking.
 *Vinduets størrelse og antall tekstfelter varierer utifra hvilket rom som blir booket.*/
public class BookingDialogVindu extends JDialog {
    private int maxPersoner;
    private Gjesteregister gregister;
    private BookingPanel bookingpanel;
    private InnsjekkingPanel innsjekkingpanel;
    private JTextArea status,forelderstatus;
    private JPanel felter,knapper,knappeomr;
    private JTextField pNr1,pNr2,pNr3,pNr4,pNr5,pNr6,pNr7,navn1,navn2,navn3,navn4,navn5,navn6,navn7;
    private JButton ok,hoppOver,hoppOverAlle, avbryt;    
    private PrivatReservasjon res;
    private Knappelytter kLytter;
    private Muselytter mLytter;
    private final String pNrRegex;
    private final String navneRegex;
    
    /*Konstruktør som oppretter objekt av typen BookingDialogVindu.
     * Parametrene angir dialogvinduets forelder, romtype, kallsted og dets statusfelt og gjesteregisteret.*/
    public BookingDialogVindu(TabVindu tv, int max, PrivatReservasjon r, JComponent jc, JTextArea jt, Gjesteregister g){
        super(tv,"Fyll inn gjester",true);
        forelderstatus=jt;
        gregister=g;
        maxPersoner=max;
        res=r;
        // Dialogvinduet skal se forskjellig ut ettersom hvor det blir kalt opp fra.
        if(jc instanceof InnsjekkingPanel){
            InnsjekkingPanel innsjekkingPanel =(InnsjekkingPanel) jc;
            innsjekkingpanel=innsjekkingPanel;
        }
        else if(jc instanceof BookingPanel){
            BookingPanel bookingPanel=(BookingPanel) jc;
            bookingpanel=bookingPanel;
        }
        
        pNrRegex="\\d{11}";
        navneRegex="[a-zæøåA-ZÆØÅ -.'`´]{2,}";
        
        kLytter=new Knappelytter();
        mLytter=new Muselytter();
        status=new JTextArea(2,27);
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        status.setEditable(false);
        
        ok=new JButton("Ok");
        ok.addActionListener(kLytter);
        if(innsjekkingpanel==null){ //Hvis kallsted er BookingPanel
            hoppOver=new JButton("Hopp over");
            hoppOver.addActionListener(kLytter);
            hoppOverAlle=new JButton("Hopp over alle");
            hoppOverAlle.addActionListener(kLytter);
        }
        else{
            avbryt=new JButton("Avbryt");
            avbryt.addActionListener(kLytter);
        }
        
        felter=new JPanel();
        felter.setLayout(new BoxLayout(felter,BoxLayout.PAGE_AXIS));
        felter.add(new JLabel("Fyll inn personnummer og navn"));
        
        opprettFelter();
        
        knappeomr=new JPanel();
        knappeomr.setLayout(new BoxLayout(knappeomr,BoxLayout.PAGE_AXIS));
        knappeomr.add(status);
        
        knapper=new JPanel();
        knapper.setLayout(new BoxLayout(knapper,BoxLayout.LINE_AXIS));
        knapper.add(ok);
        knapper.setBorder(new EmptyBorder(15, 15, 15, 15));
        if(innsjekkingpanel==null){
            knapper.add(hoppOver);
            knapper.add(hoppOverAlle);
        }
        else
            knapper.add(avbryt);
        
        knappeomr.add(knapper);        
                
        add(felter,BorderLayout.PAGE_START);
        add(new JLabel("Status"),BorderLayout.LINE_START);
        add(knappeomr,BorderLayout.PAGE_END);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    } //Slutt på konstruktør.
    
    //Oppretter riktig antall tekstfelter ettersom hvilket rom som blir booket.
    private void opprettFelter(){
        if(maxPersoner==1){
            pNr1=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr1.addMouseListener(mLytter);
            navn1=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn1.addMouseListener(mLytter);
            felter.add(new JLabel("Person 1"));
            felter.add(pNr1);
            felter.add(navn1);
        }
        else if(maxPersoner==2){
            pNr1=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr1.addMouseListener(mLytter);
            navn1=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn1.addMouseListener(mLytter);
            navn2=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn2.addMouseListener(mLytter);
            pNr2=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr2.addMouseListener(mLytter);
            felter.add(new JLabel("Person 1"));
            felter.add(pNr1);
            felter.add(navn1);
            felter.add(new JLabel("Person 2"));
            felter.add(pNr2);
            felter.add(navn2);
        }
        else if(maxPersoner==4){
            pNr1=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr1.addMouseListener(mLytter);
            navn1=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn1.addMouseListener(mLytter);
            navn2=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn2.addMouseListener(mLytter);
            pNr2=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr2.addMouseListener(mLytter);
            pNr3=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr3.addMouseListener(mLytter);
            navn3=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn3.addMouseListener(mLytter);
            navn4=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn4.addMouseListener(mLytter);
            pNr4=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr4.addMouseListener(mLytter);
            felter.add(new JLabel("Person 1"));
            felter.add(pNr1);            
            felter.add(navn1);
            felter.add(new JLabel("Person 2"));
            felter.add(pNr2);
            felter.add(navn2);
            felter.add(new JLabel("Person 3"));
            felter.add(pNr3);
            felter.add(navn3);
            felter.add(new JLabel("Person 4"));
            felter.add(pNr4);
            felter.add(navn4);
        }
        else if(maxPersoner==7){
            pNr1=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr1.addMouseListener(mLytter);
            navn1=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn1.addMouseListener(mLytter);
            navn2=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn2.addMouseListener(mLytter);
            pNr2=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr2.addMouseListener(mLytter);
            pNr3=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr3.addMouseListener(mLytter);
            navn3=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn3.addMouseListener(mLytter);
            navn4=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn4.addMouseListener(mLytter);
            pNr4=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr4.addMouseListener(mLytter);
            navn5=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn5.addMouseListener(mLytter);
            pNr5=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr5.addMouseListener(mLytter);
            pNr6=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr6.addMouseListener(mLytter);
            navn6=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn6.addMouseListener(mLytter);
            navn7=new JTextField("Fyll inn navn",TabVindu.TEKSTFELTSTR);
            navn7.addMouseListener(mLytter);
            pNr7=new JTextField("Fyll inn personnummer",TabVindu.TEKSTFELTSTR);
            pNr7.addMouseListener(mLytter);            
            felter.add(new JLabel("Person 1"));
            felter.add(pNr1);
            felter.add(navn1);
            felter.add(new JLabel("Person 2"));
            felter.add(pNr2);
            felter.add(navn2);
            felter.add(new JLabel("Person 3"));
            felter.add(pNr3);
            felter.add(navn3);
            felter.add(new JLabel("Person 4"));
            felter.add(pNr4);
            felter.add(navn4);
            felter.add(new JLabel("Person 5"));
            felter.add(pNr5);
            felter.add(navn5);
            felter.add(new JLabel("Person 6"));
            felter.add(pNr6);
            felter.add(navn6);
            felter.add(new JLabel("Person 7"));
            felter.add(pNr7);
            felter.add(navn7);
        }
    } //Slutt på metode opprettFelter().
    
    //Kontrollerer personnummer og navn, og returnerer resultatet i form av en tekst.
    private String kontrollerInput(String personNr, String navn){
        StringBuilder bygger=new StringBuilder();
        if(!personNr.matches(pNrRegex))
            bygger.append("'Personnummer' må fylles inn med nøyaktig 11 siffer!\n");
        if(!navn.matches(navneRegex))
            bygger.append("'Navn' må fylles inn med kun lovlige tegn!\n");
        return bygger.toString();
    }
    
    //Setter gjest inn i enkeltrom og i hotellets gjesteregister.
    private void leggInnIEnkeltrom(){
        StringBuilder bygger=new StringBuilder();
        String personNrRegex=pNr1.getText().trim();
        String navnet=navn1.getText().trim();
        
        // Sjekker om feltene er utfylt
        if(personNrRegex.length()==0 || personNrRegex.matches("Fyll inn personnummer")) //Dvs at feltene ikke er utfylt
            personNrRegex="99999999999";
        else{
            bygger.append(kontrollerInput(personNrRegex, navnet));
        }
        if(!"".equals(bygger.toString())){ //Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }        
        // Har metoden kommet hit betyr det at det data er riktig innfylt
        long personNr=Long.parseLong(personNrRegex);
        if(personNr==99999999999L){ //Hvis personnummer ikke ble fylt ut.
            if(bookingpanel==null){
                beskjedInnsjekk();
                return;
            }
            else{
                int valgt=beskjed();
                if(valgt==1)
                    return;
            }
        }
        else{
            Person gjest=new Person(navnet,personNr);
            Person innlagt=leggInnIGjesteregister(gjest); //Setter gjest med rett personalia inn i gjesteregisteret.
            if(innlagt==null)
                return;
            if(!res.settInnGjest(innlagt)) //Setter gjest inn i Reservasjon sin gjesteliste.
                forelderstatus.append("Problemer ved innsetting av " + innlagt.getNavn() + "\n");
            else
                forelderstatus.append(innlagt.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        dispose(); // Sletter vinduet
    } //Slutt på leggInnIEnkeltrom()
   
    //Setter gjest inn i dobbeltrom og i hotellets gjesteregister.
    private void leggInnIDobbeltrom(){
        StringBuilder bygger=new StringBuilder();
        String personNrRegex=pNr1.getText().trim();
        String navnet=navn1.getText().trim();
        String personNrRegex2=pNr2.getText().trim();
        String navnet2=navn2.getText().trim();
        
        // Sjekker om feltene er utfylt
        if(personNrRegex.length()==0 || personNrRegex.matches("Fyll inn personnummer"))
            personNrRegex="99999999999";
        else{
            bygger.append(kontrollerInput(personNrRegex, navnet));  
        }            
        if(personNrRegex2.length()==0 || personNrRegex2.matches("Fyll inn personnummer"))
            personNrRegex2="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex2, navnet2));
        }     
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }
        // Har metoden kommet hit betyr det at det data er riktig innfylt
        long personNr=Long.parseLong(personNrRegex);
        long personNr2=Long.parseLong(personNrRegex2);
        if(personNr==99999999999L && personNr2==99999999999L){ //Hvis personnummer ikke ble fylt ut.
            if(bookingpanel==null){
                beskjedInnsjekk();
                return;
            }
            else{
                int valgt=beskjed();
                if(valgt==1)
                    return;
            }
        }
        if(!(personNr==99999999999L)){
            Person gjest=new Person(navnet,personNr);
            Person innlagt=leggInnIGjesteregister(gjest); //Setter gjest med rett personalia inn i gjesteregisteret.
            if(innlagt==null)
                return;
            if(!res.settInnGjest(innlagt)) //Setter gjest inn i Reservasjon sin gjesteliste.
                forelderstatus.append("Problemer ved innsetting av " + innlagt.getNavn() + "\n");
            else
                forelderstatus.append(innlagt.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr2==99999999999L)){
            Person gjest2=new Person(navnet2,personNr2);
            Person innlagt2=leggInnIGjesteregister(gjest2);
            if(innlagt2==null)
                return;
            if(!res.settInnGjest(innlagt2))
                forelderstatus.append("Problemer ved innsetting av " + innlagt2.getNavn() +"\n");
            else
                forelderstatus.append(innlagt2.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        dispose(); //Sletter vinduet
    } //Slutt på leggInnIDobbeltrom()
    
    //Setter gjest inn i familierom og i hotellets gjesteregister.
    private void leggInnIFamilierom(){
        StringBuilder bygger=new StringBuilder();
        String personNrRegex=pNr1.getText().trim();
        String navnet=navn1.getText().trim();
        String personNrRegex2=pNr2.getText().trim();
        String navnet2=navn2.getText().trim();
        String personNrRegex3=pNr3.getText().trim();
        String navnet3=navn3.getText().trim();
        String personNrRegex4=pNr4.getText().trim();
        String navnet4=navn4.getText().trim();
        
        //Sjekker om feltene er utfylt
        if(personNrRegex.length()==0 || personNrRegex.matches("Fyll inn personnummer"))
            personNrRegex="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex, navnet));
        }            
        if(personNrRegex2.length()==0 || personNrRegex2.matches("Fyll inn personnummer"))
            personNrRegex2="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex2, navnet2));
        }
        if(personNrRegex3.length()==0 || personNrRegex3.matches("Fyll inn personnummer"))
            personNrRegex3="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex3, navnet3));
        }
        if(personNrRegex4.length()==0 || personNrRegex4.matches("Fyll inn personnummer"))
            personNrRegex4="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex4, navnet4));
        }
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }
        // Har metoden kommet hit betyr det at det data er riktig innfylt
        long personNr=Long.parseLong(personNrRegex);
        long personNr2=Long.parseLong(personNrRegex2);
        long personNr3=Long.parseLong(personNrRegex3);
        long personNr4=Long.parseLong(personNrRegex4);
        //Hvis personnummer ikke ble fylt ut:
        if(personNr==99999999999L && personNr2==99999999999L && personNr3==99999999999L && personNr4==99999999999L){
            if(bookingpanel==null){
                beskjedInnsjekk();
                return;
            }
            else{
                int valgt=beskjed();
                if(valgt==1)
                    return;
            }
        }
        if(!(personNr==99999999999L)){
            Person gjest=new Person(navnet,personNr);
            Person innlagt=leggInnIGjesteregister(gjest); //Setter gjest med rett personalia inn i gjesteregisteret.
            if(innlagt==null)
                return;
            if(!res.settInnGjest(innlagt)) //Setter gjest inn i Reservasjon sin gjesteliste.
                forelderstatus.append("Problemer ved innsetting av " + innlagt.getNavn() + "\n");
            else
                forelderstatus.append(innlagt.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr2==99999999999L)){
            Person gjest2=new Person(navnet2,personNr2); 
            Person innlagt2=leggInnIGjesteregister(gjest2);
            if(innlagt2==null)
                return;
            if(!res.settInnGjest(innlagt2))
                forelderstatus.append("Problemer ved innsetting av " + innlagt2.getNavn() +"\n");
            else
                 forelderstatus.append(innlagt2.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr3==99999999999L)){
            Person gjest3=new Person(navnet3,personNr3);
            Person innlagt3=leggInnIGjesteregister(gjest3);
            if(innlagt3==null)
                return;
            if(!res.settInnGjest(innlagt3))
                forelderstatus.append("Problemer ved innsetting av " + innlagt3.getNavn() + "\n");
            else
                forelderstatus.append(innlagt3.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr4==99999999999L)){
            Person gjest4=new Person(navnet4,personNr4);
            Person innlagt4=leggInnIGjesteregister(gjest4);
            if(innlagt4==null)
                return;
            if(!res.settInnGjest(innlagt4))
                forelderstatus.append("Problemer ved innsetting av " + innlagt4.getNavn() +"\n");
            else
                forelderstatus.append(innlagt4.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        dispose(); //Sletter vinduet
    } //Slutt på leggInnIFamilierom()
    
    //Setter gjest inn i suite og i hotellets gjesteregister.
    private void leggInnISuite(){
        StringBuilder bygger=new StringBuilder();
        String personNrRegex=pNr1.getText().trim();
        String navnet=navn1.getText().trim();
        String personNrRegex2=pNr2.getText().trim();
        String navnet2=navn2.getText().trim();
        String personNrRegex3=pNr3.getText().trim();
        String navnet3=navn3.getText().trim();
        String personNrRegex4=pNr4.getText().trim();
        String navnet4=navn4.getText().trim();
        String personNrRegex5=pNr5.getText().trim();
        String navnet5=navn5.getText().trim();
        String personNrRegex6=pNr6.getText().trim();
        String navnet6=navn6.getText().trim();
        String personNrRegex7=pNr7.getText().trim();
        String navnet7=navn7.getText().trim();
        
        // Sjekker om feltene er utfylt
        if(personNrRegex.length()==0 || personNrRegex.matches("Fyll inn personnummer"))
            personNrRegex="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex, navnet));
        }            
        if(personNrRegex2.length()==0 || personNrRegex2.matches("Fyll inn personnummer"))
            personNrRegex2="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex2, navnet2));
        }
        if(personNrRegex3.length()==0 || personNrRegex3.matches("Fyll inn personnummer"))
            personNrRegex3="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex3, navnet3));
        }
        if(personNrRegex4.length()==0 || personNrRegex4.matches("Fyll inn personnummer"))
            personNrRegex4="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex4, navnet4));
        }
        if(personNrRegex5.length()==0 || personNrRegex5.matches("Fyll inn personnummer"))
            personNrRegex5="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex5, navnet5));
        }
        if(personNrRegex6.length()==0 || personNrRegex6.matches("Fyll inn personnummer"))
            personNrRegex6="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex6, navnet6));
        }
        if(personNrRegex7.length()==0 || personNrRegex7.matches("Fyll inn personnummer"))
            personNrRegex7="99999999999";
        else{ 
            bygger.append(kontrollerInput(personNrRegex7, navnet7));
        }
        if(!"".equals(bygger.toString())){ // Hvis en eller flere feil har oppstått, så fortsetter ikke programmet.
            status.setText(bygger.toString());
            return;
        }
        // Har metoden kommet hit betyr det at det data er riktig innfylt
        long personNr=Long.parseLong(personNrRegex);
        long personNr2=Long.parseLong(personNrRegex2);
        long personNr3=Long.parseLong(personNrRegex3);
        long personNr4=Long.parseLong(personNrRegex4);
        long personNr5=Long.parseLong(personNrRegex5);
        long personNr6=Long.parseLong(personNrRegex6);
        long personNr7=Long.parseLong(personNrRegex7); 
        //Hvis personnummer ikke ble fylt ut:
        if(personNr==99999999999L && personNr2==99999999999L && personNr3==99999999999L && personNr4==99999999999L &&
                personNr5==99999999999L && personNr6==99999999999L && personNr7==99999999999L){
            if(bookingpanel==null){
                beskjedInnsjekk();
                return;
            }
            else{
                int valgt=beskjed();
                if(valgt==1)
                    return;
            }
        }
        if(!(personNr==99999999999L)){
            Person gjest=new Person(navnet,personNr);
            Person innlagt=leggInnIGjesteregister(gjest); //Setter gjest med rett personalia inn i gjesteregisteret.
            if(innlagt==null)
                return;
            if(!res.settInnGjest(innlagt)) // Setter gjest inn i Reservasjon sin gjesteliste.
                forelderstatus.append("Problemer ved innsetting av " + innlagt.getNavn() + "\n");
            else
                forelderstatus.append(innlagt.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr2==99999999999L)){
           Person gjest2=new Person(navnet2,personNr2); 
           Person innlagt2=leggInnIGjesteregister(gjest2);
           if(innlagt2==null)
               return;
           if(!res.settInnGjest(innlagt2))
               forelderstatus.append("Problemer ved innsetting av " + innlagt2.getNavn() +"\n");
           else
               forelderstatus.append(innlagt2.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr3==99999999999L)){
           Person gjest3=new Person(navnet3,personNr3);
           Person innlagt3=leggInnIGjesteregister(gjest3);
           if(innlagt3==null)
               return;
           if(!res.settInnGjest(innlagt3))
               forelderstatus.append("Problemer ved innsetting av " + innlagt3.getNavn() + "\n");
           else
               forelderstatus.append(innlagt3.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }   
        if(!(personNr4==99999999999L)){
           Person gjest4=new Person(navnet4,personNr4);
           Person innlagt4=leggInnIGjesteregister(gjest4);
           if(innlagt4==null)
               return;
           if(!res.settInnGjest(innlagt4))
               forelderstatus.append("Problemer ved innsetting av " + innlagt4.getNavn() +"\n");
           else
               forelderstatus.append(innlagt4.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr5==99999999999L)){
           Person gjest5=new Person(navnet5,personNr5);
           Person innlagt5=leggInnIGjesteregister(gjest5);
           if(innlagt5==null)
               return;
           if(!res.settInnGjest(innlagt5))
               forelderstatus.append("Problemer ved innsetting av " + innlagt5.getNavn() +"\n");
           else
               forelderstatus.append(innlagt5.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr6==99999999999L)){
           Person gjest6=new Person(navnet6,personNr6);
           Person innlagt6=leggInnIGjesteregister(gjest6);
           if(innlagt6==null)
               return;
           if(!res.settInnGjest(innlagt6))
               forelderstatus.append("Problemer ved innsetting av " + innlagt6.getNavn() + "\n");
           else
               forelderstatus.append(innlagt6.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }
        if(!(personNr7==99999999999L)){
           Person gjest7=new Person(navnet7,personNr7);
           Person innlagt7=leggInnIGjesteregister(gjest7);
           if(innlagt7==null)
               return;
           if(!res.settInnGjest(innlagt7))
               forelderstatus.append("Problemer ved innsetting av " + innlagt7.getNavn() +"\n");
           else
               forelderstatus.append(innlagt7.getNavn() + " har blitt lagt til i reservasjonen.\n");
        }    
        dispose(); // Sletter vinduet
    } //Slutt på leggInnISuite()
    
    /*Legger person som er sendt med som parameter inn i hotellets gjesteregister dersom personen ikke er der fra før.
     * Er personen der fra før av passer metoden på at riktig info blir lagret.*/
    private Person leggInnIGjesteregister(Person g){
        String navn=g.getNavn();
        long personnummer=g.getPersonNr();
        Person gjest=(Person) gregister.finnGjest(personnummer);
        if(gjest==null){ //Da finnes ikke personen i gjesteregisteret fra før av.
            if(!gregister.settInn(g)){
                forelderstatus.append("Kunne ikke registrere kontaktperson");
            }
            return g;
        }
        if(!navn.equals(gjest.getNavn())){ //Hvis utfylt navn ikke stemmer med funnet gjest sitt navn.
            StringBuilder streng=new StringBuilder();
            streng.append("Gjest med dette personnummeret eksisterer allerede, vennligst kontroller:\n");
            streng.append("Opprinnelig informasjon:\n").append(gjest.toString()).append("\n\n");
            streng.append("Ny informasjon:\n").append(g.toString());
            String[] valg = { "Behold opprinnelig informasjon", "Oppdater", "Avbryt" };
            int valgt=JOptionPane.showOptionDialog(this, streng.toString(), "Velg riktig informasjon",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
            if(valgt==0)
                forelderstatus.setText("Beholdt opprinnelig kontaktinformasjon!\n");
            if(valgt==1){
                gjest.setNavn(navn); //Oppdaterer funnet gjest sitt navn.
                forelderstatus.setText("Kontaktinformasjon ble oppdatert!\n");
            }
            if(valgt==2){
                return null;
            }               
            return gjest;
        }
        return gjest;
    }
    
    //Viser et dialogvindu hvor man får to valg.
    private int beskjed(){
        String[] valg = { "Gå videre", "Gå tilbake" };
            int valgt=JOptionPane.showOptionDialog(this, "Ingen personer er lagt til i rommet.\nDisse må legges inn nå eller ved innsjekking", "OBS!",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, valg, valg[0]);
            
            return valgt;
    }
    
    //Viser et dialogvindu hvor man får to valg. Blir brukt når kallsted på denne klassen er InnsjekkingPanel.
    private void beskjedInnsjekk(){
            JOptionPane.showMessageDialog(this, "Ingen personer er lagt til i rommet.\nDisse må legges inn nå for å sjekke inn!", "OBS!",JOptionPane.WARNING_MESSAGE);
    }
    
    //En indre klasse som lytter på knappetrykk.
    private class Knappelytter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource()==ok){
                if(maxPersoner==1){
                    leggInnIEnkeltrom();
                }                    
                if(maxPersoner==2){
                    leggInnIDobbeltrom();
                }
                if(maxPersoner==4){
                    leggInnIFamilierom();
                }
                if(maxPersoner==7){
                    leggInnISuite();
                }
            }
            else if(ae.getSource()==hoppOver){
                int valgt=beskjed();
                if(valgt==0)
                    dispose();
            }
            else if(ae.getSource()==hoppOverAlle){
                bookingpanel.setAvbrytelseDialogVindu(true);
                int valgt=beskjed();
                if(valgt==0)
                    dispose();                
            }
            else if(ae.getSource()==avbryt){
                innsjekkingpanel.setAvbrytelseDialogVindu(true);
                beskjedInnsjekk();
                dispose();                
            }
        }            
    }
    
    //En indre klasse som lytter på musetrykk
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getSource() == pNr1) 
                pNr1.setText(""); 
            else if(me.getSource()==navn1)
                navn1.setText("");
            else if(me.getSource()==pNr2)
                pNr2.setText("");
            else if(me.getSource()==navn2)
                navn2.setText("");
            else if(me.getSource() == pNr3) 
                pNr3.setText(""); 
            else if(me.getSource()==navn3)
                navn3.setText("");
            else if(me.getSource()==pNr4)
                pNr4.setText("");
            else if(me.getSource()==navn4)
                navn4.setText("");
            else if(me.getSource()==pNr5)
                pNr5.setText("");
            else if(me.getSource()==navn5)
                navn5.setText("");
            else if(me.getSource() == pNr6) 
                pNr6.setText(""); 
            else if(me.getSource()==navn6)
                navn6.setText("");
            else if(me.getSource()==pNr7)
                pNr7.setText("");
            else if(me.getSource()==navn7)
                navn7.setText("");
        }
    }    
} //Slutt på klassen BookingDialogVindu.
