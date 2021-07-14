package dev.tk2575.fantasybaseball.controllers;

import dev.tk2575.fantasybaseball.models.TeamRoster;
import dev.tk2575.fantasybaseball.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/rosters")
public class RostersController {

	PlayerService playerService;

	@Autowired
	RostersController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TeamRoster> getTeamRosters(@RequestParam(value = "year") String leagueYear) {
		return playerService.getTeamRosters(leagueYear);
	}

}
