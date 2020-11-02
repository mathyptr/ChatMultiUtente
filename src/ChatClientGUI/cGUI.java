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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ChatServer.Client;

/**
 * descrizione
 * @author Patrissi Mathilde
 */


public class cGUI extends JPanel  implements ActionListener{

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 625;

    private final int INITIAL_DELAY = 1000;
    private final int PERIOD_INTERVAL = 1000;
	

    private boolean chaton=false;
    private boolean pricegiving=false;
    private boolean soundON=true;
    private Timer timer;
    private int x, y;
    private String atmo="atmo_snow";
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
	BufferedImage imagebkg=null;
	BufferedImage imagefinish=null;
//	JMenu fileMenu = new JMenu("Opzioni");
	
	private JPanel panHorse= new JPanel();
	private JPanel panHorseOpt= new JPanel();
	private JPanel panOpt= new JPanel();
	private JPanel panHorseImg= new JPanel();
	private JPanel panHorseImgRight= new JPanel();
	private JPanel panHorseImgLeft= new JPanel();
	private JPanel panButtonStart= new JPanel();
	
	private JTextField usernameField = new JTextField("10",2);
	private JComboBox userCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox 	languageCombo = new JComboBox();
	private JCheckBox soundCheckBox= new JCheckBox();

	private Box boxUpper;
	private Box boxCenter;
	private Box boxBottom;
	private Box boxALL;
	private JLabel usernameLabel =new JLabel();
	private JLabel atmoLabel = new JLabel();
	private JLabel percorsoLabel = new JLabel();
	private JLabel languageLabel = new JLabel();
	private JLabel soundLabel = new JLabel();

	
	private JButton btnStart =null;
	private JTextArea tareaIn;
	private JTextArea tareaOut;

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
		
		PrintStream pStream = new PrintStream(new CustomOutputStream(tareaOut));
		System.setOut(pStream);
		System.setErr(pStream);     
		
		chattone = new Client(msgB.GetResourceValue("server_name"), Integer.valueOf(msgB.GetResourceValue("server_port")), "Mathy");
		try {
			chattone.StartG();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chattone.getList();

	    buildComponent();
    }
    
    private void MenuStart() {
    	Menu();
    	panelMenuSetStatus(false);
    } 
    private void Menu() {
    	
   
    	
    	btnStart = new JButton();
    	Border edge=BorderFactory.createRaisedBevelBorder();
    	Dimension size = new Dimension(100,60);
   //3005   	btnStart.setBorder(edge);
  //3005   	btnStart.setPreferredSize(size);
 soundCheckBox.setSelected (true);
 
 

 		size = new Dimension(80,20);
 		atmoLabel.setPreferredSize(size);  //3005     
 		userCombo.setPreferredSize(size);  //3005
 		percorsoLabel.setPreferredSize(size);  //3005       
 		bkgCombo.setPreferredSize(size);  //3005          
 		languageLabel.setPreferredSize(size);  //3005
 		languageCombo.setPreferredSize(size);  //3005
 		usernameLabel.setPreferredSize(size);  //3005;
 		usernameField.setPreferredSize(size);  //3005  
 		soundCheckBox.setPreferredSize(size);  //3005

 

       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
           

       boxUpper = Box.createHorizontalBox();
   	   Box boxUpper1 = Box.createVerticalBox();
   	   Box boxUpper2 = Box.createVerticalBox();
   	   Box boxUpper3 = Box.createVerticalBox();
     
       
       boxUpper1.add(atmoLabel);       
       boxUpper1.add(userCombo);
       //boxUpper1.add(Box.createVerticalStrut(10));
       boxUpper1.add(percorsoLabel);       
       boxUpper1.add(bkgCombo);          
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
       
       
       Box boxVerticalLeft = Box.createVerticalBox();
    
        Box boxVerticalRight = Box.createVerticalBox();
 
        
        tareaOut = new JTextArea(70, 20);      
        JScrollPane scrollpOut = new JScrollPane(tareaOut);
     
 //    	boxCenter = Box.createHorizontalBox();
    	boxCenter = Box.createVerticalBox();
     	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
     	boxCenter.add(scrollpOut);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
    	
    	tareaIn = new JTextArea(10, 20);  
    	
        JScrollPane scrollpIn = new JScrollPane(tareaIn);      
      	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
     	boxCenter.add(scrollpIn);
    	boxCenter.add(Box.createHorizontalStrut(B_WIDTH/10));
    	
    	//boxCenter.add(Box.createVerticalStrut(10));
 //!!!!!!    	boxCenter.setSize(100, 100);
   
     	//panButtonStart.setLayout(new GridLayout(3,4,10,10));//3005
      	panButtonStart.add(btnStart);//3005

     	boxBottom = Box.createHorizontalBox();
  	
  //      boxBottom.add(Box.createHorizontalStrut(B_WIDTH/10)); //3005
 //       boxBottom.add(Box.createVerticalStrut(10)); //3005
     	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50)); //3005
        boxBottom.add(panButtonStart);//3005
       	boxBottom.add(Box.createHorizontalStrut(B_WIDTH/2-50)); //3005
 //     boxBottom.setBounds(B_WIDTH, B_HEIGHT-100, 50, 50); //3005
 
   
 //3005      boxBottom.add(btnStart);   
       
     	boxALL= Box.createVerticalBox();
    	boxALL.add(Box.createVerticalStrut(B_HEIGHT));
    	
        BoxLayout box=new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(box);

        boxBottom.setOpaque( false );//3005
 
       
        this.add(boxUpper);
