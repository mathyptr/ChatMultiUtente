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
  	String type="cmd";
  	MessagesBundle msgB;
	java.util.Vector <String> list;
	/**
	 * Costruttore della classe ThreadClient
	 * 
	 * @param inputServer BufferedReader
	 * @param clientName String
	 */
public ThreadClient (BufferedReader inputServer, String clientName, SharedRegion SR, String type,MessagesBundle msgB){//viene passato come parametro lo stream per leggere i dati dal server
	this.inputServer=inputServer;
	this.clientName=clientName;
	this.SR= SR;
	this.type=type;
	this.msgB=msgB;
	command= new CmdUtil();
	list=new 	java.util.Vector <String>(1,1);
	dbchat=new DBManager();
	msgB.SetLanguage("it", "IT");
}

	/**
	 * metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {// metodo che permette la lettura del messaggio dell'altro client
		int index;
		while (!endChat) {
			try {
				result = inputServer.readLine(); // viene letto cio' che invia il server
//				System.out.println("DEBUG: " +result+ "\n");
				index=command.checkCMD(result);
				if (index==-1)
					manageStatus(result);
				else 
					ServerResp(index,result);
			} catch (Exception e) {
//				manageStatus("errore dal server.Bye Bye. Data: "+result);
				manageStatus("errorsrv_exit_msg");
				endChat = true; //in caso di errore con il server viene settata la variabile a true per uscire dal ciclo
			}
		}
	}

	public void manageStatus(String s)
	{
		 String msg=msgB.GetResourceValue(s);
		
		 if(type.equalsIgnoreCase("cmd"))
			 System.out.println(msg);
		 else
			 SR.pushStatusMSG(msg);
	}

	public void sendInfoInStatus(String s)
	{
	
		 if(type.equalsIgnoreCase("cmd"))
			 System.out.println(s);
		 else
			 SR.pushStatusMSG(s);
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
		 String src = data.substring(0, data.indexOf(":"));
		 String mes=data.substring(data.indexOf(":")+1, data.length());
		 dbchat.insert( src,clientName, mes);
		 if(SR.getDest().equalsIgnoreCase(src)||SR.getDest().equalsIgnoreCase("all"))
		 {
			 if(type.equalsIgnoreCase("cmd"))
				 System.out.print("--> ");
			 System.out.println(mes);
		 }
		 else
			 sendInfoInStatus(src+": "+mes);
	}

	private void ListCMD(String clientData) {
		StringTokenizer data= new StringTokenizer(command.getDataCMD(clientData),"|");
		list.removeAllElements();
		String name;
		while(data.hasMoreTokens()) {
			name=data.nextToken();
			if(!name.equals(clientName))
				list.addElement(name);
	}
		list.addElement("all");			
//	list.set(list.size()-1, "all");
		SR.putList(list);
}
	public java.util.Vector <String> getList() { 
		return SR.getList();	
//		return list;
	}
}

