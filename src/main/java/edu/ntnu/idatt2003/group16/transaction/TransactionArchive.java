package edu.ntnu.idatt2003.group16.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an archive of transactions.
 *
 * @author Odin Grav
 */
public class TransactionArchive {
  private final List<Transaction> transactions;

  /**
   * Constructor for the {@link TransactionArchive} class.
   */
  public TransactionArchive() {
    this.transactions = new ArrayList<>();
  }

  /**
   * Adds a transaction.
   *
   * @param transaction the transaction to be added
   * @return true if the {@link #transactions} changed, false if it did not change
   * @throws IllegalArgumentException when transaction is null
   */
  public boolean add(Transaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("transaction cannot be null");
    }

    return transactions.add(transaction);
  }

  /**
   * Chacks if the transaction archive is empty.
   *
   * @return true if archive is empty, full if it is not empty
   */
  public boolean isEmpty() {
    return transactions.isEmpty();
  }

  /**
   * Gets the transactions completed during the specified week.
   *
   * @param week the week to get transactions from
   * @return the transactions completed during the specified week
   * @throws IllegalArgumentException when week is not a positive integer
   */
  public List<Transaction> getTransactions(int week) {
    weekValidator(week);

    return transactions.stream()
        .filter(transaction -> transaction.getWeek() == week)
        .toList();
  }

  /**
   * Gets the purchases completed during the specified week.
   *
   * @param week the week to get purchases from
   * @return the purchases completed during the specified week
   * @throws IllegalArgumentException when week is not a positive integer
   */
  public List<Transaction> getPurchases(int week) {
    weekValidator(week);

    return transactions.stream()
        .filter(transaction -> transaction instanceof Purchase)
        .filter(purchase -> purchase.getWeek() == week)
        .toList();
  }

  /**
   * Gets the sales completed during the specified week.
   *
   * @param week the week to get sales from
   * @return the sales completed during the specified week
   * @throws IllegalArgumentException when week is not a positive integer
   */
  public List<Transaction> getSales(int week) {
    weekValidator(week);

    return transactions.stream()
        .filter(transaction -> transaction instanceof Sale)
        .filter(sale -> sale.getWeek() == week)
        .toList();
  }

  /**
   * Counts the number of distinct weeks associated with a transaction.
   *
   * @return the number of distinct weeks associated with a transaction
   */
  public int countDistinctWeeks() {
    return (int) transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .count();
  }

  /**
   * Validates if a given week is valid.
   *
   * <p>The week must be a positive integer</p>
   *
   * @param week the week to validate
   * @throws IllegalArgumentException when week is not a positive integer
   */
  private void weekValidator(int week) {
    if (week <= 0) {
      throw new IllegalArgumentException("week must be a positive integer");
    }
  }
}