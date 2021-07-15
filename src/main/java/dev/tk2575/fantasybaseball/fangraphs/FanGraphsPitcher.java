package dev.tk2575.fantasybaseball.fangraphs;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@ToString
public class FanGraphsPitcher implements FanGraphsPlayer {

	private final String team;
	private final String name;
	private final String playerId;
	private final long games;
	private final BigDecimal war;

	private final long wins;
	private final long losses;
	private final long saves;
	private final long holds;
	private final BigDecimal earnedRunAverage;
	private final long gamesStarted;
	private final BigDecimal inningsPitched;
	private final long hits;
	private final long earnedRuns;
	private final long homeRuns;
	private final long strikeOuts;
	private final long walks;
	private final BigDecimal walksHitsPerInningsPitched;
	private final BigDecimal strikeoutsPerNine;
	private final BigDecimal walksPerNine;
	private final BigDecimal fieldingIndependentPitching;

	@Override
	public long getPoints() {
		/*@formatter:off*/
		return (inningsPitched.setScale(0, RoundingMode.FLOOR).longValue())
				+ (7 * wins) + (-5 * losses) + (5 * saves) + strikeOuts
				+ (-2 * earnedRuns) + (-1 * hits) + (-1 * walks);
		/*@formatter:on*/
	}
}
