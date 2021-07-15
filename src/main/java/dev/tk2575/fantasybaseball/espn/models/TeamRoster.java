package dev.tk2575.fantasybaseball.espn.models;

import dev.tk2575.fantasybaseball.espn.entities.Player;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamRoster {
	String name;
	List<Player> players;
}
