package ChatClientGUI;


import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
/**
 * Classe di override dello standard output
 * @author Patrissi Mathilde
 */
	public class CustomOutputStream extends OutputStream {
        private JTextPane textPane;
        /**Costruttore della classe CustomOutputStream
         * @param textArea JTextPane
 	    */
        public CustomOutputStream(JTextPane textArea) {
            this.textPane = textArea;
        }
        /**Metodo write
         * @param textArea JTextPane
 	    */
        @Override
        public void write(int b) throws IOException {
 /*           // redirects data to the text area
            textArea.append(String.valueOf((char)b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
            
        	*/
        	
         	 StyledDocument doc = textPane.getStyledDocument();
            try {
                    doc.insertString(doc.getLength(), String.valueOf((char)b),doc.getStyle("italic"));
            } catch (BadLocationException ble) {
                System.err.println("Couldn't insert initial text into text pane.");
            }
       	 
        }

        /**
         * Metod println
         * @param string String
         **/
        public void println(String string) {
        	
      	 StyledDocument doc = textPane.getStyledDocument();
         try {
                 doc.insertString(doc.getLength(), string+"\n",doc.getStyle("italic"));
         } catch (BadLocationException ble) {
             System.err.println("Couldn't insert initial text into text pane.");
         }
    	
        }


    /**
     * Metodo print
     * @param string String
     **/
    public void print(String string) {
   	 StyledDocument doc = textPane.getStyledDocument();
     try {
             doc.insertString(doc.getLength(),string,doc.getStyle("italic"));
     } catch (BadLocationException ble) {
         System.err.println("Couldn't insert initial text into text pane.");
     }
    }
    
    

    
}