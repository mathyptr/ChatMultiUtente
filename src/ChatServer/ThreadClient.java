package ChatServer;
import java.io.BufferedReader;
import java.util.Calendar;
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
//	msgB.SetLanguage("it", "IT");
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

	public void setLanguage(MessagesBundle msgB)
	{
		this.msgB=msgB;
	}	
	/**
	 * metodo per la memorizzazione del messaggio del server
	 * @param s String
	 */
	public void manageStatus(String s)
	{
		 String msg=msgB.GetResourceValue(s);
		
		 if(type.equalsIgnoreCase("cmd"))
			 System.out.println(msg);
		 else
			 SR.pushStatusMSG(msg);
	}
	/**
	 * metodo che permette notificare nella barra di stato i nuovi messaggi di altre chat 
	 * @param s String
	 */
	public void sendInfoInStatus(String s)
	{
	
		 if(type.equalsIgnoreCase("cmd"))
			 System.out.println(s);
		 else
			 SR.pushStatusMSG(s);
	}
	/**
	 * metodo per la gestione dei messaggi dal server
	 * @param index int
	 * @param clientData String
	 */
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
	
	

	/**
	 * metodo per la gestione e visualizzazione dei messaggi nella chat
	 * @param clientData String
	 */
	
	private void DataCMD(String clientData) {
		// TODO Auto-generated method stub
		String data=command.getDataCMD(clientData);	
		 String src = data.substring(0, data.indexOf(":"));
//10112020		 String mes=data.substring(data.indexOf(":")+1, data.length());
		 String pack1 =data.substring(data.indexOf(":")+1, data.length());;
		 String group=pack1.substring(0, pack1.indexOf(":"));
		 String mes=pack1.substring(pack1.indexOf(":")+1, pack1.length());
		 
//11112020		 dbchat.insert( src,clientName,group, src+": "+mes);
		 dbchat.insert( src,clientName,group,mes);
		 if((SR.getDest().equalsIgnoreCase(src)&&group.equalsIgnoreCase(clientName))||SR.getDest().equalsIgnoreCase("all"))
		 {
			 if(type.equalsIgnoreCase("cmd"))
				 System.out.println("--> "+src+": "+mes);
			 else if (SR.getDest().equalsIgnoreCase("all"))
				 System.out.println(src+": "+mes);
			 else
				 System.out.println(mes);
			 Calendar now = Calendar.getInstance();
			 System.out.println(now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+"\n");                		 
//11112020			 System.out.println(src+": "+mes);
		 }
		 else
			 sendInfoInStatus(src+": "+mes);
	}
	/**
	 * metodo per la gestione della lista degli utenti connessi
	 * @param clientData String
	 */
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
	/**
	 * metodo per conoscere la lista degli utenti connessi
	 * @return la lista degli utenti connessi
	 */
	public java.util.Vector <String> getList() { 
		return SR.getList();	
//		return list;
	}
}

