package ChatServer;
import java.io.*;
import java.net.*;

/**
 * Classe per l'implementazione di un Client
 * 
 * @author Patrissi Mathilde
 */
public class Client {
	String serverName;
	String clientName;
	int serverPort;
	DataInputStream inKeyboard;
	PrintStream outServer = null;
	Socket clientSocket;
	BufferedReader keyboard;
	BufferedReader inputServer;
	SharedRegion SR;
	ThreadClient thC = null;
	CmdUtil command;
  	DBManager dbchat = null;
 	String type="cmd";
    MessagesBundle msgB= new MessagesBundle();
	/**
	 * Costruttore della classe Client
	 * 
	 * @param serverName String
	 * @param serverPort int
	 * @param name       String
	 */
	public Client(String serverName, int serverPort, String name) {
			this.serverName=serverName;
			this.serverPort=serverPort;
			command= new CmdUtil();
			SR = new SharedRegion();
			clientName = name;
			dbchat=new DBManager();	
			msgB.SetLanguage("it", "IT");
	}
	/**
	 * Metodo per il settaggio della lingua 
	 * 
	 * @param language String
	 * @param country String
	 */
	public void setLanguage(String language, String country)
	{
		msgB.SetLanguage(language, country);
	
	}
	/**
	 * Metodo che permette la connessione con il server
	 * 
	 * @param type String
	 */
	public void Connect(String type) {
		try {	
			this.type=type;
			clientSocket = new Socket(serverName, serverPort);// viene creato un socket dato il nome del server e la sua porta			
			inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outServer = new PrintStream(clientSocket.getOutputStream(), true);
			manageStatus("Connessione stabilita");
		} catch (IOException e) {
			System.out.println("Host non riconosciuto");
		}
	} 
	/**
	 * Metodo per l'invio al server del nome del Client: condizione necessaria per
	 * l'inizio della chat
	 */
	public void hello() {
		outServer.println(command.getHELLO_CMD() + clientName);

	}

	/**
	 * Metodo per la creazione e la gestione dei thread client da CLI
	 * 
	 * @throws InterruptedException
	 */
	public void Start() throws InterruptedException {
		type="cmd";
		Connect(type);
		thC = new ThreadClient(inputServer, clientName, SR, "cmd",msgB); // viene creato e fatto partire un ThreadClient
		thC.start();
		hello(); // prima di poter inviare i dati il client deve dire il proprio nome
		setDestName("all");
		String Message;
	
		boolean ris=false;
		while (!ris) { // viene richiamato il metodo sendData() finche l'utente non decide di lasciare
							// la chat
			 Message = "";
			 InputStreamReader input = new InputStreamReader(System.in);
			 keyboard = new BufferedReader(input);
			 try {
				 Message = keyboard.readLine(); // viene letto il messaggio da input
			 } catch (Exception e) {
				 System.out.println("errore input");
			 }
			 ris=sendData(Message);
	}
		thC.join(); // viene atteso il termine del thread client

	}
	/**
	 * Metodo per la creazione e la gestione dei thread client da GUI
	 * 
	 * @throws InterruptedException
	 */
	public void StartG() throws InterruptedException {

		type="GUI";
		Connect(type);
		thC = new ThreadClient(inputServer, clientName, SR,type,msgB); // viene creato e fatto partire un ThreadClient
		thC.start();
//		hello(); // prima di poter inviare i dati il client deve dire il proprio nome

	
	}

	
	/**
	 * Metodo per l'invio del messaggio del Client al server
	 * @param Message String
	 * @return true nel caso l'utente digiti il comando di uscita
	 */	
	public boolean sendData(String Message) {
		 if(command.checkCMD(Message)==-1) { 
			 String s=command.getDATA_CMD()+Message;
			 outServer.println(s); // il messaggio viene mandato al server
			 dbchat.insert(clientName, SR.getDest(), Message);
		  }
		  else
			outServer.println(Message);
		return (Message.equalsIgnoreCase("q!")); // restituisce true quando l'utente ha inserito il comando per uscire
													// dalla chat
	}
	
	public void hello(String clientName) {
		
		this.clientName = clientName;
		hello(); // prima di poter inviare i dati il client deve dire il proprio nome
		
	}
	/**
	 * Metodo per l'invio del comando di richiesta degli utenti connessi
	 * 
	 */
	public void SendListCMD() {
		
		sendData(command.getLIST_CMD());
	}
	/**
	 * Metodo che permette il settaggio dell'utente destinatario
	 * 
	 */
	public void setDestName(String destName) {
		SR.putDest(destName);
		sendData(command.getSETDST_CMD()+destName);
	}	
	/**
	 * Metodo che restituisce il ThreadClient attuale
	 * @return il ThreadClient attuale
	 * 
	 */
	public ThreadClient getThreadClient() {
		return thC;
	}
	/**
	 * Metodo che restituisce la lista degli utenti connessi
	 * @return la lista degli utenti connessi
	 * 
	 */
	public java.util.Vector <String> getList( ) {
		return SR.getList();
	}
	/**
	 * metodo per la memorizzazione del messaggio del server
	 * @param s String
	 */
	public void manageStatus(String s)
	{
		 if(type.equalsIgnoreCase("cmd"))
			 System.out.println(s);
		 else
			 SR.pushStatusMSG(s);
	}
	/**
	 * metodo che permette notificare nella barra di stato i nuovi messaggi di altre chat 
	 * @param s String
	 */
	public String getStatus()
	{
		 return SR.popStatusMSG();
	}
	/**
	 * metodo che permette prelevare dal database i messaggi richiesti
	 * @return i messaggi
	 */
	public java.util.Vector <String[]> getMSGFromChat() {
		java.util.Vector <String[]> msg=new java.util.Vector <String[]>(1,1);
		msg=dbchat.msgFromChat(SR.getDest(), clientName);
		return msg;
	}
		
	
}
