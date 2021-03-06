package ChatServer;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Calendar;


/**
 * Classe per l'implementazione di un Thread Server che si occuper� della comunizione con un client
 *  TODO: in questa versione, che prevede da specifica solo due client, durante l'invio di
 *    un messaggio viene scelto il primo threadServer diverso da quello utilizzato nel momento in questione.
 *    Infatti, essendoci due soli ThreadServer, si tratter� sicuramente dell'altro Thread.
 *    Entrambi vegono pero' inseriti in un vettore poiche', implementando un comando per identificare 
 *    il destinatario, sar� possibile gestire lo scambio di messaggi con un numero superiore di client.
 * 
 * 
 * @author Patrissi Mathilde
 */
public class ThreadServer extends Thread{
	BufferedReader inputClient;
	PrintStream outClient=null;	
	String clientData;
	String clientName;
	String status="initChat";
	String currentDest="all";
	java.util.Vector <String> CMD=new java.util.Vector <String> (1,1);
	java.util.Vector <Thread> ThreadVect;
	CmdUtil command;
	LocalDateTime  lastListcmd=LocalDateTime.now();
//    MessagesBundle msgB= new MessagesBundle();
	/**
	 * Costruttore della classe ThreadServer
	 * 
	 * @param ThreadVect java.util.Vector
	 * @param clientSocket Socket
	 */
	public ThreadServer (java.util.Vector <Thread> ThreadVect,Socket clientSocket){
		command= new CmdUtil();
		this.ThreadVect=ThreadVect; //il vettore contiene tutti i serverThread che gestiscono la communicazione con un client
		try { //vengono creati gli stream per leggere/scrivere dal/al client
		inputClient= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outClient = new PrintStream(clientSocket.getOutputStream(), true);
	} 
	catch (IOException e) {
		System.out.println("errore I/O");
	}
	}

	/**
	 * metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {
		int dest = -1;
		int index;
		int cmd;
		boolean end=false;
		while (!end) {
		try {
			clientData = inputClient.readLine(); // lettura del messaggio del client
		} catch (Exception e) {
			SendMSG("errorsrv_readfromclient_msg");
			System.out.println("errorsrv_readfromclient_msg");
			end=true;
			break;
		}
		cmd=command.checkCMD(clientData);
		 if(cmd!=-1)
		  end=DecEndExec(command.checkCMD(clientData),clientData);
		else {
			SendMSG("errorsrv_cmd_msg");
			System.out.println("errorsrv_cmd_msg");
		}
		}
	}
	
	/**
	 * metodo per l'esecuzione del comando richiesto
	 * @param index int
	 * @param clientData String
	 * @return il nome del Client
	 */
	public boolean DecEndExec(int index, String clientData) {
		boolean end=false;
//		System.out.println("DEBUG: " +clientData+ "\n");		
		switch(index) { 
		case 0 :  //comando hello: dalla fase initChat entriamo nella fase goChat
			clientName=command.getDataCMD(clientData); // viene salvato in una variabile il nome del client
//0811			outClient.println("Ciao " + clientName + " ti diamo il benvenuto nella chat");
			outClient.println("hello_msg");			
			
			status="goChat";
			break;
		
		case 1: //comando quit: dalla fase goChat entriamo nella fase stopChat
		     status="stopChat";
		     SendMSG(command.getQUIT_CMD()+"bye_msg");
		     SendToAll(command.getSTATUS_RESP()+clientName+" si e' disconnesso");
			 end=true;
			break;
		
		case 2 : //comando data: restiamo nella fase goChat
			if(status.equalsIgnoreCase("goChat"))
				Send(command.getDATA_CMD()+clientName + ":"+currentDest+ ":"+command.getDataCMD(clientData));
			else
					SendMSG("Impossibile iniziare la chat");
			break;
		
		case 3: //comando setDest: restiamo nella fase goChat ma cambiamo destinatario
			if(status.equalsIgnoreCase("goChat"))
				currentDest=command.getDataCMD(clientData); 
			break;
			
		case 4: //comando list
			//if(status.equalsIgnoreCase("goChat"))
				SendMSG(getList());
			break;
			
		default : 
			System.out.println("Errore" + "\n"); 
		
		}
		lastListcmd=LocalDateTime.now();
		return end; //viene restituito true quando si desidera uscire dalla chat
	}
		
