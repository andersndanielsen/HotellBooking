/* Laget av
****Magnus J�rem Moltzau, s180473
****Anders N�dland Danielsen, s180475
****siste oppdatering: 11.05.12
****Beskrivelse:
* Hotell.java inneholder klassen Hotell.
*/

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Klassen inneholder mainmetoden som starter hele programmet.
public class Hotell {
    public static void main( String[] args ) //Starter programmet ved � opprette TabVindu.
    {
	final TabVindu vindu = new TabVindu();
	vindu.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing( WindowEvent we )
                {
                    vindu.skrivTilFil(); //Skriver til fil f�r programmet lukkes.
                    System.exit(0);
		}
	});
    }
} //Slutt p� klassen Hotell.