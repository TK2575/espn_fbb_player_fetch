package dev.tk2575.fantasybaseball.fangraphs;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@ToString
public class FanGraphsBatter implements FanGraphsPlayer {
	private final String team;
	private final String name;
	private final String playerId;
	private final long games;
	private final BigDecimal war;

	private final long plateAppearances;
	private final long atBats;
	private final long hits;
	private final long doubles;
	private final long triples;
	private final long homeRuns;
	private final long runs;
	private final long runsBattedIn;
	private final long walks;
	private final long strikeOuts;
	private final long hitsByPitch;
	private final long stolenBases;
	private final long caughtStealing;
	private final BigDecimal battingAverage;
	private final BigDecimal onBasePercentage;
	private final BigDecimal sluggingPercentage;
	private final BigDecimal onBasePlusSlugging;
	private final BigDecimal weightedOnBaseAverage;
	private final BigDecimal fieldingValue;
	private final BigDecimal baseRunningValue;

	@Override
	public long getPoints() {
		long singles = hits - doubles - triples - homeRuns;
		/*@formatter:off*/
		return singles + (2 * doubles) + (3 * triples) + (4 * homeRuns)
				+ (2 * stolenBases) + (-2 * caughtStealing) + runs
				+ (2 * runsBattedIn) + walks + (-2 * strikeOuts);
		/*@formatter:on*/
	}
}
