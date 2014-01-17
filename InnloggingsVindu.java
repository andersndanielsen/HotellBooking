/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 15.05.12
****Beskrivelse:
* InnloggingsVindu.java inneholder klassene InnloggingsVindu og Knappelytter.
*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer egendefinert dialogvindu som brukes ved åpning av programmet
 * for å logge inn ansatte eller gjester slik at riktige moduler  av programmet vises til bruker.*/
public class InnloggingsVindu extends JDialog{
    private Gjesteregister gregister;
    private JPanel felter, knapper, felt1, felt2;
    private JTextField pNr;
    private JPasswordField passord;
    private JButton loggInn, gjest;    
    private Knappelytter kLytter;
    private final String pNrRegex="\\d{11}";
    
    /*Konstruktøren oppretter InnloggingsVindu-objekt og mottar TabVindu for 
     * å bestemme hvor vinduet skal vises og gjesteregisteret.*/
    public InnloggingsVindu(TabVindu tv, Gjesteregister g){
        super(tv, "Logg inn om du er ansatt", true);
        gregister=g;
        kLytter=new Knappelytter();
        
        loggInn=new JButton("Logg inn");
        loggInn.addActionListener(kLytter);
        gjest=new JButton("Gjest");
        gjest.addActionListener(kLytter);
        
        pNr=new JTextField(TabVindu.TEKSTFELTSTR);
        passord=new JPasswordField(TabVindu.TEKSTFELTSTR);
        
        felt1=new JPanel(new GridLayout(0, 1)); //Oppretter kolonne med labeler.
        felt1.add(new JLabel("Personnummer: (12345678901)"));
        felt1.add(new JLabel("Passord: (Admin1)"));
        
        felt2=new JPanel(new GridLayout(0, 1)); //Oppretter kolonne med inputfelter.
        felt2.add(pNr);
        felt2.add(passord);
        
        felter=new JPanel(); //Oppretter panel som innholder kolonnene felt1 og felt2.
        felter.setLayout(new GridLayout(1, 0));
        felter.add(felt1);
        felter.add(felt2);
        felter.setBorder(new EmptyBorder(10, 10, 0, 10));
        
        knapper=new JPanel(); //Oppretter panel som inneholder knappene.
        knapper.setLayout(new GridLayout(1, 0));
        knapper.add(loggInn);
        knapper.add(gjest);
        knapper.setBorder(new EmptyBorder(10, 10, 10, 10));
                
        add(felter, BorderLayout.PAGE_START);
        add(knapper,BorderLayout.PAGE_END);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    } //Slutt på konstruktør.
    
    //Logger inn ansatt om personnummer og passord stemmer overens.
    private void loggInn(String persNr, String pass){ 
        if(!persNr.matches(pNrRegex)){
            beskjed("'Personnummer' må fylles inn med nøyaktig 11 siffer!");
            return;
        }
        else if(pass.length()==0){
            beskjed("'Passord' må fylles inn!");
            return;
        }
        long personNr=Long.parseLong(persNr);
        boolean innlogget=gregister.innlogging(personNr, pass);
        TabVindu.innlogget=innlogget; //Setter boolean innlogget i TabVindu til true om innloggingen gikk bra, ellers false.
        if(!innlogget){
            beskjed("Feil personnummer eller passord");
            return;
        }
        dispose();
    }
    
    private void beskjed(String streng){ //Viser dialogvindu med beskjed lik mottatt streng.
            JOptionPane.showMessageDialog(this, streng, "OBS!",JOptionPane.WARNING_MESSAGE);
    }
    
    //Indre klasse som lytter på knappetrykk.
    private class Knappelytter implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource()==loggInn){
                loggInn(pNr.getText().trim(), passord.getText().trim());
            }
            else
                dispose();
        }            
    }
} //Slutt på klassen InnloggingsVindu
  