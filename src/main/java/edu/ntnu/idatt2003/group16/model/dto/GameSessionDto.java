package edu.ntnu.idatt2003.group16.model.dto;

import edu.ntnu.idatt2003.group16.model.exchange.Exchange;
import edu.ntnu.idatt2003.group16.model.player.Player;

public record GameSessionDto(String gameName, Player player, Exchange exchange) {}
