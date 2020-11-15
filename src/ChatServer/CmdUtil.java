package ChatServer;
/**
 * Classe per l'implementazione del protocollo di comunicazione
 * @author Patrissi Mathilde
 */
public class CmdUtil {
	
	String HELLO_CMD="ciao sono"; //comando di identificazione client
	String QUIT_CMD="q!";
	String DATA_CMD="data:";
	String SETDEST_CMD="dest:";
	String LIST_CMD="list";
	String STATUS_RESP="status";//1411
	String status="initChat";
	java.util.Vector <String> CMD=new java.util.Vector <String> (1,1);
	
	 /**Costruttore della classe CmdUtil
		 */
	public CmdUtil() {
	CMD.add(HELLO_CMD);
	CMD.add(QUIT_CMD);
	CMD.add(DATA_CMD);
	CMD.add(SETDEST_CMD);
	CMD.add(LIST_CMD);
	CMD.add(STATUS_RESP);//1411
	}
	 /**
		 * Metodo che permette di individuare il comando richiesto
		 * 
		 * @param clientData String
		 */
	public int checkCMD(String clientData) {
		int pos=-1;
		for(int i=0;i<CMD.size();i++) {
			if(clientData.length()>=CMD.elementAt(i).length()) {
			 int cmdLenght=CMD.elementAt(i).length();
			 String cmd = clientData.substring(0, cmdLenght);
			 if(cmd.equalsIgnoreCase(CMD.elementAt(i))) {
			   	pos=i;
				break;
			  }
			}
		}
		return pos;
	}
	/**
	 * Metodo per prelevare dal pacchetto il messaggio dell'utente
	 * @param clientData
	 * @return il messaggio dell'utente
	 */
	public String getDataCMD(String clientData) {
		return clientData.substring(CMD.elementAt(checkCMD(clientData)).length(),clientData.length());
	}
	/**
	 * Metodo che restituisce il comando di hello
	 * @return il comando di hello
	 */
	public String getHELLO_CMD() {
		return HELLO_CMD;
	}
	/**
	 * Metodo che restituisce il comando data
	 * @return il comando di data
	 */
	public String getDATA_CMD() {
		return DATA_CMD;
	}
	/**
	 * Metodo che restituisce il comando per la richiesta della lista degli utenti connessi
	 * @return il comando per la richiesta della lista degli utenti connessi
	 */
	public String getLIST_CMD() {
		return LIST_CMD;
	}
	/**
	 * Metodo che restituisce il comando per settare il destinatario
	 * @return il comando per settare il destinatario
	 */
	public String getSETDST_CMD() {
		return SETDEST_CMD;
	}
	/**
	 * Metodo che restituisce il comando per uscire
	 * @return il comando per uscire
	 */
	public String getQUIT_CMD() {
		return QUIT_CMD;
	}
	/**
	 * Metodo che restituisce il comando di stato
	 * @return il comando di stato
	 */
	public String getSTATUS_RESP() {
		return STATUS_RESP;
	}
}
