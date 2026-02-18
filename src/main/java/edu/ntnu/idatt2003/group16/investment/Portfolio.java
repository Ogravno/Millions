package edu.ntnu.idatt2003.group16.investment;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private final List<Share> shares;

  public Portfolio() {
    this.shares = new ArrayList<>();
  }

  public boolean addShare(Share share) {
    return shares.add(share);
  }

  public boolean removeShare(Share share) {
    return shares.remove(share);
  }

  // Get all
  public List<Share> getShares() {
    return shares;
  }

  // Get specific, Dobbelsjekk denne
  public List<Share> getShares(String symbol) {
    return shares.stream()
      .filter(share -> share.getStock().getSymbol().equals(symbol)).toList();
    }


  public boolean contains(Share share) {
    return shares.contains(share);
  }
}
