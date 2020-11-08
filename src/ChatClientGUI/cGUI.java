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
 * descrizione
 * @author Patrissi Mathilde
 */


public class cGUI extends JPanel  implements ActionListener{

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 625;

    private final int INITIAL_DELAY = 2000;
    private final int PERIOD_INTERVAL = 5000;
	

    private boolean chaton=false;
 //   private boolean pricegiving=false;
    private boolean soundON=true;
    private Timer timer;
    private int x, y;
//    private String atmo="atmo_snow";
//    private Locale currentLocale;
 //   private ResourceBundle messages;
    MessagesBundle msgB= new MessagesBundle();
    boolean isSetByProgram=false;
    Clip clipMenu;
    Clip clipRace;
    Clip clipWin; 
    
// 	JPopupMenu popup = new JPopupMenu();
//	JMenuBar menuBar = new JMenuBar();
//	ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");
//	BufferedImage imagebkg=null;
//	BufferedImage imagefinish=null;
//	JMenu fileMenu = new JMenu("Opzioni");
	
	private JPanel panHorse= new JPanel();
	private JPanel panHorseOpt= new JPanel();
	private JPanel panOpt= new JPanel();
	private JPanel panHorseImg= new JPanel();
	private JPanel panHorseImgRight= new JPanel();
	private JPanel panHorseImgLeft= new JPanel();
	private JPanel panButtonStart= new JPanel();
	
	private JTextField usernameField = new JTextField("",2);
	private JComboBox userCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox 	languageCombo = new JComboBox();
	private JCheckBox soundCheckBox= new JCheckBox();

	private Box boxUpper;
	private Box boxCenter;
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
	//0311
	private JTextPane textPane;	
	//0311
//	private JTextPane textPaneStatus;
	Client chattone;
	
    public cGUI() { 	
     
        msgB.SetLanguage("it", "IT");
   

    	try {     
 	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/Menu.wav")));
 	         clipMenu = AudioSystem.getClip();
 	         clipMenu.open(audioIn);
 	      	 
  	      	 audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/galoppata.wav")));
 	         clipRace = AudioSystem.getClip();
  	      	 clipRace.open(audioIn);
 	  		
	         audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/applausi.wav")));
 	         clipWin = AudioSystem.getClip();
  	      	 clipWin.open(audioIn);  	        	         
  		}
 	    catch (Exception e){
 		   System.out.println("errore on sound: "+e.toString());
 	    }
        
		MenuStart();
		
		//0311		PrintStream pStream = new PrintStream(new CustomOutputStream(tareaOut));
		//0311			
		PrintStream pStream = new PrintStream(new CustomOutputStream(textPane));		
		//0311	
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
	    buildComponent();
    }
    
