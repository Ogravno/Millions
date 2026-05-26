package edu.ntnu.idatt2003.group16.model.investment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.ntnu.idatt2003.group16.model.transaction.calculator.SaleCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of the player's shares.
 *
 * <p>The portfolio supports adding, removing, retrieving,
 * and checking owned shares.</p>
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
   * @param share the share to add
   * @return true if the share was added
   * @throws IllegalArgumentException if the share is null
   * @throws IllegalArgumentException if the share is already in the portfolio
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
   * @param share the share to remove
   * @return true if the share was removed
   * @throws IllegalArgumentException if the share is  null
   * @throws IllegalArgumentException if the share is not in the portfolio
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
   * @param symbol the stock symbol to filter by
   * @return a list of shares matching the symbol
   * @throws IllegalArgumentException if the symbol is null or blank
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
   * @param share the share to check for
   * @return true if the portfolio contains the share
   * @throws IllegalArgumentException if the share is  null
   */
  public boolean contains(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }

    return shares.contains(share);
  }

  /**
   * Calculates the portfolio's net worth.
   *
   * <p>The portfolio's net worth is calculated by summing the sales value of all the shares in
   * the portfolio.</p>
   *
   * @return the portfolio's net worth
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
