package edu.ntnu.idatt2003.group16.model.player;

/**
 * Tells the player's progress
 *
 * <p>
 *   {@code NOVICE} - Start level, no qualification needed.
 * </p>
 * <p>
 *   {@code INVESTOR} - Level for players with at least 10 week playtime and
 *    have at least 20% profit
 * </p>
 * <p>
 *   {@code SPECULATOR} - Level for players with at least 20 week playtime and
 *   have at least 100% profit
 * </p>
 *
 * @author Robin Strand Prestmo
 */
public enum Status {
  NOVICE,
  INVESTOR,
  SPECULATOR
}