package edu.ntnu.idatt2003.group16.investment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfolio {
  private final List<Share> shares;

  public Portfolio() {
    this.shares = new ArrayList<>();
  }

  public boolean addShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }
    return shares.add(share);
  }

  public boolean removeShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null");
    }
    return shares.remove(share);
  }

  // Get all
  public List<Share> getShares() {
    return Collections.unmodifiableList(shares);
  }

  public List<Share> getShares(String symbol) {
    if (symbol == null || symbol.isBlank()) {
      throw new IllegalArgumentException("Symbol cannot be null or blank.");
    }
    return shares.stream()
      .filter(share -> share.getStock().getSymbol().equals(symbol)).toList();
  }


  public boolean contains(Share share) {
    return shares.contains(share);
  }
}
