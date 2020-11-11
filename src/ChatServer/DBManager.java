package ChatServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Classe per la gestione del database
 * @author Patrissi Mathilde
 */
public class DBManager {
    String url = "jdbc:sqlite:.//db//chatDB.sqlite"; 
    /**
     * Metodo per la connessione al database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
//        String url = "jdbc:sqlite:C://Users//mathy//eclipse-workspace//ChatClientGUI//bin//db//chatDB.sqlite";
   	
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Metodo per l'inserimento di una riga nel database
     *
     * @param src String
     * @param dest String
     * @param mes String
     */
    public void insert(String src, String dest, String group, String mes) {
        String sql = "INSERT INTO messages(source,dest,groupm,mess) VALUES(?,?,?,?)";

        try {
        	Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, src);
            pstmt.setString(2,dest);
            pstmt.setString(3,group);
            pstmt.setString(4,mes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metodo per prelevare i messaggi richiesti
     *
     * @param source String
     * @param dest String
     * @param il vettore contenente i messaggi
     */
    public java.util.Vector <String[]> msgFromChat(String dest,String source) {
//    	 String sql = "SELECT source,dest,groupm,mess FROM messages where (source='"+source+"' and groupm='"+dest+"') or  (source='"+dest+"' and groupm='"+dest+"') order by insert_at";
//   	 	 String sql = "SELECT source,dest,groupm,mess FROM messages where groupm='"+dest+"' order by insert_at";
    	String sql ="select source,dest,mess,groupm from messages where  (source='"+source+"' and groupm='"+dest+"') or  (source='"+dest+"' and groupm='"+source+"') or (dest='"+source+"' and groupm='"+dest+"') order by insert_at"; 

    	 java.util.Vector <String[]> msg=new java.util.Vector <String[]>(1,1);
    	 String[] row;
    	 try{
     	Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
     	ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
               row= new String[4];
               row[0]=rs.getString("source");
               row[1]=rs.getString("dest");     
               row[2]=rs.getString("groupm");
               row[3]=rs.getString("mess");               
               msg.add(row);
             }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    	 return msg;
    }
    /**
     * @param args the command line arguments
     */
  /*  public static void main(String[] args) {

        InsertApp app = new InsertApp();
        // insert three new rows
        app.insert("Raw Materials", 3000);
        app.insert("Semifinished Goods", 4000);
        app.insert("Finished Goods", 5000);
    }
*/
}