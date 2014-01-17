/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 09.05.12
****Beskrivelse:
* FlereNavnDialogVindu.java inneholder klassene FlereNavnDialogVindu, Knappelytter, Muselytter og 
* FlereNavnListeRenderer.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer FlereNavnDialogVindu-objekter som er egendefinerte dialogvinduer, og her skal man velge mellom 
 * flere personer. Dette vinduet "popper" opp når man søker i BookingPanel, InnsjekkingPanel, BookingOversikt eller 
 * UtsjekkingPanel på navn og det finnes flere personer med samme navn.*/
public class FlereNavnDialogVindu extends JDialog {
    private InnsjekkingPanel innsjekking;
    private BookingPanel booking;
    private BookingOversiktPanel oversikt;
    private UtsjekkingPanel utsjekking;
    private JPanel hovedPanel, knappePanel;
    private JButton ok, avbryt;
    private Knappelytter kLytter;
    private Muselytter mLytter;
    private JList<Gjest> personvelger;
    private DefaultListModel<Gjest> modell;
    private Set<Gjest> liste;
    
    /*Konstruktør som oppretter objekt av typen FlereNavnDialogVindu.
     * Parametrene angir dialogvinduets forelder, gjestene og kallstedet.*/
    public FlereNavnDialogVindu(TabVindu tv, Set<Gjest> set,JComponent jc){
        super(tv,"Flere treff",true);
        if(jc instanceof InnsjekkingPanel){
            InnsjekkingPanel innsjekkingPanel =(InnsjekkingPanel) jc;
            innsjekking=innsjekkingPanel;
        }
        else if(jc instanceof BookingPanel){
            BookingPanel bookingpanel=(BookingPanel) jc;
            booking=bookingpanel;
        }
        else if(jc instanceof BookingOversiktPanel){
            BookingOversiktPanel oversiktpanel=(BookingOversiktPanel) jc;
            oversikt=oversiktpanel;
        }
        else if(jc instanceof UtsjekkingPanel){
            UtsjekkingPanel utsjekkingpanel=(UtsjekkingPanel) jc;
            utsjekking=utsjekkingpanel;
        }
        liste=set;
        kLytter=new Knappelytter();
        mLytter=new Muselytter();
        modell=new DefaultListModel<>();
        personvelger= new JList<>(modell);
        personvelger.setVisibleRowCount(7);
        personvelger.setFixedCellWidth( 170 );
        personvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personvelger.setCellRenderer( new FlereNavnListeRenderer() );
        personvelger.addMouseListener(mLytter);
        
        ok=new JButton("Ok");
        ok.addActionListener(kLytter);
        avbryt=new JButton("Avbryt");
        avbryt.addActionListener(kLytter);
        
        hovedPanel=new JPanel();
        hovedPanel.setLayout(new BoxLayout(hovedPanel,BoxLayout.PAGE_AXIS));
        hovedPanel.add(personvelger);
        
        knappePanel=new JPanel();
        knappePanel.setLayout(new BoxLayout(knappePanel,BoxLayout.LINE_AXIS));
        knappePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        knappePanel.add(ok);
        knappePanel.add(avbryt); 
        
        add(new JLabel("Velg riktig person:"),BorderLayout.PAGE_START);
        add(hovedPanel,BorderLayout.LINE_START);
        add(knappePanel,BorderLayout.PAGE_END);
        fyllListe();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();   
     } //Slutt på konstruktør
    
    //Fyller klassens JList med Booking-objekter.
    private void fyllListe(){
        Iterator<Gjest> iter=liste.iterator();
        while(iter.hasNext()){
            Gjest neste=iter.next();
            modell.addElement(neste);
        }
    }
    
    //Indre klasse som lytter på knappetrykk
    private class Knappelytter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource()==ok){
                Gjest valgtGjest=personvelger.getSelectedValue();
                if(valgtGjest!=null && innsjekking!=null)
                    innsjekking.setValgtGjest(valgtGjest);                
                else if(valgtGjest!=null && booking!=null)
                    booking.setValgtGjest(valgtGjest);
                else if(valgtGjest!=null && oversikt!=null)
                    oversikt.setValgtGjest(valgtGjest);
                else if(valgtGjest!=null && utsjekking!=null)
                    utsjekking.setValgtGjest(valgtGjest);
                dispose();
            }
            else if(ae.getSource()==avbryt){
                dispose();
            }
        }
    } //Slutt på klassen Knappelytter
    
    //Indre klasse som lytter på knappetrykk
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getClickCount()==2){
                Gjest valgtGjest=personvelger.getSelectedValue();
                if(valgtGjest!=null && innsjekking!=null)
                    innsjekking.setValgtGjest(valgtGjest);                
                else if(valgtGjest!=null && booking!=null)
                    booking.setValgtGjest(valgtGjest);
                else if(valgtGjest!=null && oversikt!=null)
                    oversikt.setValgtGjest(valgtGjest);
                else if(valgtGjest!=null && utsjekking!=null)
                    utsjekking.setValgtGjest(valgtGjest);
                dispose();
            }                
        }
    } //Slutt på klassen Muselytter
} //Slutt på klassen FlereNavnDialogVindu

//Denne klassen gir farge til en JList og beskriver hvordan et objekt skal vises.
class FlereNavnListeRenderer extends JLabel implements ListCellRenderer{
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    
    public FlereNavnListeRenderer() {
    setOpaque(true);
    setIconTextGap(12);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Person person=(Person) value;
        setText(person.getNavn() + ", " + person.getPersonNr());
        
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
