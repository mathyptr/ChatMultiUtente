package ChatServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author sqlitetutorial.net
 */
public class DBManager {
    String url = "jdbc:sqlite:.//db//chatDB.sqlite"; 
    /**
     * Connect to the test.db database
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
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param capacity
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

    public java.util.Vector <String> msgFromChat(String dest,String source) {
    	 String sql = "SELECT mess FROM messages where source='"+source+"' and dest='"+dest+"' order by insert_at";
    	 java.util.Vector <String> msg=new java.util.Vector <String>(1,1);
    	 try{
     	Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
     	ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
               msg.add(rs.getString("mess"));
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