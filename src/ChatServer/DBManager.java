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
    public void insert(String src, String dest,String mes) {
        String sql = "INSERT INTO messages(source,dest,mess) VALUES(?,?,?)";

        try {
        	Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, src);
            pstmt.setString(2,dest);
            pstmt.setString(3,mes);
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
    	 String sql = "SELECT source,mess FROM messages where source='"+source+"' and dest='"+dest+"' or source='"+dest+"' and dest='"+source+"' order by insert_at";
    	 java.util.Vector <String[]> msg=new java.util.Vector <String[]>(1,1);
    	 String[] row;
    	 try{
     	Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
     	ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
               row= new String[2];
               row[0]=rs.getString("source");
               row[1]=rs.getString("mess");               
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