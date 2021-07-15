package dev.tk2575.fantasybaseball.espn.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerAthleteContainer
{
	private PlayerAthlete athlete;
}
