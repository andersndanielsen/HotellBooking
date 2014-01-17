/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 17.05.12
****Beskrivelse:
* InfoPanel.java inneholder klassen InfoPanel.
*/

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer informasjonspanelet som er en tab i TabVindu.
 * Panelet viser diverse informasjon ment for gjester og potensielle gjester av hotellet.*/
public class InfoPanel extends JPanel{
    JLabel tekst, bilderamme, undertekst;
    Icon hotell;
    
    //Konstruktøren oppretter InfoPanel-objektet.
    public InfoPanel(){
        setLayout(new BorderLayout());
        
        tekst=new JLabel("Velkommen til Hotell Nødzau",SwingConstants.CENTER);
        tekst.setFont(new Font("Helvetica", Font.BOLD,30));
        tekst.setBorder(new EmptyBorder(20,0,0,0));
        undertekst=new JLabel("Mer info kommer...",SwingConstants.CENTER);
        undertekst.setFont(new Font("Helvetica", Font.BOLD,20));
        hotell=new ImageIcon(getClass().getResource("hotelnodzau.gif"));
        bilderamme=new JLabel(hotell);
        bilderamme.setBorder(new EmptyBorder(0,0,50,0));
        
        add(tekst,BorderLayout.PAGE_START);
        add(undertekst,BorderLayout.CENTER);
        add(bilderamme,BorderLayout.PAGE_END);
    }
} //Slutt på klassen InfoPanel