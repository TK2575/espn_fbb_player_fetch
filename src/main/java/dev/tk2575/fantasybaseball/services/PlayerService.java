package dev.tk2575.fantasybaseball.services;

import dev.tk2575.fantasybaseball.entities.Player;
import dev.tk2575.fantasybaseball.entities.Team;
import dev.tk2575.fantasybaseball.entities.Topics;
import dev.tk2575.fantasybaseball.models.TeamRoster;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Log4j2
public class PlayerService extends ESPNService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

	private final RestTemplate restTemplate;

	public PlayerService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Return a list of all teams in the league with their current rosters
	 */
	public List<TeamRoster> getTeamRosters(String leagueYear) {
		// https://fantasy.espn.com/apis/v3/games/flb/seasons/2019/segments/0/leagues/30710/teams/

		HttpHeaders espnRequestHeaders = getEspnRequestHeaders(leagueYear, null);
		log.info(String.format("Request Headers: %s", espnRequestHeaders));
		HttpEntity<Topics> entity = new HttpEntity<>(espnRequestHeaders);
		List<TeamRoster> teams = new ArrayList<>();

		try {
			String url = "https://fantasy.espn.com/apis/v3/games/flb/seasons/" + leagueYear + "/segments/0/leagues/" + LEAGUE_ID + "/teams";
			log.info(String.format("url: %s", url));
			ResponseEntity<List<Team>> teamResponse = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
			if (teamResponse.getBody() != null) {
				teamResponse.getBody().forEach(teamToFetch -> {
					Team team = getTeam(leagueYear, entity, teamToFetch);
					TeamRoster teamRoster = TeamRoster.builder().name(teamToFetch.getLocation() + " " + teamToFetch.getNickname()).build();
					Set<String> rosterNames = new HashSet<>();

					if (team != null && team.getRoster() != null && !isEmpty(team.getRoster().getEntries())) {
						team.getRoster().getEntries().forEach(entry -> {
							if (entry.getPlayerPoolEntry() != null) {
								rosterNames.add(entry.getPlayerPoolEntry().getPlayer().getFullName());
							}
						});
					}

					teamRoster.setRoster(rosterNames);
					teams.add(teamRoster);
				});
			}
			return teams;
		}
		catch (Exception e) {
			logger.error("an error occurred fetching team rosters", e);
		}

		return new ArrayList<>();
	}

	private Team getTeam(String leagueYear, HttpEntity<Topics> entity, Team team) {
		// get teams roster
		String url = "https://fantasy.espn.com/apis/v3/games/flb/seasons/" + leagueYear + "/segments/0/leagues/" + LEAGUE_ID + "/teams/" + team.getId() + "?view=mRoster";
		log.info(String.format("getTeam url: %s", url));
		ResponseEntity<Team> rosterResponse = restTemplate.exchange(url, HttpMethod.GET, entity, Team.class);
		return rosterResponse.getBody();
	}

	@Async
	public CompletableFuture<Player> getPlayerByIdAsync(String leagueYear, String playerId) {
		return CompletableFuture.completedFuture(getPlayerById(leagueYear, playerId));
	}

	public Player getPlayerById(String leagueYear, String playerId) {
		String url = String.format("https://fantasy.espn.com/apis/v3/games/flb/seasons/%s/players/%s?scoringPeriodId=0&view=players_wl", leagueYear, playerId);
		HttpEntity<Topics> entity = new HttpEntity<>(getEspnRequestHeaders(leagueYear, null));
		try {
			ResponseEntity<Player> playerResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Player.class);
			return playerResponseEntity.getBody();
		}
		catch (Exception e) {
			logger.error("An error occurred fetching player details for Player ID: {}", playerId, e);
		}

		return null;
	}
}
