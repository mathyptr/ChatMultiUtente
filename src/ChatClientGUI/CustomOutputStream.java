package ChatClientGUI;


/**
 * Class TextAreaPrintStream
 * extends PrintStream.
 * A custom made PrintStream which overrides methods println(String)
 * and print(String).
 * Thus, when the out stream is set as this PrintStream (with System.setOut
 * method), all calls to System.out.println(String) or System.out.print(String)
 * will result in an output stream of characters in the JTextArea given as an
 * argument of the constructor of the class.
 **/


import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
	
	public class CustomOutputStream extends OutputStream {
        private JTextPane textPane;
		       
        public CustomOutputStream(JTextPane textArea) {
            this.textPane = textArea;
        }
       
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
         * Method println
         * @param the String to be output in the JTextArea textArea (private
         * attribute of the class).
         * After having printed such a String, prints a new line.
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
     * Method print
     * @param the String to be output in the JTextArea textArea (private
     * attribute of the class).
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