package dev.tk2575.fantasybaseball;

import dev.tk2575.fantasybaseball.espn.entities.Player;
import dev.tk2575.fantasybaseball.espn.models.TeamRoster;
import dev.tk2575.fantasybaseball.espn.services.PlayerService;
import dev.tk2575.fantasybaseball.fangraphs.FanGraphsCSVParser;
import dev.tk2575.fantasybaseball.fangraphs.FanGraphsPlayer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.Duration.ofMillis;

@Log4j2
public class AnalysisService {

	private RestTemplate restTemplate() {
		return new RestTemplateBuilder().setConnectTimeout(ofMillis(500000)).setReadTimeout(ofMillis(500000)).build();
	}

	public List<TeamRoster> getRostersWithProjections() {
		List<TeamRoster> rosters = new PlayerService(restTemplate()).getTeamRosters(Integer.toString(LocalDate.now().getYear()));
		Map<String, FanGraphsPlayer> projections = new FanGraphsCSVParser().getAllProjections();

		String playerName;
		for (TeamRoster roster : rosters) {
			for (Player player : roster.getPlayers()) {
				playerName = player.getFullName();
				if (player.getDefaultPositionId() == 1 || player.getDefaultPositionId() == 11) {
					playerName = playerName + "-1";
				}
				if (projections.containsKey(playerName)) {
					player.setPoints(projections.get(playerName).getPoints());
					projections.remove(playerName);
				}
				else {
					log.warn(String.format("Could not find projection for %s", playerName));
				}
			}
		}

		log.info("---Projected points per team---");
		for (TeamRoster roster : rosters) {
			log.info(String.format("%s:\t%s", roster.getName(), roster.getPoints()));
		}
		return rosters;
	}

	public static void main(String[] args) {
		new AnalysisService().getRostersWithProjections();
		System.exit(0);
	}
}
