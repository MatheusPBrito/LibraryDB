package matheuspbrito.librarydb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

          try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/","user","123");
            Statement statement = con.createStatement();
            String command = "CREATE DATABASE IF NOT EXISTS librarydb";
            statement.executeUpdate(command);
            command = "USE librarydb";
            statement.executeUpdate(command);
            command = "CREATE TABLE IF NOT EXISTS authors(id INT PRIMARY KEY AUTO_INCREMENT, name varchar(30), country varchar(30))";
            statement.executeUpdate(command);
            command = "CREATE TABLE IF NOT EXISTS books(id INT PRIMARY KEY AUTO_INCREMENT, name varchar(30), authorid int, FOREIGN KEY (authorid) REFERENCES authors(id))";
            statement.executeUpdate(command);

            while(true){
              System.out.println("(1) Adicionar autor (2) Adicionar livro (3) Listar autores (4) Listar livros (5) Sair");
              int opcao = 0;
              while(input.hasNext()){
                if (input.hasNextInt()){
                    opcao = input.nextInt();
                    input.nextLine();
                    break;
                }
                else
                  System.out.println("Opção invalida: " + input.next());
                  break;
              }
              
              switch (opcao){
                  case 1:
                    System.out.print("Digite o nome do autor: ");
                    String nomeAutor = input.nextLine();
                    System.out.print("Digite o seu pais natal: ");
                    String paisNatal = input.nextLine();
                    command = "INSERT INTO authors(name, country) VALUES (?,?)";
                    try(PreparedStatement ps = con.prepareStatement(command)){
                      ps.setString(1, nomeAutor);
                      ps.setString(2, paisNatal);
                      ps.executeUpdate();
                      System.out.println("Autor: " + nomeAutor + " Adicionado!");
                    } catch (SQLException e){
                      e.printStackTrace();
                    }
                    break;
                  case 2:
                    System.out.println("Digite o nome do Livro: ");
                    String titulo = input.nextLine();
                    System.out.println("Digite o autor do Livro: ");
                    String autorLivro = input.nextLine();
                    command = "SELECT ID FROM authors WHERE name = ?";
                    int authorid = 0; 
                    try(PreparedStatement ps = con.prepareStatement(command)){
                          ps.setString(1, autorLivro);
                          ResultSet resultSet = ps.executeQuery();
                          while(resultSet.next()){
                            authorid = resultSet.getInt("id");
                          }
                          if(authorid != 0){
                              command = "INSERT INTO books(name,authorid) VALUES (?,?)";
                              try (PreparedStatement insertPs = con.prepareStatement(command)){
                                insertPs.setString(1,titulo);
                                insertPs.setInt(2,authorid);
                                insertPs.executeUpdate();
                                System.out.println("Livro adicionado!");
                              } catch(SQLException e){
                                e.printStackTrace();
                              }
                          }
                          else 
                            System.out.println("Autor não encontrado"); 

                    } catch(SQLException e){
                      e.printStackTrace();
                    }
                  default:
                    break;
              }
              if(opcao == 5)
                  break;
            }
            
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