	/**
	 * metodo per la restituzione del nome del client 
	 * che comunica con il server in questione
	 * @return il nome del Client
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * metodo per l'identificazione della posizione del threadServer attuale all'interno del vettore
	 * @return l'indice che identifica il threadServerserver nel vettore
	 */
	public int findMyPos() {
		int pos=-1;
		for(int i=0; i<ThreadVect.size(); i++) {
			if(((ThreadServer)ThreadVect.elementAt(i)).getClientName().equalsIgnoreCase(clientName) && ThreadVect.elementAt(i).isAlive())//se la condizione non e' verificata significa che nella posizione i del vettore e' presente l'altro ThreadSever
				pos=i;
		}
			return pos;
	}
	
	/**
	 * metodo per l'identificazione della posizione del threadServer richiesto all'interno del vettore
	 * @return l'indice che identifica il threadServerserver nel vettore
	 */
	public int findDest(String name) {
		int pos=-1;
		for(int i=0; i<ThreadVect.size(); i++) {
			if(((ThreadServer)ThreadVect.elementAt(i)).getClientName().equals(name) && ThreadVect.elementAt(i).isAlive())//se la condizione non e' verificata significa che nella posizione i del vettore e' presente l'altro ThreadSever
				pos=i;
		}
			return pos;
	}
	
	
	
	
	/**
	 * metodo per l'invio dei messaggi al client
	 * @param clientMSG String
	 */
	public void SendMSG(String clientMSG) {
		outClient.println(clientMSG);	
		
	}
	/**
	 * metodo per l'invio dei messaggi agli altri client
	 * @param clientData String
	 */
	public void SendToAll(String clientData) {
		int dest = findMyPos();
		if (dest!= -1) {// nelle successive istruzioni viene inviato il messaggio del client gestito da questo ThreadServer, agli altri ThreadServer presenti nell'apposito vettore.
			for(int i=0;i<ThreadVect.size();i++) {
				if(dest!=i) {
//			    ((ThreadServer) ThreadVect.elementAt(i)).SendMSG("--> " + clientData);
				    ((ThreadServer) ThreadVect.elementAt(i)).SendMSG(clientData);					
			    System.out.println(clientData);
				}
			}
		} else {
			SendMSG("Il server risponde: errore di comunicazione con l'altro client");
			System.out.println("Errore di comunicazione con l'altro client");
		}		
	}
	/**
	 * metodo per l'invio dei messaggi all'altro client richiesto
	 * @param clientData String
	 * @param name String
	 */
	public void SendToOther(String clientData) {
		int dest = -1;
		if ((dest = findDest(currentDest)) != -1) {// nelle successive istruzioni viene inviato il messaggio del client gestito da questo ThreadServer, all'apposito ThreadServer
			  ((ThreadServer) ThreadVect.elementAt(dest)).SendMSG(clientData);
			//			  ((ThreadServer) ThreadVect.elementAt(dest)).SendMSG("--> " + clientData);
			  System.out.println(clientData);
		}
	     else {
			SendMSG("Il server risponde: errore di comunicazione con l'altro client");
			System.out.println("Errore di comunicazione con l'altro client");
		}	
	}
	public void Send(String clientData) {
		if(currentDest.equalsIgnoreCase("all"))
			SendToAll(clientData);
		else 
			SendToOther(clientData);
	}
	
	public String getList() {
		String list=command.getLIST_CMD();
		for(int i=0;i<ThreadVect.size();i++)
			if(((ThreadServer) ThreadVect.elementAt(i)).isAlive())
			 list=list+((ThreadServer) ThreadVect.elementAt(i)).getClientName()+"|";
		return list;
	}
	public LocalDateTime getlastListcmd() {
		return lastListcmd;
	}
}
