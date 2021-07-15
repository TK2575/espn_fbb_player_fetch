package dev.tk2575.fantasybaseball.espn.entities;

import lombok.*;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

	int playerId;
	String fullName;
	int defaultPositionId;
	int proTeamId;
	boolean injured;
	String displayBatsThrows;

	String positionName;
	String opposingTeamId;
	String opposingTeamHomeAway;
	String opposingTeamName;
	String gameTime;
	String opposingTeamSummary;
	String opsVsLefties;
	String opsVsRighties;
	PlayerAthlete opposingPitcher;

	long points;
}
