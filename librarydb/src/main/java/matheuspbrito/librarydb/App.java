package matheuspbrito.librarydb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {
          try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb","user","123");
            Statement statement = con.createStatement();
            con.close();
          }
          catch(ClassNotFoundException e){
            System.err.println("Driver class not found: " + e.getMessage());
          }
          catch(SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
          }
    }
}
