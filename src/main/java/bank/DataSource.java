package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We are connected");
    } catch (SQLException e) {
      e.getStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "SELECT * FROM customers WHERE username = ?";
    Customer customer = null;

    try(Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql)){
        
        statement.setString(1, username);
        try(ResultSet resltSet = statement.executeQuery()){
            customer = new Customer(
              resltSet.getInt("id"), 
              resltSet.getString("name"), 
              resltSet.getString("username"),
              resltSet.getString("password"), 
              resltSet.getInt("account_id"));
        }
      
    } catch (SQLException e) {
      e.getStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId){
    String sql = "SELECT * FROM accounts WHERE id = ?";
    Account account = null;

    try(Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql)){
        
        statement.setInt(1, accountId);
        try(ResultSet resltSet = statement.executeQuery()){
            account = new Account(
              resltSet.getInt("id"), 
              resltSet.getString("type"), 
              resltSet.getDouble("balance"));
        }
      
    } catch (SQLException e) {
      e.getStackTrace();
    }
    return account;
  }
  
  public static void updateAccountBalance(int accountId, double balance){
    String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
    try(Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql)){
        
        statement.setDouble(1, balance);
        statement.setInt(2, accountId);
        statement.executeUpdate();
      
    } catch (SQLException e) {
      e.getStackTrace();
    }
  }
  
  //Commented out for testing purposes
  // public static void main(String[] args) {
  //   Customer customer = getCustomer("twest8o@friendfeed.com");
  //   System.out.println(customer.getName());
  //   Account account = getAccount(customer.getAccountId());
  //   System.out.println(account.getBalance());
  // }
}
