package ChatServer;
import java.io.BufferedReader;
import java.util.StringTokenizer;
/**
 * Classe per l'implementazione di un Thread Client
 * @author Patrissi Mathilde
 */
public class ThreadClient extends Thread{
	BufferedReader inputServer;
	String result,clientName;
	boolean endChat=false;
	CmdUtil command;
	java.util.Vector <String> list;
	/**
	 * Costruttore della classe ThreadClient
	 * 
	 * @param inputServer BufferedReader
	 * @param clientName String
	 */
public ThreadClient (BufferedReader inputServer, String clientName){//viene passato come parametro lo stream per leggere i dati dal server
	this.inputServer=inputServer;
	this.clientName=clientName;
	command= new CmdUtil();
	list=new 	java.util.Vector <String>(1,1);
}

	/**
	 * metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {// metodo che permette la lettura del messaggio dell'altro client
		int index;
		while (!endChat) {
			try {
				result = inputServer.readLine(); // viene letto cio' che invia il server
				index=command.checkCMD(result);
				if (index==-1)
					System.out.println(result);
				else 
					ServerResp(index,result);
			} catch (Exception e) {
				System.out.println("errore dal server");
				endChat = true; //in caso di errore con il server viene settata la variabile a true per uscire dal ciclo
			}
		}
	}

	
	public void ServerResp(int index, String clientData) {
		boolean end=false;
		switch(index) { 
		case 0:  
			System.out.println(command.getDataCMD(clientData));
			break;
		case 1: 
			System.out.println(command.getDataCMD(clientData));
			endChat = true; //viene settata la variabile a true poiche' l'utente ha digitato il comando di uscita
			break;
		case 2: 
			System.out.println(command.getDataCMD(clientData));
			break;
		case 3: 
			System.out.println(command.getDataCMD(clientData));
			break;	
		case 4: //comando list
			StringTokenizer data= new StringTokenizer(command.getDataCMD(clientData),"|");
			while(data.hasMoreTokens())
			list.addElement(data.nextToken());
			break;
			
		default : 
			System.out.println("Errore" + "\n"); 
		
		}
	}
	
	public java.util.Vector <String> getList() { 
		list.set(list.size()-1, "all");
		return list;
	}
}

