package edu.ntnu.idatt2003.group16.player;

import edu.ntnu.idatt2003.group16.investment.Portfolio;
import edu.ntnu.idatt2003.group16.transaction.TransactionArchive;
import java.math.BigDecimal;

/**
 * Class representing a player.
 */
public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;
  private Status status;

  /**
   * Constructor for the {@link Player} class.
   *
   * @param name the name of the player
   * @param startingMoney the money the player will start with
   * @throws IllegalArgumentException when {@code name} is null or blank
   * @throws IllegalArgumentException when {@code startingMoney} null, negative or zero
   */
  public Player(String name, BigDecimal startingMoney) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }

    moneyValidation(startingMoney, "startingMoney");

    this.name = name;
    this.startingMoney = startingMoney;
    this.money = new BigDecimal(startingMoney.toString());
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMoney() {
    return money;
  }

  /**
   * Adds money.
   *
   * @param amount the amount of money to add
   * @throws IllegalArgumentException if {@code amount} is null
   * @throws IllegalArgumentException if {@code amount} is negative or zero
   */
  public void addMoney(BigDecimal amount) {
    moneyValidation(amount, "amount");

    money = money.add(amount);
  }

  /**
   * Withdraws money.
   *
   * @param amount the amount of money to withdraw
   * @throws IllegalArgumentException if {@code amount} is null
   * @throws IllegalArgumentException if {@code amount} is negative or zero
   * @throws IllegalArgumentException if {@code amount} is larger than player's amount of money
   */
  public void withdrawMoney(BigDecimal amount) {
    moneyValidation(amount, "amount");
    if (amount.compareTo(getMoney()) > 0) {
      throw new IllegalArgumentException("Player does not have enough money");
    }

    money = money.subtract(amount);
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public TransactionArchive getTransactionArchive() {
    return transactionArchive;
  }

  /**
   * Validates if a given amount of money is valid.
   *
   * @param money the amount of money to validate
   * @param parameterName the name of the parameter being validated
   * @throws IllegalArgumentException if {@code money} is null
   * @throws IllegalArgumentException if {@code money} is negative or zero
   */
  private void moneyValidation(BigDecimal money, String parameterName) {
    if (money == null) {
      throw new IllegalArgumentException(parameterName + " cannot be null");
    }

    if (money.signum() != 1) {
      throw new IllegalArgumentException(parameterName + " has to be positive");
    }
  }

  /**
   * Calculates the player's net worth.
   *
   * <p>The player's net worth is calculated by adding together the player's money
   * and the net worth of the player's portfolio.</p>
   *
   * @return the player's net worth
   */
  public BigDecimal getNetWorth() {
    return money.add(portfolio.getNetWorth());
  }
}
