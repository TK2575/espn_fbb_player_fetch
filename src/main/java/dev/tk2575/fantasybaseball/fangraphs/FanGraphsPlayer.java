package dev.tk2575.fantasybaseball.fangraphs;

import java.math.BigDecimal;

public interface FanGraphsPlayer {

	String getTeam();

	String getName();

	String getPlayerId();

	long getGames();

	BigDecimal getWar();

	long getPoints();
}
