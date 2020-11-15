package ChatClientGUI;
import ChatServer.*;
import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import ChatServer.Client;

/**
 * Classe che gestisce la chat visualizzando i vari elementi grafici
 * @author Patrissi Mathilde
 */


public class cGUI extends JPanel  implements ActionListener{

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 625;

    private final int INITIAL_DELAY = 2000;
    private final int PERIOD_INTERVAL = 2000;
    private final int PERIOD_INTERVAL_LIST = 3*PERIOD_INTERVAL;
    private int tickTimeForList=PERIOD_INTERVAL;

    private boolean chaton=false;
    private boolean soundON=true;
    private Timer timer;
 
//    private int x, y;
    MessagesBundle msgB= new MessagesBundle();
    boolean isSetByProgram=false;
    Clip clipMenu;

	private JPanel panUpLeft= new JPanel();
	private JPanel panUpCenter= new JPanel();
	private JPanel panUpRight= new JPanel();

	private JPanel panButtonStart= new JPanel();
	
	private JTextField usernameField = new JTextField("",2);
	private JComboBox userCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox 	languageCombo = new JComboBox();
	private JCheckBox soundCheckBox= new JCheckBox();

	private Box boxUpper;
	private Box boxCenter;
	private Box boxCenter1;
	private Box boxCenterIN;
	private Box boxCenterOUT;
	private Box boxBottom;
	private Box boxALL;
	private Box boxStatus;
	private JLabel usernameLabel =new JLabel();
	private JLabel addressLabel = new JLabel();
	private JLabel percorsoLabel = new JLabel();
	private JLabel languageLabel = new JLabel();
	private JLabel soundLabel = new JLabel();
	private JLabel statusLabel = new JLabel();
	
