package edu.ntnu.idatt2003.group16.investment;

import edu.ntnu.idatt2003.group16.transaction.calculator.SaleCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Portfolio} class represents a collection of the user's shares.
 * Includes features such as addShare, removeShare, getShares and contains.
 *
 * @author Robin Strand Prestmo
 */
public class Portfolio {
  private final List<Share> shares;

  /**
   * Creates an empty portfolio.
   */
  public Portfolio() {
    this.shares = new ArrayList<>();
  }

  /**
   * Adds a new share to the portfolio.
   *
   * @param share to be added.
   * @return {@code true} if the share was added.
   * @throws IllegalArgumentException if share is null.
   * @throws IllegalArgumentException if share is already in portfolio
   */
  public boolean addShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }
    if (shares.contains(share)) {
      throw new IllegalArgumentException("Share already in portfolio");
    }

    return shares.add(share);
  }

  /**
   * Removes a share from the portfolio.
   *
   * @param share to be removed.
   * @return {@code true} if the share was removed.
   * @throws IllegalArgumentException if share is null.
   * @throws IllegalArgumentException if share is not in portfolio
   */
  public boolean removeShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }
    if (!shares.contains(share)) {
      throw new IllegalArgumentException("Share not in portfolio");
    }

    return shares.remove(share);
  }

  /**
   * Get all shares in the portfolio.
   *
   * @return an unmodifiable list with all the shares.
   */
  public List<Share> getShares() {
    return Collections.unmodifiableList(shares);
  }

  /**
   * Get all shares with the chosen symbol.
   *
   * @param symbol to filter the right shares.
   * @return a list with shares that matches the symbol.
   * @throws IllegalArgumentException if symbol is null or blank.
   */
  public List<Share> getShares(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return shares.stream()
      .filter(share -> share.getStock().getSymbol().equals(symbol)).toList();
  }

  /**
   * Checks if the portfolio contains the given share.
   *
   * @param share the share to check for.
   * @return {@code true} if the portfolio contains the share.
   */
  public boolean contains(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }

    return shares.contains(share);
  }

  /**
   * Sums the sales value of all the shares in the portfolio.
   *
   * @return the sum of the shares in the portfolio
   */
  public BigDecimal getNetWorth() {
    return shares.stream()
        .map(share -> {
          SaleCalculator calculator = new SaleCalculator(share);
          return calculator.calculateTotal();
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
