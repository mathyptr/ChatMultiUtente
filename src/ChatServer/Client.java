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
	ThreadClient thC = null;
	CmdUtil command;
	/**
	 * Costruttore della classe Client
	 * 
	 * @param serverName String
	 * @param serverPort int
	 * @param name       String
	 */
	public Client(String serverName, int serverPort, String name) {
		try {
			clientSocket = new Socket(serverName, serverPort);// viene creato un socket dato il nome del server e la sua
																// porta
			command= new CmdUtil();
			clientName = name;
			inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outServer = new PrintStream(clientSocket.getOutputStream(), true);
			System.out.println("Connessione stabilita");
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

		thC = new ThreadClient(inputServer, clientName); // viene creato e fatto partire un ThreadClient
		thC.start();
		hello(); // prima di poter inviare i dati il client deve dire il proprio nome
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

		thC = new ThreadClient(inputServer, clientName); // viene creato e fatto partire un ThreadClient
		thC.start();
//		hello(); // prima di poter inviare i dati il client deve dire il proprio nome

	
	}

	
	/**
	 * Metodo per l'invio del messaggio del Client al server
	 * @param Message String
	 * @return true nel caso l'utente digiti il comando di uscita
	 */	
	public boolean sendData(String Message) {
		 if(command.checkCMD(Message)==-1) 
			 outServer.println(command.getDATA_CMD()+Message); // il messaggio viene mandato al server
		  else
			outServer.println(Message);
		return (Message.equalsIgnoreCase("q!")); // restituisce true quando l'utente ha inserito il comando per uscire
													// dalla chat
	}
	
	public void hello(String clientName) {
		
		this.clientName = clientName;
		hello(); // prima di poter inviare i dati il client deve dire il proprio nome
		
	}
	public void SendListCMD() {
		
		sendData(command.getLIST_CMD());
	}
	
	public void setDestName(String destName) {
		
		sendData(command.getSETDST_CMD()+destName);
	}	
	
	public ThreadClient getThreadClient() {
		return thC;
	}
}
