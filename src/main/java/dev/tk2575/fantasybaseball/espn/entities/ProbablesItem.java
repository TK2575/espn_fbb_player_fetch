package dev.tk2575.fantasybaseball.espn.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProbablesItem{
	private String shortDisplayName;
	private Athlete athlete;
	private String displayName;
	private String name;
	private String abbreviation;
	private int playerId;
}
