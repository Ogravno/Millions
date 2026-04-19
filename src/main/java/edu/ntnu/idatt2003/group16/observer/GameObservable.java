package edu.ntnu.idatt2003.group16.observer;

/**
 * Interface for classes that can be observed for changes in the game state.
 */
public interface GameObservable {

  /**
   * Adds an observer that will be notified of changes.
   *
   * @param observer the observer to add
   */
  void addObserver(GameObserver observer);

  /**
   * Removes an observer.
   *
   * @param observer the observer to be removed.
   */
  void removeObserver(GameObserver observer);

  /**
   * Notifies all registered observers that the game state has changed.
   */
  void notifyObservers();
}