    private void MenuStart() {
    	Menu();
    	panelMenuSetStatus(false);
    } 
    private void Menu() {  	
     	
    	btnStart = new JButton();
    	
 /*   	btnStart.setOpaque(false);
    	btnStart.setFocusPainted(false);
    	btnStart.setBorderPainted(false);
    	btnStart.setContentAreaFilled(false);
    	btnStart.setForeground(Color.BLUE);*/
 //   	setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); 
  //  	btnStart.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    	//btnStart.setForeground(Color.BLUE);
  /*  	btnStart.setBorderPainted(false);
    	btnStart.setFocusPainted(false);
    	btnStart.setContentAreaFilled(false);*/
 //   	btnStart.setBounds(10,40,50,20);
  //  	btnStart.setBorder(new Border(10));
    	
    	Border edge=BorderFactory.createRaisedBevelBorder();
    	Dimension size = new Dimension(100,60);
   //3005   	btnStart.setBorder(edge);
  //3005   	btnStart.setPreferredSize(size);
 soundCheckBox.setSelected (true);
 
 

 		size = new Dimension(80,20);
 		addressLabel.setPreferredSize(size);  //3005     
 		userCombo.setPreferredSize(size);  //3005
 //		percorsoLabel.setPreferredSize(size);  //3005       
 //		bkgCombo.setPreferredSize(size);  //3005          
 		languageLabel.setPreferredSize(size);  //3005
 		languageCombo.setPreferredSize(size);  //3005
 		usernameLabel.setPreferredSize(size);  //3005;
 		usernameField.setPreferredSize(size);  //3005  
 		soundCheckBox.setPreferredSize(size);  //3005

 		statusLabel.setPreferredSize(new Dimension(B_WIDTH, 20));  
 		statusLabel.setText("-------------------Mathy");
       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
           

       boxUpper = Box.createHorizontalBox();
   	   Box boxUpper1 = Box.createVerticalBox();
   	   Box boxUpper2 = Box.createVerticalBox();
   	   Box boxUpper3 = Box.createVerticalBox();
     
       
       boxUpper1.add(addressLabel);       
       boxUpper1.add(userCombo);
       //boxUpper1.add(Box.createVerticalStrut(10));
 //      boxUpper1.add(percorsoLabel);       
 //      boxUpper1.add(bkgCombo);          
//       boxUpper1.add(Box.createHorizontalStrut(20));  
       boxUpper2.add(languageLabel);
       boxUpper2.add(languageCombo);
       //boxUpper2.add(Box.createVerticalStrut(10));
       boxUpper2.add(usernameLabel);
       boxUpper2.add(usernameField);  
//       boxUpper2.add(Box.createHorizontalStrut(20));
        boxUpper3.add(soundCheckBox);
        
 /*      boxUpper.add(Box.createHorizontalStrut(100));
       boxUpper.add(boxUpper1);
       boxUpper.add(Box.createHorizontalStrut(100));
       boxUpper.add(boxUpper2);
       boxUpper.add(Box.createHorizontalStrut(150));
   */    
      	panHorse.setBorder(new TitledBorder(new EtchedBorder(),""));
      	panHorse.add(boxUpper1,BorderLayout.CENTER);
      	panHorseOpt.setBorder(new TitledBorder(new EtchedBorder(),""));
      	panHorseOpt.add(boxUpper2,BorderLayout.CENTER);
      	panOpt.setBorder(new TitledBorder(new EtchedBorder(),""));
      	panOpt.add(boxUpper3,BorderLayout.CENTER);

      	boxUpper.add(panHorse);
//      	boxUpper.add(Box.createHorizontalStrut(5));        		
    	boxUpper.add(panHorseOpt); 
       	boxUpper.add(panOpt);              
     
       
 //      Box boxVerticalLeft = Box.createVerticalBox();  
//        Box boxVerticalRight = Box.createVerticalBox();
        
       tareaOut = new JTextArea(70, 20);      
       //0311      JScrollPane scrollpOut = new JScrollPane(tareaOut);      
      //0311
       textPane = new JTextPane();
       //textPane.setContentType("text/html");
     //Initialize some styles.
       addStylesToTextPAne(textPane);
 //      textPane.setSize(10, 10);
  //    textPane.setPreferredSize(new Dimension(10, 10));
 //         textPane.setBounds( 0, 0, 70, 20 );
       JScrollPane scrollpOut = new JScrollPane(textPane);

//         scrollpOut.setLayout( null );
//         scrollpOut.add( textPane );    
     scrollpOut.setPreferredSize(new Dimension(20, 370));
 //      scrollpOut.setMinimumSize(new Dimension(10, 10));   
 	tareaIn = new JTextArea(5, 5);  
    JScrollPane scrollpIn = new JScrollPane(tareaIn);      
       
     //0311       
//  	boxCenter = Box.createHorizontalBox();
    	boxCenter = Box.createVerticalBox();
    	boxCenter.add(Box.createVerticalStrut(10));
     	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
     	boxCenter.add(scrollpOut);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
   	
         
      	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
       	boxCenter.add(scrollpIn);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
    	
    	boxCenter.add(Box.createVerticalStrut(10));
    	
 //!!!!!!    	boxCenter.setSize(100, 100);
   
      	panButtonStart.add(btnStart);//3005
     	boxBottom = Box.createHorizontalBox();
  	
     	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50)); //3005
        boxBottom.add(panButtonStart);//3005
       	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50)); //3005
        
     	boxALL= Box.createVerticalBox();
    	boxALL.add(Box.createVerticalStrut(B_HEIGHT));
    	
        BoxLayout box=new BoxLayout(this,BoxLayout.Y_AXIS);
      this.setLayout(box);
 
        boxBottom.setOpaque( false );//3005
       
 /*       textPaneStatus = new JTextPane();
        textPaneStatus.setSize(10, B_WIDTH);
        JScrollPane scrollpStatus = new JScrollPane(textPaneStatus);  */  
        boxStatus = Box.createHorizontalBox();
        boxStatus.add(statusLabel); 
 //       boxStatus.add(scrollpStatus); 
        boxStatus.add(Box.createHorizontalStrut(B_WIDTH));

        this.add(boxUpper); 
        this.add(Box.createVerticalStrut(10));

        this.add(boxCenter); 
       this.add(Box.createVerticalStrut(10));//3005
       
        this.add(boxBottom);    
    this.add(boxALL);
    this.add(boxStatus);
        this.setOpaque( false );//3005
         
 //0111       buildComponent();
   //     bkgCombo.addActionListener(this);     
        userCombo.addActionListener(this);    
        languageCombo.addActionListener(this);
        btnStart.addActionListener(this);
        soundCheckBox.addActionListener(this);
        
        ClipSound(clipRace,false);
        ClipSound(clipWin,false);
        ClipSound(clipMenu,true);
     
      	
   	} 
  
    
    protected void addStylesToTextPAne(JTextPane tp) 
    {
    	StyledDocument doc = tp.getStyledDocument();
    	Style def = StyleContext.getDefaultStyleContext().
    	getStyle(StyleContext.DEFAULT_STYLE);

    	Style regular = doc.addStyle("regular", def);
    	StyleConstants.setFontFamily(def, "SansSerif");

    	Style s = doc.addStyle("italic", regular);
    	StyleConstants.setItalic(s, true);

    	Style sR = doc.addStyle("italic", regular);
    	sR = doc.addStyle("bold", regular);
    	StyleConstants.setBold(sR, true);
    	StyleConstants.setAlignment(sR, StyleConstants.ALIGN_RIGHT);
    	StyleConstants.setForeground(sR, Color.BLUE);
    	//      textPane.setParagraphAttributes(sR, true);


    	textPane.setParagraphAttributes(s, false);

    	s = doc.addStyle("small", regular);
    	StyleConstants.setFontSize(s, 10);

    	s = doc.addStyle("large", regular);
    	StyleConstants.setFontSize(s, 16);

    	s = doc.addStyle("icon", regular);
    	textPane.setEditable(false);
}
    
    
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
    
    private void startChat()
    { 
    	chattone = new Client(msgB.GetResourceValue("server_name"), Integer.valueOf(msgB.GetResourceValue("server_port")), usernameField.getText());
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
 //  		String h="<html><head><style>.button {  background-color: #4CAF50;  border: none;  color: white;  padding: 20px;  text-align: center;  text-decoration: none;  display: inline-block;  font-size: 16px;  margin: 4px 2px;}.button1 {border-radius: 2px;}</style></head><button class='button button1'>"+msgB.GetResourceValue("btn_send")+"</button></html>";
//   		btnStart.setText(h);   		
//   		btnStart.setText("<html><i>"+msgB.GetResourceValue("btn_send")+"</i></html>");   
   		btnStart.setText(msgB.GetResourceValue("btn_send"));
   		usernameField.setEditable(false);
/*   		java.util.Vector <String> v= chattone.getList( );
   		int naddress=v.size();   
   		for(int i=0;i<naddress;i++) {
   			if (v.elementAt(i).equalsIgnoreCase("ALL"))
         		userCombo.setSelectedIndex(i);
   		}  */
   		

   		panelMenuSetStatus(true);
      	ClipSound(clipWin,false);
        ClipSound(clipRace,false);
    	ClipSound(clipMenu,true); 
    	timer = new Timer();
    	timer.scheduleAtFixedRate(new ScheduleTask(),INITIAL_DELAY, PERIOD_INTERVAL);
	}
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	chattone.SendListCMD();
        	buildList();
        	String status=chattone.getStatus();
        	if(!status.equalsIgnoreCase("empty"))
        		statusLabel.setText(status);
        }
    } 
    
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
 /*      		else if(cb==bkgCombo)  
       		{
       	        loadBKGimg();
       		   	repaint();
       		
       		}*/
       		
       		else if (cb==userCombo)
       		{	
          		 
       			chattone.setDestName(userCombo.getSelectedItem().toString());
       			StyledDocument doc = textPane.getStyledDocument();
       			textPane.setText("");
       	        doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));       			
       			java.util.Vector <String> msg=chattone.getMSGFromChat();
       			for(int i=0;i<msg.size();i++){	
       				System.out.println(msg.elementAt(i));
       			}
       		}	
       		
       	}
       	else if(MessagesBundle.GetResourceKey(pulsante).contentEquals("label_sound"))
       	{	
       		btnStart.setText(msgB.GetResourceValue("btn_send"));
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
    	{  	
       		
 //   		loadBKGimg();    		
    		sendMsg();
    
      	    
    	}		
       	else if(pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_start")))
    	{  	
       		startChat();
    	}		
    	}
    }  
    
    private void sendMsg()
    {
    	
    	String d=tareaIn.getText();
    	chattone.sendData(d);
//0311    	tareaOut.append(d+"\n");
   	 //0311 
   	 StyledDocument doc = textPane.getStyledDocument();
     doc.setLogicalStyle(doc.getLength(), doc.getStyle("bold"));
        try {
                doc.insertString(doc.getLength(), d+"\n",doc.getStyle("bold"));
        } catch (BadLocationException ble) {
    
            System.err.println("Couldn't insert initial text into text pane.");
        }
//0311    	
        doc.setLogicalStyle(doc.getLength(), doc.getStyle("italic"));
        

    	tareaIn.setText(""); 
	}
    
    private void panelMenuSetStatus(boolean status)
    {
//     	btnStart.setText(msgB.GetResourceValue("btn_start"));
//    	boxUpper.setVisible(status);
    	boxCenter.setVisible(status);
    	boxALL.setVisible(!status);
	}
    
    private void chgLanguage()
    {
    	if(languageCombo.getSelectedItem().toString().equals("IT"))
    		msgB.SetLanguage("it", "IT");   	     
    	else
    		msgB.SetLanguage("en", "US");
    } 
    
    private void buildComponent()
    {
    	isSetByProgram=true;
    	int i;
 /*   	 int itemCount = bkgCombo.getItemCount();
    	 
        for( i=0;i<itemCount;i++){
        	bkgCombo.removeItemAt(0);
         }*/
        String lan;
        if(languageCombo.getSelectedItem()!=null)
        	lan=languageCombo.getSelectedItem().toString();
        else 
        	lan=null;
        
        int itemCount = languageCombo.getItemCount();
        for( i=0;i<itemCount;i++){
        	languageCombo.removeItemAt(0);
         }
    
  //      remove(panHorse);        
        
/*        bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_rock")); 
        bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad")); 
        */
        languageCombo.addItem("IT");
        languageCombo.addItem("EN");          
        if(lan!=null) {
        	if(lan.equals("IT"))
        		languageCombo.setSelectedIndex(0);
        	else
        		languageCombo.setSelectedIndex(1);
    	}
        else
        		languageCombo.setSelectedIndex(0);
     	        
 //        panHorse.add(atmoLabel);       
//       panHorse.add(atmoCombo);
        
  //    buildList();
        
//        panHorse.add(percorsoLabel);       
//        panHorse.add(bkgCombo)
        if(chaton)
          	btnStart.setText(msgB.GetResourceValue("btn_send"));
        else
          	btnStart.setText(msgB.GetResourceValue("btn_start"));
        usernameLabel.setText(msgB.GetResourceValue("label_username"));
    	addressLabel.setText(msgB.GetResourceValue("label_address"));
//    	percorsoLabel.setText(msgB.GetResourceValue("label_place"));
    	languageLabel.setText(msgB.GetResourceValue("label_language"));
    	soundCheckBox.setText(msgB.GetResourceValue("label_sound"));

    	isSetByProgram=false;
    }
 
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
/*    
    int naddress=chattone.getThreadClient().getList().size();   
    for(int i=0;i<naddress;i++) {
    	userCombo.addItem(chattone.getThreadClient().getList().elementAt(i));
    	if (chattone.getThreadClient().getList().elementAt(i).equalsIgnoreCase(sel))
        		userCombo.setSelectedIndex(i);
    }
    */
   // userCombo.setSelectedIndex(userCombo.getItemCount()-1);
	isSetByProgram=false;
    } 

    
}