package bank;

import bank.exceptions.AmountException;

public class Account {
  // properties
  private int id;
  private String type;
  private double balance;

  // constructor
  public Account(int id, String type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  // getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) throws AmountException{
    if (amount < 1) {
      throw new AmountException("Amount must be at least $1.00");
    } 
    else {
      double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
  }

  public void withdraw(double amount) throws AmountException {
    if (amount < 0) {
      throw new AmountException("The withdrawal amount must be at least $1.00");
    } 
    else if (amount > getBalance()){
      throw new AmountException("You do not have enough funds to withdraw that amount");
    } 
    else {
      double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
    }
  }
}
