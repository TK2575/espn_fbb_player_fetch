package dev.tk2575.fantasybaseball.services;

import org.springframework.http.HttpHeaders;

public abstract class ESPNService {

	public static final String LEAGUE_ID = "SECRET";

	protected HttpHeaders getEspnRequestHeaders(String leagueYear, String filters) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Referer", "http://fantasy.espn.com/baseball/recentactivity?leagueId=" + LEAGUE_ID + "&endDate=20190729&seasonId=" + leagueYear + "&startDate=20190727&teamId=-1&transactionType=2&activityType=2&page=1");
		headers.add("X-Fantasy-Source", "kona");
		headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		headers.add("X-Fantasy-Platform", "kona-PROD-6b1bde3ecf8dde941512a5c0d02d8fd8a7461f47");
		headers.add("Origin", "http://fantasy.espn.com");
		headers.add("Cache-Control", "no-cache");
		headers.add("Host", "fantasy.espn.com");
		headers.add("Cookie", "secret");
		headers.add("Connection", "keep-alive");
		headers.add("cache-control", "no-cache");

		if (filters != null) {
			headers.add("X-Fantasy-Filter", filters);
		}

		return headers;
	}

}
