/* Laget av
****Magnus Jårem Moltzau, s180473
****Anders Nødland Danielsen, s180475
****siste oppdatering: 13.05.12
****Beskrivelse:
* Tabindu.java inneholder klassen TabVindu
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*Klassen definerer TabVindu-objekter, og er programmets vindu. Denne klassen oppretter hele brukergrensesnittet.*/
public class TabVindu extends JFrame{
    public static final int BORD=10;
    public static final int HALV_BORD=5;
    public static final int TJUKK_BORD=10;
    public static final int TEKSTFELTSTR=17;
    public static final int DATOFELTSTR=8;
    public static final int ROMFELTSTR=2;
    public static boolean innlogget;
    public static Color RAMMEFARGE=new Color(192,212,237);
    public static Color BAKGRUNNSFARGE=new Color(211, 224, 237);
    private JTabbedPane tabbedPane;
    private JPanel informasjon, bookingOversikt, utsjekking;
    private Box booking, innsjekking;
    private InnloggingsVindu innlogging;
    private String hotellFil="hotellfil.data";
    private Gjesteregister gregister;
    private Bookingregister bregister;                       
    private Romregister rregister;
    private JMenu meny;
    private JMenuItem lagre, avslutt;
    private JMenuBar menylinje;
        
    //Konstruktør som starter innlogging, oppretter meny, taber og ikon og leser fra fil.
    public TabVindu(){
        super("Hotell Nødzau");
        lesFraFil();
        
        String bildefil="hotelnodzau.gif"; //Oppretter et ikon
        URL kilde=TabVindu.class.getResource(bildefil);
        if(kilde!=null){
            ImageIcon bilde=new ImageIcon(kilde);
            Image ikon=bilde.getImage();
            setIconImage(ikon);
        }
        
        visInnlogging();
        
        meny=new JMenu("Fil");
        meny.setMnemonic('F');
        
        lagre=new JMenuItem("Lagre");
        lagre.setMnemonic('L');
        lagre.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                skrivTilFil();
            }
        });
        
        avslutt=new JMenuItem("Avslutt");
        avslutt.setMnemonic('A');
        avslutt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                skrivTilFil();
                System.exit(0);
            }
        });
        
        meny.add(lagre);
        meny.add(avslutt);
   
        menylinje=new JMenuBar();
        setJMenuBar(menylinje);
        menylinje.add(meny);
        
        //oppretter informasjonssiden
        informasjon=new InfoPanel();
        informasjon.setBackground(BAKGRUNNSFARGE);
        
        //oppretter bookingsiden
        booking=new BookingPanel(gregister, bregister, rregister, this);
        
        //oppretter bookingOversiktsiden
        bookingOversikt=new BookingOversiktPanel(gregister, rregister, this);
        bookingOversikt.setBackground(BAKGRUNNSFARGE);
        
        //oppretter innsjekkingssiden
        innsjekking=new InnsjekkingPanel(gregister, bregister, rregister, this);
        
        //oppretter utsjekkingssiden
        utsjekking=new UtsjekkingPanel(gregister, bregister, rregister, this);
        utsjekking.setBackground(BAKGRUNNSFARGE);
        
        //setter inn taber
        tabbedPane=new JTabbedPane();
        tabbedPane.addTab("Informasjon", null, informasjon, "Informasjonsvindu");
        
        if(innlogget){
            tabbedPane.addTab("Booking", null, booking, "Bookingvindu");
            tabbedPane.addTab("Bookingoversikt", null, bookingOversikt, "Bookingoversiktvindu");
            tabbedPane.addTab("Innsjekking", null, innsjekking, "Innsjekkingsvindu");
            tabbedPane.addTab("Utsjekking", null, utsjekking, "Utsjekkingsvindu");
        }
        tabbedPane.setBorder(new EmptyBorder(TJUKK_BORD, TJUKK_BORD, TJUKK_BORD, TJUKK_BORD));
        tabbedPane.setBackground(BAKGRUNNSFARGE);
        
        add(tabbedPane, BorderLayout.PAGE_START);
        
        Container c=getContentPane();
        c.setBackground(RAMMEFARGE);
       
        pack();
        setVisible(true);
    } //Slutt på konstruktør
    
    //Leser gjesteregister, romregister og bookingregister til fil, oppretter nye registere om de ikke eksisterer
    private void lesFraFil(){
        try(ObjectInputStream inn=new ObjectInputStream(new FileInputStream(hotellFil))){
            gregister=(Gjesteregister)inn.readObject();
            rregister=(Romregister)inn.readObject();
            bregister=(Bookingregister)inn.readObject();
	}
        catch(ClassNotFoundException cnf){
            beskjed("Problemer med lesing av fil.\nOppretter nytt register\n");
            gregister=new Gjesteregister();
            rregister=new Romregister();
            bregister=new Bookingregister(rregister);
	}
	catch(FileNotFoundException fnf){
            beskjed("Finner ikke fil for innlesing.\nOppretter nytt register\n");
            gregister=new Gjesteregister();
            rregister=new Romregister();
            bregister=new Bookingregister(rregister);
        }
	catch(IOException io){
            beskjed("Problemer med lesing av fil.\nOppretter nytt register\n");
            gregister=new Gjesteregister();
            rregister=new Romregister();
            bregister=new Bookingregister(rregister);
	}
    } //Slutt på metode lesFraFil()
    
    //Lagrer gjesteregister, romregister og bookingregister til fil
    public void skrivTilFil(){
	try (ObjectOutputStream ut=new ObjectOutputStream(new FileOutputStream(hotellFil))){
	    ut.writeObject(gregister);
            ut.writeObject(rregister);
            ut.writeObject(bregister);
	}
	catch(NotSerializableException ns){
            beskjed("Problemer med lagring til fil, kunne ikke serialisere objekter");
        }
	catch(IOException io){
            beskjed("Problemer med lagring til fil");
	}
    }
    
    //Viser et innloggingsvindu
    private void visInnlogging(){
        innlogging=new InnloggingsVindu(this, gregister);
        innlogging.setLocationRelativeTo(this);
        innlogging.setVisible(true);
    }
    
    //Mottar tekststreng som vises i dialogboks som advarsel
    public void beskjed(String tekst){
        JOptionPane.showMessageDialog(null, tekst, "Advarsel", JOptionPane.WARNING_MESSAGE);
    }
} //Slutt på klassen TabVindu