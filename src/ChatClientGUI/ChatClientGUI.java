package ChatClientGUI;

import java.applet.AudioClip;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;


/**
 * descrizione
 * @author Patrissi Mathilde
 */

public class ChatClientGUI extends JFrame {
	/**Costruttore della classe HorseRacing
	    */
    public ChatClientGUI() {
   	
    	
        add(new cGUI());
                        
        setResizable(false);
        pack();
        
        setTitle("Chat");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new ChatClientGUI();
            ex.setVisible(true);
        });
    }
}