	private JButton btnStart =null;
	private JTextArea tareaIn;
	private JTextArea tareaOut;
	private JTextPane textPane;	
	Client chattone;
	/**
	 * Costruttore della classe cGUI
	 */
    public cGUI() { 	
     
        msgB.SetLanguage("it", "IT");
   

    	try {     
 	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/Menu.wav")));
 	         clipMenu = AudioSystem.getClip();
 	         clipMenu.open(audioIn);    	 
  
  		}
 	    catch (Exception e){
 		   System.out.println("errore on sound: "+e.toString());
 	    }
        
		MenuStart();
		
		PrintStream pStream = new PrintStream(new CustomOutputStream(textPane));			
		System.setOut(pStream);
		System.setErr(pStream);     
		
	/*0811	chattone = new Client(msgB.GetResourceValue("server_name"), Integer.valueOf(msgB.GetResourceValue("server_port")), "Mathy");
		try {
			chattone.StartG();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chattone.SendListCMD();
   		chattone.setDestName("all");*/
		isSetByProgram=true;
   	  	userCombo.addItem("all");
    	userCombo.setSelectedIndex(0);
    	isSetByProgram=false;
    	chaton=false;
	    buildComponent();
    }
    /**
	 * Metodo per configurare la visualizzazione della finestra di avvio
	 */
    private void MenuStart() {
    	Menu();
    	panelMenuSetStatus(false);
    } 
    /**
	 * Metodo per l'implementazione dell'interfaccia menu' iniziale
	 */
    private void Menu() {  	
     	
    	btnStart = new JButton();

  	
    	Border edge=BorderFactory.createRaisedBevelBorder();
    	Dimension size = new Dimension(100,60);
    	soundCheckBox.setSelected (true);
 
 

 		size = new Dimension(80,20);
 		addressLabel.setPreferredSize(size);    
 		userCombo.setPreferredSize(size);        
 		languageLabel.setPreferredSize(size);  
 		languageCombo.setPreferredSize(size); 
 		usernameLabel.setPreferredSize(size);  
 		usernameField.setPreferredSize(size);  
 		soundCheckBox.setPreferredSize(size);  

 		statusLabel.setPreferredSize(new Dimension(B_WIDTH, 20));  
 		statusLabel.setText("------------------------");
       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
           

       boxUpper = Box.createHorizontalBox();
   	   Box boxUpper1 = Box.createVerticalBox();
   	   Box boxUpper2 = Box.createVerticalBox();
   	   Box boxUpper3 = Box.createVerticalBox();
     
       boxUpper1.add(addressLabel);       
       boxUpper1.add(userCombo);
       boxUpper2.add(languageLabel);
       boxUpper2.add(languageCombo);
       boxUpper2.add(usernameLabel);
       boxUpper2.add(usernameField);  
       boxUpper3.add(soundCheckBox);
   
  
        panUpLeft.setBorder(new TitledBorder(new EtchedBorder(),""));
        panUpLeft.add(boxUpper1,BorderLayout.CENTER);
        panUpCenter.setBorder(new TitledBorder(new EtchedBorder(),""));
        panUpCenter.add(boxUpper2,BorderLayout.CENTER);
        panUpRight.setBorder(new TitledBorder(new EtchedBorder(),""));
        panUpRight.add(boxUpper3,BorderLayout.CENTER);

        boxUpper.add(Box.createHorizontalStrut(20));       //111120	
      	boxUpper.add(panUpLeft); 		
    	boxUpper.add(panUpCenter); 
       	boxUpper.add(panUpRight);              
        boxUpper.add(Box.createHorizontalStrut(20));   //111120
       

        
       tareaOut = new JTextArea(70, 20);      
       textPane = new JTextPane();
       addStylesToTextPAne(textPane);

       JScrollPane scrollpOut = new JScrollPane(textPane);
  
       scrollpOut.setPreferredSize(new Dimension(20, 370));
  
       tareaIn = new JTextArea(5, 5);  
       JScrollPane scrollpIn = new JScrollPane(tareaIn);      
       scrollpIn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    
    
       boxCenterIN = Box.createVerticalBox();
       boxCenterOUT = Box.createVerticalBox();
       boxCenterIN.add(scrollpIn);  
       boxCenterOUT.add(scrollpOut);
    

       boxCenter = Box.createVerticalBox();
    	
 
        Border border;
        Border blackline, raisedetched, loweredetched,    raisedbevel = null, loweredbevel = null, empty;
        blackline =  BorderFactory.createLineBorder(Color.black);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();    
        border = BorderFactory.createCompoundBorder(raisedetched, loweredetched);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(border);
       
        boxCenterIN.setBorder(border);
        boxCenterOUT.setBorder(border);
     
    	boxCenter.add(Box.createVerticalStrut(10));
     	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
     	boxCenter.add(boxCenterOUT);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));         
      	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
       	boxCenter.add(boxCenterIN);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));    	
    	boxCenter.add(Box.createVerticalStrut(10));    	
    	boxCenter1= Box.createHorizontalBox();
     	
    	
    	
    	boxCenter1.add(Box.createHorizontalStrut(20)); 
    	boxCenter1.add(boxCenter);
    	boxCenter1.add(Box.createHorizontalStrut(20));
      	panButtonStart.add(btnStart);
     	boxBottom = Box.createHorizontalBox();
  	
     	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50)); 
        boxBottom.add(panButtonStart);
       	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50));
        
     	boxALL= Box.createVerticalBox();
    	boxALL.add(Box.createVerticalStrut(B_HEIGHT));
    	
        BoxLayout box=new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(box);
 
        boxBottom.setOpaque( false );  
        boxStatus = Box.createHorizontalBox();
        boxStatus.add(statusLabel); 
        boxStatus.add(Box.createHorizontalStrut(B_WIDTH));

        this.add(boxUpper); 
        this.add(Box.createVerticalStrut(10));
        this.add(boxCenter1);
        this.add(Box.createVerticalStrut(10));
       
        this.add(boxBottom);    
        this.add(boxALL);
        this.add(boxStatus);
        this.setOpaque( false );
   
        userCombo.addActionListener(this);    
        languageCombo.addActionListener(this);
        btnStart.addActionListener(this);
        soundCheckBox.addActionListener(this);

      
        ClipSound(clipMenu,true);
     
      	
   	} 
    /**
	 * Metodo per configurare gli stili di formattazione della classe TextPane
	 * @param tp JTextPane
	 */
    protected void addStylesToTextPAne(JTextPane tp) 
    {
    	StyledDocument doc = tp.getStyledDocument();
    	Style def = StyleContext.getDefaultStyleContext().
    	getStyle(StyleContext.DEFAULT_STYLE);

    	Style regular = doc.addStyle("regular", def);
    	StyleConstants.setFontFamily(def, "SansSerif");

    	Style s1 = doc.addStyle("center", regular);
    	StyleConstants.setAlignment(s1, StyleConstants.ALIGN_CENTER);
    	StyleConstants.setBackground(s1, new Color(255, 255, 179));    	
    	s1.addAttribute(StyleConstants.SpaceBelow,Float.valueOf(tp.getFont().getSize()/2.0f));
 
    	Style s2 = doc.addStyle("timeReceive", regular);
 //    	s2.addAttribute(StyleConstants.SpaceAbove,Float.valueOf(tp.getFont().getSize()/5.0f));    	
    	StyleConstants.setBackground(s2, new Color(108, 251, 175));    	
  //  	StyleConstants.setFontSize(s2,tp.getFont().getSize()/2);
    	StyleConstants.setFontSize(s2,10);
  
    	Style s3 = doc.addStyle("timeSend", regular);
 //    	s3.addAttribute(StyleConstants.SpaceAbove,Float.valueOf(tp.getFont().getSize()/5.0f));
    	StyleConstants.setAlignment(s3, StyleConstants.ALIGN_RIGHT);
    	StyleConstants.setBackground(s3, new Color(204, 230, 255));   
    	StyleConstants.setForeground(s3, Color.BLUE);
 //   	StyleConstants.setFontSize(s3,tp.getFont().getSize()/2);
    	StyleConstants.setFontSize(s3,10);	
    	
    	Style s = doc.addStyle("italic", regular);
    	StyleConstants.setItalic(s, true);

    	Style sR = doc.addStyle("italic", regular);
    	sR.addAttribute(StyleConstants.SpaceAbove,Float.valueOf(tp.getFont().getSize()/2.0f));
    	StyleConstants.setBackground(sR, new Color(108, 251, 175));
    	
    	sR = doc.addStyle("bold", regular);
    	StyleConstants.setBold(sR, true);
    	StyleConstants.setAlignment(sR, StyleConstants.ALIGN_RIGHT);
    	sR.addAttribute(StyleConstants.SpaceAbove,Float.valueOf(tp.getFont().getSize()/2.0f));    	
    	StyleConstants.setBackground(sR, new Color(204, 230, 255));
    	StyleConstants.setForeground(sR, Color.BLUE);
   
    	textPane.setParagraphAttributes(s, false);

    	s = doc.addStyle("small", regular);
    	StyleConstants.setFontSize(s, 10);

    	s = doc.addStyle("large", regular);
    	StyleConstants.setFontSize(s, 16);

    	s = doc.addStyle("icon", regular);
    	textPane.setEditable(false);
}
    
    /**
	 * Metodo per attivare o disattivare il suono
	 * 
	 * @param clipsound Clip
	 * @param state     boolean
	 */
    private void ClipSound(Clip clipsound,boolean state)
    {
    	if(soundON)
    		if(state) {
    			try {     
    				clipsound.loop(100);
//    				clipsound.LOOP_CONTINUOUSLY;
		   			}
		  	    	catch (Exception e){
		  	    		System.out.println("errore on sound: "+e.toString());
		  	    	}
    		}
    		else
    			clipsound.stop();
 
    }
    
    /**
     * 
	 * Metodo che permette uscire dalla chat 
	 */    
    public void quitChat()
    { 
    	if(chaton) 
    		chattone.quit();
    	
    }

    
    /**
     * 
	 * Metodo che permette l'inizio della chat inizializzando il client
	 */
    private void startChat()
    { 
    	    	
    	chattone = new Client(msgB.GetResourceValue("server_name"), Integer.valueOf(msgB.GetResourceValue("server_port")), usernameField.getText());
    	chgLanguage();
    	
		try {
			chattone.StartG();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		chattone.SendListCMD();
   		chattone.setDestName("all");
    	
		chattone.hello(usernameField.getText());
   		chaton=true;
   		btnStart.setText(msgB.GetResourceValue("btn_send"));
   		usernameField.setEditable(false);

   		panelMenuSetStatus(true);

    	ClipSound(clipMenu,true); 
    	populateTextPanMsg();
    	timer = new Timer();
    	timer.scheduleAtFixedRate(new ScheduleTask(),INITIAL_DELAY, PERIOD_INTERVAL);
	}
    
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	if(tickTimeForList>=PERIOD_INTERVAL_LIST) {
        		tickTimeForList=0;
        		chattone.SendListCMD();
        		buildList();
        	}
        	tickTimeForList+=PERIOD_INTERVAL;
        	String status=chattone.getStatus();
        	if(!status.equalsIgnoreCase("empty"))
        		statusLabel.setText(status);
        }
    } 
    /**
	 * Metodo per la gestione dei possibili eventi di interazione con l'utente
	 * attraverso il menu'
	 * 
	 * @param e ActionEvent
	 */

    public void actionPerformed(ActionEvent e)
    { 
    	if(!isSetByProgram)
    	{				
    		
    	String pulsante=e.getActionCommand();
  
    	if(pulsante.contentEquals("comboBoxChanged"))
    	{

    		JComboBox cb= (JComboBox) e.getSource();

    		if(cb==languageCombo)   
    		{
    			chgLanguage();
    			buildComponent();
    		}
     		else if (cb==userCombo)
       			populateTextPanMsg();
    		
       	}
       	else if(MessagesBundle.GetResourceKey(pulsante).contentEquals("label_sound"))
       	{	
    		boolean s=soundCheckBox.isSelected();
       		if(s) {
       			soundON=s;
       			ClipSound(clipMenu,true);
       		}		
       		else {     
       			ClipSound(clipMenu,false);
       			soundON=s;
       		}
       		
       	}			
       	else if(pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_send")))
    		sendMsg();
       	else if(pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_start")))
       	{	
       		if(usernameField.getText().isEmpty())
       			JOptionPane.showMessageDialog(this,msgB.GetResourceValue("warn_noname_msg"),msgB.GetResourceValue("warn_msg"),JOptionPane.INFORMATION_MESSAGE);
       		else
          		startChat();
    	}
    	}
    }  
    
    private void populateTextPanMsg() { 
		chattone.setDestName(userCombo.getSelectedItem().toString());
		StyledDocument doc = textPane.getStyledDocument();
		textPane.setText("");
		doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));  
		String [] m;
		String src,group,messaggio,datemsg="1900-01-01";
		java.util.Vector <String[]> msg=chattone.getMSGFromChat();
		try {	
			for(int i=0;i<msg.size();i++){	
				m=msg.elementAt(i);
/*				if(m[0].contentEquals(usernameField.getText())) {
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("bold"));
					doc.insertString(doc.getLength(), m[3]+"\n",doc.getStyle("bold"));
				}
				else
					doc.insertString(doc.getLength(),m[1]+":"+ m[3]+"\n",doc.getStyle("italic"));
				doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));  */
				if(!datemsg.equals(m[4]))//visualizza la data se questa e' diversa rispetto al messaggio precedente
				{
					datemsg=m[4];
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("center"));
					doc.insertString(doc.getLength(),m[4]+"\n",doc.getStyle("center"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));      						
				}
				src=m[0];
				group=m[2];
				if(usernameField.getText().contentEquals(src))
				{
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("bold"));
					doc.insertString(doc.getLength(), messaggio= m[3]+"\n",doc.getStyle("bold"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("timeSend"));    						
					doc.insertString(doc.getLength(), messaggio= m[5]+"\n",doc.getStyle("timeSend"));
				}    						
				else if(group.equalsIgnoreCase("ALL"))
				{
					doc.insertString(doc.getLength(),m[0]+":"+ m[3]+"\n",doc.getStyle("italic"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("timeReceive"));  
						doc.insertString(doc.getLength(), messaggio= m[5]+"\n",doc.getStyle("timeReceive"));
				}
				else
				{
					doc.insertString(doc.getLength(),m[3]+"\n",doc.getStyle("italic"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("timeReceive"));    						
						doc.insertString(doc.getLength(), messaggio= m[5]+"\n",doc.getStyle("timeReceive"));
				}
				doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));  
										  					
				
				//       				System.out.println(m[1]);
			}
		} catch (BadLocationException ble) {

			System.err.println("Couldn't insert initial text into text pane.");
		}

	}

    
    /**
	 * Metodo per la visualizzazione dei messaggi nella chat
	 */
    private void sendMsg()
    {
    	
    	String d=tareaIn.getText();
    	d = d.replaceAll("\\R+", " ");
    	chattone.sendData(d);
    	StyledDocument doc = textPane.getStyledDocument();
    	doc.setLogicalStyle(doc.getLength(), doc.getStyle("bold"));
        try {
                doc.insertString(doc.getLength(), d+"\n",doc.getStyle("bold"));
                doc.setLogicalStyle(doc.getLength(), doc.getStyle("timeSend"));
                Calendar now = Calendar.getInstance();
        		doc.insertString(doc.getLength(), now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+"\n",doc.getStyle("timeSend"));                
        } catch (BadLocationException ble) {
    
        System.err.println("Couldn't insert initial text into text pane.");
    }
        
//0311    	
        doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));
        

    	tareaIn.setText(""); 
	}
    /**
	 * Metodo che visualizza o nasconde il menu in base allo stato attuale della chat
	 * @param status boolean
	 */
    private void panelMenuSetStatus(boolean status)
    {
    	boxCenter.setVisible(status);
    	boxALL.setVisible(!status);
	}
    /**
	 * Metodo che permette il cambio della lingua
	 */
    private void chgLanguage()
    {

    	String language="",country="";
    	if(languageCombo.getSelectedItem().toString().equals("IT")) {
    		language="it";
    		country="IT";   	
    	}
    	else if(languageCombo.getSelectedItem().toString().equals("EN")) {
    		language="en";
    		country="US"; 	
    	}
    	else if(languageCombo.getSelectedItem().toString().equals("FR")) {
    		language="fr";
    		country="FR";
    	}
    	else if(languageCombo.getSelectedItem().toString().equals("ES")) {
    		language="es";
    		country="ES";   
    	}
    	else if(languageCombo.getSelectedItem().toString().equals("DE")) {
    		language="de";
    		country="DE";
    	}
    	msgB.SetLanguage(language,country);
    	if(chaton) {
    		chattone.setLanguage(language,country);
    		chattone.manageStatus("hello_msg");
    	}
  
    } 
    /**
	 * Metodo che imposta i componenti del menu' in base alla lingua scelta
	 */
    private void buildComponent()
    {
    	isSetByProgram=true;
    	int i;

        String lan;
        if(languageCombo.getSelectedItem()!=null)
        	lan=languageCombo.getSelectedItem().toString();
        else 
        	lan=null;
        
        int itemCount = languageCombo.getItemCount();
        for( i=0;i<itemCount;i++){
        	languageCombo.removeItemAt(0);
         }
    
        languageCombo.addItem("IT");
        languageCombo.addItem("EN");
        languageCombo.addItem("FR");
        languageCombo.addItem("ES");
        languageCombo.addItem("DE");
        if(lan!=null) {
        	if(lan.equals("IT"))
        		languageCombo.setSelectedIndex(0);
        	else if(lan.equals("EN"))
        		languageCombo.setSelectedIndex(1);
        	else if(lan.equals("FR"))
        		languageCombo.setSelectedIndex(2);        	
        	else if(lan.equals("ES"))
        		languageCombo.setSelectedIndex(3);    
        	else if(lan.equals("DE"))
        		languageCombo.setSelectedIndex(4);  
    	}
        else
        		languageCombo.setSelectedIndex(0);
     	        

        if(chaton)
          	btnStart.setText(msgB.GetResourceValue("btn_send"));
        else
          	btnStart.setText(msgB.GetResourceValue("btn_start"));
        
        usernameLabel.setText(msgB.GetResourceValue("label_username"));
    	addressLabel.setText(msgB.GetResourceValue("label_address"));
    	languageLabel.setText(msgB.GetResourceValue("label_language"));
    	soundCheckBox.setText(msgB.GetResourceValue("label_sound"));

    	isSetByProgram=false;
    }
    /**
	 * Metodo che imposta i componenti della listBox contenente i nomi degli utenti connessi
	 */
    public void buildList() {
    	
    isSetByProgram=true;    	
    int itemCount = userCombo.getItemCount();
    String sel=null;
    
    if(itemCount!=0)
    	sel=userCombo.getSelectedItem().toString();
    
    for( int i=0;i<itemCount;i++){
     	 userCombo.removeItemAt(0);
       }
    java.util.Vector <String> v= chattone.getList( );
    int naddress=v.size();   
    for(int i=0;i<naddress;i++) {
    	userCombo.addItem(v.elementAt(i));
    	if (v.elementAt(i).equalsIgnoreCase(sel))
        		userCombo.setSelectedIndex(i);
    }    

	isSetByProgram=false;
    } 

    
}