package ChatClientGUI;

import java.applet.AudioClip;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * Classe per la gestione dell'interfaccia della chat lato utente
 * @author Patrissi Mathilde
 */

public class ChatClientGUI extends JFrame {
	/**Costruttore della classe ChatClientGUI
	    */
    public ChatClientGUI() {
   	
    	cGUI ClientGUI= new cGUI();
    	  add(ClientGUI);
// 1411        add(new cGUI());
                        
        setResizable(false);
        pack();
        
        setTitle("Chat");
        setLocationRelativeTo(null);
// 1411       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
                        // Ask for confirmation before terminating the program.
 /*       		int option = JOptionPane.showConfirmDialog(
        				this, 
        			"Are you sure you want to close the application?",
        			"Close Confirmation", 
        			JOptionPane.YES_NO_OPTION, 
        			JOptionPane.QUESTION_MESSAGE);
        		if (option == JOptionPane.YES_OPTION) {
        			System.exit(0);
        		}
        		
        	}*/
        ClientGUI.quitChat();
        System.exit(0);
          }
        }); 
    }
    /**Main
	    */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new ChatClientGUI();
            ex.setVisible(true);
        });
    }
}
