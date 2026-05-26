package edu.ntnu.idatt2003.group16.model.player;

import edu.ntnu.idatt2003.group16.model.investment.Portfolio;
import edu.ntnu.idatt2003.group16.model.transaction.Purchase;
import edu.ntnu.idatt2003.group16.model.transaction.TransactionArchive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a player.
 */
public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;
  private final Map<Integer, BigDecimal> weeklyNetWorth;

  /**
   * Constructor for the {@link Player} class.
   *
   * @param name the name of the player
   * @param startingMoney the money the player will start with
   * @throws IllegalArgumentException when {@code name} is null or blank
   * @throws IllegalArgumentException when {@code startingMoney} is null, negative or zero
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
    this.weeklyNetWorth = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public BigDecimal getStartingMoney() {
    return startingMoney;
  }

  public BigDecimal getMoney() {
    return money;
  }

  /**
   * Gets the player's money formatted to 2 decimals.
   *
   * <p>Used for GUI display only.</p>
   *
   * @return the player's money formatted to 2 decimals
   */
  public String getFormattedMoney() {
    return money.setScale(2, RoundingMode.HALF_UP).toString();
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

  /**
   * Get the players net worth for the specified week.
   *
   * <p>The player's net worth is calculated by adding together the player's money
   * and the net worth of the player's portfolio.</p>
   *
   * @param week week to get the net worth from
   * @return the player's net worth
   * @throws IllegalArgumentException if week is not positive
   */
  public BigDecimal getNetWorth(int week) {
    if (week < 1) {
      throw new IllegalArgumentException("Parameter week must be positive");
    }

    return weeklyNetWorth.get(week);
  }

  /**
   * Saves the net worth of the player.
   *
    * @param week week to get the net worth from
   */
  public void saveNetWorth(int week) {
    if (week < 1) {
      throw new IllegalArgumentException("Parameter week must be positive");
    }

    weeklyNetWorth.put(week, getNetWorth());
  }

  /**
   * Gets the player's net worth formatted to 2 decimals.
   *
   * <p>Used for GUI display only.</p>
   *
   * @return the player's net worth formatted to 2 decimals
   */
  public String getFormattedNetWorth() {
    return getNetWorth().setScale(2, RoundingMode.HALF_UP).toString();
  }

  /**
   * Gets the current status of the player based on the current game week
   * and the player's net worth.
   *
   * <P>{@code NOVICE} - Starting level for player. No qualification needed.
   * </P>
   *
   * <P>{@code INVESTOR} - Player reaches INVESTOR if the player has played for minimum 10 weeks
   *   and have at least 20% profit.
   * </P>
   *
   * <P>{@code SPECULATOR} - Player reaches SPECULATOR if the player has played for minimum 20 weeks
   *    *   and have at least doubled his investments.
   * </P>
   *
   * @param currentWeek the current week of the game.
   * @return the player's current status
   */
  public Status getStatus(int currentWeek) {
    BigDecimal twentyPercentThreshold = startingMoney.multiply(new BigDecimal("1.2"));
    BigDecimal doubleThreshold = startingMoney.multiply(new BigDecimal("2"));
    BigDecimal netWorth = getNetWorth();

    if (currentWeek >= 20 && netWorth.compareTo(doubleThreshold) >= 0) {
      return Status.SPECULATOR;
    } else if (currentWeek >= 10 && netWorth.compareTo(twentyPercentThreshold) >= 0) {
      return Status.INVESTOR;
    } else {
      return Status.NOVICE;
    }
  }

}
