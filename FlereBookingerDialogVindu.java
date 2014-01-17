/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 09.05.12
****Beskrivelse:
* FlereBookingerDialogVindu.java inneholder klassene FlereBookingerDialogVindu, Knappelytter, Muselytter og 
* FlereBookingerListeRenderer.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer FlereBookingerDialogVindu-objekter som er egendefinerte dialogvinduer, og her skal man velge mellom 
 * flere bookinger. Dette vinduet "popper" opp når man søker i BookingPanel, og det finnes flere bookinger på en person.*/
public class FlereBookingerDialogVindu extends JDialog{
    private BookingPanel bookingpanel;
    private JPanel hovedPanel,knappePanel;
    private JButton ok,avbryt;
    private Knappelytter kLytter;
    private Muselytter mLytter;
    private JList<Booking> bookingvelger;
    private DefaultListModel<Booking> modell;
    private Set<Booking> liste;
    
    /*Konstruktør som oppretter objekt av typen FlereBookingerDialogVindu.
     * Parametrene angir dialogvinduets forelder, bookingene og kallstedet.*/
    public FlereBookingerDialogVindu(TabVindu tv,Set<Booking> set,BookingPanel bp){
        super(tv,"Flere treff",true);
        bookingpanel=bp;
        liste=set;
        kLytter=new Knappelytter();
        mLytter=new Muselytter();
        modell=new DefaultListModel<>();
        bookingvelger= new JList<>(modell);
        bookingvelger.setVisibleRowCount(7);
        bookingvelger.setFixedCellWidth( 170 );
        bookingvelger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingvelger.setCellRenderer( new FlereBookingerListeRenderer() );
        bookingvelger.addMouseListener(mLytter);
        
        ok=new JButton("Ok");
        ok.addActionListener(kLytter);
        avbryt=new JButton("Avbryt");
        avbryt.addActionListener(kLytter);
        
        hovedPanel=new JPanel();
        hovedPanel.setLayout(new BoxLayout(hovedPanel,BoxLayout.PAGE_AXIS));
        hovedPanel.add(bookingvelger);
        
        knappePanel=new JPanel();
        knappePanel.setLayout(new BoxLayout(knappePanel,BoxLayout.LINE_AXIS));
        knappePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        knappePanel.add(ok);
        knappePanel.add(avbryt); 
        
        add(new JLabel("Velg riktig booking:"),BorderLayout.PAGE_START);
        add(hovedPanel,BorderLayout.LINE_START);
        add(knappePanel,BorderLayout.PAGE_END);
        fyllListe();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }
    
    //Fyller klassens JList med Booking-objekter.
    private void fyllListe(){
        Iterator<Booking> iter=liste.iterator();
        while(iter.hasNext()){
            Booking neste=iter.next();
            modell.addElement(neste);
        }
    }
    
    //Indre klasse som lytter på knappetrykk
    private class Knappelytter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource()==ok){
                Booking valgtBooking=bookingvelger.getSelectedValue();
                if(valgtBooking!=null){
                    bookingpanel.setValgtBooking(valgtBooking); 
                }
                dispose();
            }
            else if(ae.getSource()==avbryt){
                dispose();
            }
        }
    } //Slutt på klassen Knappelytter
    
    //Indre klasse som lytter på musetrykk
    private class Muselytter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent me){
            if(me.getClickCount()==2){
                Booking valgtBooking=bookingvelger.getSelectedValue();
                if(valgtBooking!=null)
                    bookingpanel.setValgtBooking(valgtBooking); 
                dispose();
            }
        }
    } //Slutt på klassen Muselytter
} //Slutt på klassen FlereBookingerDialogVindu

//Denne klassen gir farge til en JList og beskriver hvordan et objekt skal vises.
class FlereBookingerListeRenderer extends JLabel implements ListCellRenderer{
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    
    public FlereBookingerListeRenderer() {
    setOpaque(true);
    setIconTextGap(12);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Booking booking=(Booking) value;
        GregorianCalendar fraDato=booking.getFraDato();
        GregorianCalendar tilDato=booking.getTilDato();
        setText(fraDato.get(GregorianCalendar.DAY_OF_MONTH)+"."+(fraDato.get(GregorianCalendar.MONTH)+1)+"."+
                fraDato.get(GregorianCalendar.YEAR)+" - "+
                tilDato.get(GregorianCalendar.DAY_OF_MONTH)+"."+(tilDato.get(GregorianCalendar.MONTH)+1)+"."+
                tilDato.get(GregorianCalendar.YEAR));
        
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
} //Slutt på klassen FlereBookingerListeRenderer
