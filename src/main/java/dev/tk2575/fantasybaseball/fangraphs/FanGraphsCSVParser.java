package dev.tk2575.fantasybaseball.fangraphs;

import dev.tk2575.fantasybaseball.CSVFile;
import dev.tk2575.fantasybaseball.Utils;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Long.parseLong;

@Log4j2
public class FanGraphsCSVParser {

	private static final String DIRECTORY = "fangraphs/projections";

	private static final String BATTER_HEADERS = "Name,Team,G,PA,AB,H,2B,3B,HR,R,RBI,BB,SO,HBP,SB,CS,AVG,OBP,SLG,OPS,wOBA,Fld,BsR,WAR,playerid";
	private static final String PITCHER_HEADERS = "Name,Team,W,L,SV,HLD,ERA,GS,G,IP,H,ER,HR,SO,BB,WHIP,K/9,BB/9,FIP,WAR,playerid";

	private final List<CSVFile> batterProjectionFiles = new ArrayList<>();
	private final List<CSVFile> pitcherProjectionFiles = new ArrayList<>();

	public FanGraphsCSVParser() {
		for (CSVFile file : Utils.readCSVFilesInDirectory(DIRECTORY)) {
			String fileName = file.getName().toLowerCase();
			if (fileName.contains("batter")) {
				this.batterProjectionFiles.add(file);
			}
			else if (fileName.contains("pitcher")) {
				this.pitcherProjectionFiles.add(file);
			}
		}
	}

	public Map<String, FanGraphsPlayer> getAllProjections() {
		Map<String, FanGraphsPlayer> results = new HashMap<>();
		getBatterProjections().forEach(results::put);
		getPitcherProjections().forEach((k,v) -> results.put(k+"-1", v));
		return results;
	}

	public Map<String, FanGraphsBatter> getBatterProjections() {
		Map<String, FanGraphsBatter> results = new HashMap<>();
		for (CSVFile file : batterProjectionFiles) {
			if (file.getHeader().equals(BATTER_HEADERS)) {
				int line = 1;
				for (String[] row : file.getRowsOfDelimitedValues()) {
					line++;
					try {
						FanGraphsBatter batter = batterParser.apply(row);
						results.put(batter.getName(), batter);
					}
					catch (Exception e) {
						log.error(String.format("Encountered parse error on line %s in file %s. Skipping row", line, file.getName()));
						e.printStackTrace();
					}
				}
			}
			else {
				log.error(String.format("Encountered parse error on header line in file %s; did not match expected headers", file.getName()));
			}
		}
		return results;
	}

	Map<String, FanGraphsPitcher> getPitcherProjections() {
		Map<String, FanGraphsPitcher> results = new HashMap<>();
		for (CSVFile file : pitcherProjectionFiles) {
			if (file.getHeader().equals(PITCHER_HEADERS)) {
				int line = 1;
				for (String[] row : file.getRowsOfDelimitedValues()) {
					line++;
					try {
						FanGraphsPitcher pitcher = pitcherParser.apply(row);
						results.put(pitcher.getName(), pitcher);
					}
					catch (Exception e) {
						log.error(String.format("Encountered parse error on line %s in file %s. Skipping row", line, file.getName()));
						e.printStackTrace();
					}
				}
			}
		}
		return results;
	}

	private final Function<String[], FanGraphsBatter> batterParser = row ->
			/*@formatter:off*/
			FanGraphsBatter.builder()
					.name(row[0])
					.team(row[1])
					.games(parseLong(row[2]))
					.plateAppearances(parseLong(row[3]))
					.atBats(parseLong(row[4]))
					.hits(parseLong(row[5]))
					.doubles(parseLong(row[6]))
					.triples(parseLong(row[7]))
					.homeRuns(parseLong(row[8]))
					.runs(parseLong(row[9]))
					.runsBattedIn(parseLong(row[10]))
					.walks(parseLong(row[11]))
					.strikeOuts(parseLong(row[12]))
					.hitsByPitch(parseLong(row[13]))
					.stolenBases(parseLong(row[14]))
					.caughtStealing(parseLong(row[15]))
					.battingAverage(new BigDecimal(row[16]))
					.onBasePercentage(new BigDecimal(row[17]))
					.sluggingPercentage(new BigDecimal(row[18]))
					.onBasePlusSlugging(new BigDecimal(row[19]))
					.weightedOnBaseAverage(new BigDecimal(row[20]))
					.fieldingValue(new BigDecimal(row[20]))
					.baseRunningValue(new BigDecimal(row[21]))
					.war(new BigDecimal(row[22]))
					.playerId(row[23])
			.build();
		/*@formatter:on*/

	private final Function<String[], FanGraphsPitcher> pitcherParser = row ->
			/*@formatter:off*/
			FanGraphsPitcher.builder()
					.name(row[0])
					.team(row[1])
					.wins(parseLong(row[2]))
					.losses(parseLong(row[3]))
					.saves(parseLong(row[4]))
					.holds(parseLong(row[5]))
					.earnedRunAverage(new BigDecimal(row[6]))
					.gamesStarted(parseLong(row[7]))
					.games(parseLong(row[8]))
					.inningsPitched(new BigDecimal(row[9]))
					.hits(parseLong(row[10]))
					.earnedRuns(parseLong(row[11]))
					.homeRuns(parseLong(row[12]))
					.strikeOuts(parseLong(row[13]))
					.walks(parseLong(row[14]))
					.walksHitsPerInningsPitched(new BigDecimal(row[15]))
					.strikeoutsPerNine(new BigDecimal(row[16]))
					.walksPerNine(new BigDecimal(row[17]))
					.fieldingIndependentPitching(new BigDecimal(row[18]))
					.war(new BigDecimal(row[19]))
					.playerId(row[20])
					.build();
			/*@formatter:on*/

	public static void main(String[] args) {
		FanGraphsCSVParser parser = new FanGraphsCSVParser();
		System.out.println(parser.getBatterProjections());
		System.exit(0);
	}
}