//3005        this.add(boxBottom);
        this.add(Box.createVerticalStrut(50));
        this.add(boxCenter);
    // 3005   this.add(boxBottom);
        this.add(Box.createVerticalStrut(5));//3005
        this.add(boxBottom);    
        this.add(boxALL);
        this.setOpaque( false );//3005
  //      this.add(boxBottom,BorderLayout.SOUTH);//3005
             
//        this.add(boxUpper,BorderLayout.NORTH);
    	
 //       this.add(panHorse);
 //      	this.add(panHorseOpt);
//        this.add(boxVerticalLeft,BorderLayout.WEST);
 //       this.add(boxVerticalRight,BorderLayout.EAST);
        
        
 //0111       buildComponent();
        bkgCombo.addActionListener(this);     
        userCombo.addActionListener(this);    
        languageCombo.addActionListener(this);
        btnStart.addActionListener(this);
        soundCheckBox.addActionListener(this);
        
        ClipSound(clipRace,false);
        ClipSound(clipWin,false);
        ClipSound(clipMenu,true);
     
      	
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
		chattone.hello(usernameField.getText());
   		chaton=true;
   		btnStart.setText(msgB.GetResourceValue("btn_send"));       		
   		panelMenuSetStatus(true);
      	ClipSound(clipWin,false);
        ClipSound(clipRace,false);
    	ClipSound(clipMenu,true); 
//    	timer = new Timer();
//    	timer.scheduleAtFixedRate(new ScheduleTask(),INITIAL_DELAY, PERIOD_INTERVAL);
	}
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	chattone.getList();
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
       		else if(cb==bkgCombo)  
       		{
       	        loadBKGimg();
       		   	repaint();
       		}
       		else if (cb==userCombo)
       		{	
       			chattone.setDestName(userCombo.getSelectedItem().toString());
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
    	tareaOut.append(d+"\n");
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
    	 int itemCount = bkgCombo.getItemCount();
    	 
        for( i=0;i<itemCount;i++){
        	bkgCombo.removeItemAt(0);
         }
        String lan;
        if(languageCombo.getSelectedItem()!=null)
        	lan=languageCombo.getSelectedItem().toString();
        else 
        	lan=null;
        
        itemCount = languageCombo.getItemCount();
        for( i=0;i<itemCount;i++){
        	languageCombo.removeItemAt(0);
         }
    
  //      remove(panHorse);        
        
        bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_rock")); 
        bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad")); 
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
     	
     	 itemCount = userCombo.getItemCount();
         for( i=0;i<itemCount;i++){
        	 userCombo.removeItemAt(0);
          }   
        
         for(i=0;i<chattone.getThreadClient().getList().size();i++)
        	 userCombo.addItem(chattone.getThreadClient().getList().elementAt(i));
        
 //        panHorse.add(atmoLabel);       
//       panHorse.add(atmoCombo);
      
  
//        panHorse.add(percorsoLabel);       
//        panHorse.add(bkgCombo)
        if(chaton)
          	btnStart.setText(msgB.GetResourceValue("btn_send"));
        else
          	btnStart.setText(msgB.GetResourceValue("btn_start"));
        usernameLabel.setText(msgB.GetResourceValue("label_username"));
    	atmoLabel.setText(msgB.GetResourceValue("label_atmo"));
    	percorsoLabel.setText(msgB.GetResourceValue("label_place"));
    	languageLabel.setText(msgB.GetResourceValue("label_language"));
    	soundCheckBox.setText(msgB.GetResourceValue("label_sound"));

    	isSetByProgram=false;
    }
    
    
    private void loadBKGimg()
   	{  	
    	String bkgstr,filebkg=null;
//    	bkgstr=bkgCombo.getSelectedItem().toString();
    	bkgstr=MessagesBundle.GetResourceKey(bkgCombo.getSelectedItem().toString());
  	  
		if(bkgstr.contentEquals("bkg_sand"))
			filebkg="/img/bkg/sand.jpg";		
		else if(bkgstr.contentEquals("bkg_field"))
			filebkg="/img/bkg/field.jpg";
		else if(bkgstr.contentEquals("bkg_green"))
			filebkg="/img/bkg/green.jpg";
		else if(bkgstr.contentEquals("bkg_railroad"))
			filebkg="/img/bkg/railroad.jpg";
		else if(bkgstr.contentEquals("bkg_rock"))
			filebkg="/img/bkg/rock.jpg";
		else if(bkgstr.contentEquals("bkg_ice"))
			filebkg="/img/bkg/ice.jpg";
	
		try {
			imagefinish=(BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/bkg/finish.png"));
			if(filebkg!=null)
			{	
//				Image img=ImageIO.read(getClass().getResourceAsStream(filebkg)).getScaledInstance(B_WIDTH, B_HEIGHT,Image.SCALE_SMOOTH);
//				imagebkg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
				
				imagebkg=(BufferedImage) ImageIO.read(getClass().getResourceAsStream(filebkg));
			}
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
}
         
    private void drawBackground(Graphics g) {
    	g.drawImage(imagebkg, 0, 0, this);     
        
	}  

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    	drawBackground(g);
    
    }
    
}