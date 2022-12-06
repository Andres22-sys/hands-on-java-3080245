package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticatorUser();
    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }
    




    menu.scanner.close();
  }

  private Customer authenticatorUser(){
    System.out.println("Please enter your username: ");
    String username = scanner.nextLine();

    System.out.println("Please enter your password: ");
    String password = scanner.nextLine();
    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("Login failed: " + e.getMessage());
    }
    return customer;
  }

  /**
   * @param customer
   * @param account
   */
  private void showMenu(Customer customer, Account account){
    int choice = 0;

    while(choice != 4 && customer.isAuthenticated()){
      System.out.println("====================================================");
      System.out.println("Please select one of the following options:");
      System.out.println("1. View my balance");
      System.out.println("2. Withdraw");
      System.out.println("3. Deposit");
      System.out.println("4. Logout");
      System.out.println("====================================================");

      choice = scanner.nextInt();
      scanner.nextLine();
      double amount = 0;

      switch(choice){
        case 1:
          System.out.println("Your balance is: " + account.getBalance());
          break;
        case 2:
          System.out.println("Please enter the amount to withdraw: ");
          amount = scanner.nextDouble();
          scanner.nextLine();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Withdraw failed: ");
          }
          break;
        case 3:
          System.out.println("Please enter the amount to deposit: ");
          amount = scanner.nextDouble();
          scanner.nextLine();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Deposit failed: ");
          }
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("You have been logged out");
          break;
        default:
          System.out.println("Invalid choice");
      }
    }
  }
}
