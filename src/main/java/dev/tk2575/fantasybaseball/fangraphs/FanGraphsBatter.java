package dev.tk2575.fantasybaseball.fangraphs;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@ToString
class FanGraphsBatter implements FanGraphsPlayer {
	private String team;
	private String name;
	private String playerId;
	private long games;
	private BigDecimal war;

	private long plateAppearances;
	private long atBats;
	private long hits;
	private long doubles;
	private long triples;
	private long homeRuns;
	private long runs;
	private long runsBattedIn;
	private long walks;
	private long strikeOuts;
	private long hitsByPitch;
	private long stolenBases;
	private long caughtStealing;
	private BigDecimal battingAverage;
	private BigDecimal onBasePercentage;
	private BigDecimal sluggingPercentage;
	private BigDecimal onBasePlusSlugging;
	private BigDecimal weightedOnBaseAverage;
	private BigDecimal fieldingValue;
	private BigDecimal baseRunningValue;
}
