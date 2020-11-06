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
	SharedRegion SR;
  	DBManager dbchat = null;
;
	
	java.util.Vector <String> list;
	/**
	 * Costruttore della classe ThreadClient
	 * 
	 * @param inputServer BufferedReader
	 * @param clientName String
	 */
public ThreadClient (BufferedReader inputServer, String clientName, SharedRegion SR){//viene passato come parametro lo stream per leggere i dati dal server
	this.inputServer=inputServer;
	this.clientName=clientName;
	this.SR= SR;
	command= new CmdUtil();
	list=new 	java.util.Vector <String>(1,1);
	dbchat=new DBManager();
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
			//0511 System.out.println(command.getDataCMD(clientData));
			DataCMD(clientData);
			break;
		case 3: 
			System.out.println(command.getDataCMD(clientData));
			break;	
		case 4: //comando list
			ListCMD(clientData);
			break;
			
		default : 
			System.out.println("Errore" + "\n"); 
		
		}
	}
	
	

	
	
	private void DataCMD(String clientData) {
		// TODO Auto-generated method stub
		String data=command.getDataCMD(clientData);	
		 String dest = data.substring(0, data.indexOf(":"));
		 String mes=data.substring(data.indexOf(":")+1, data.length()-1);
//		 dbchat.insert(clientName, dest, mes);
		 if(SR.get().equalsIgnoreCase(dest))
				System.out.println(mes);
	}

	private void ListCMD(String clientData) {
		StringTokenizer data= new StringTokenizer(command.getDataCMD(clientData),"|");
		list.removeAllElements();
		while(data.hasMoreTokens())
			list.addElement(data.nextToken());
		list.addElement("all");			
//	list.set(list.size()-1, "all");
		SR.put(list);
}
	public java.util.Vector <String> getList() { 
		
		return list;
	}
}

