package edu.ntnu.idatt2003.group16.model.dto;

import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.player.Player;

/**
 * Record storing some data from GameSession.
 *
 * <p>Used for serializing and deserializing game data json</p>
 *
 * @param gameName the game's name
 * @param player the player
 * @param exchange the exchange
 * @author Odin Grav
 */
public record GameSessionDto(String gameName, Player player, Exchange exchange) {}
