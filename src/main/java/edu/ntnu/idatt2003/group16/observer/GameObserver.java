package edu.ntnu.idatt2003.group16.observer;

/**
 * Interface for classes that want to observe changes in a state.
 */
public interface GameObserver {

  /**
   * Called when the game state has changed.
   */
  void onGameStateChanged();
}
