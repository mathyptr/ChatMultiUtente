package ChatServer;

public class CmdUtil {
	
	String HELLO_CMD="ciao sono"; //comando di identificazione client
	String QUIT_CMD="q!";
	String DATA_CMD="data:";
	String SETDEST_CMD="dest:";
	String LIST_CMD="list";
	String status="initChat";
	java.util.Vector <String> CMD=new java.util.Vector <String> (1,1);
	
	public CmdUtil() {
	CMD.add(HELLO_CMD);
	CMD.add(QUIT_CMD);
	CMD.add(DATA_CMD);
	CMD.add(SETDEST_CMD);
	CMD.add(LIST_CMD);
	}
	
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
	
	public String getDataCMD(String clientData) {
		return clientData.substring(CMD.elementAt(checkCMD(clientData)).length(),clientData.length());
	}
	
	public String getHELLO_CMD() {
		return HELLO_CMD;
	}
	
	public String getDATA_CMD() {
		return DATA_CMD;
	}
	public String getLIST_CMD() {
		return LIST_CMD;
	}
	public String getSETDST_CMD() {
		return SETDEST_CMD;
	}
}
