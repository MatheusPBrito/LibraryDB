package matheuspbrito.librarydb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class App {
    public static void main(String[] args) {
          try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/","user","123");
            Statement statement = con.createStatement();
            String command = "CREATE DATABASE IF NOT EXISTS librarydb";
            statement.executeUpdate(command);
            command = "USE librarydb";
            statement.executeUpdate(command);
            command = "CREATE TABLE IF NOT EXISTS authors(id INT PRIMARY KEY, name varchar(30), country varchar(30))";
            statement.executeUpdate(command);
            command = "CREATE TABLE IF NOT EXISTS books(id INT PRIMARY KEY, name varchar(30), authorid int, FOREIGN KEY (authorid) REFERENCES authors(id))";
            statement.executeUpdate(command);
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
