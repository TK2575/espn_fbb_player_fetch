package dev.tk2575.fantasybaseball.espn.services;

import org.springframework.http.HttpHeaders;

public abstract class ESPNService {

	public static final String LEAGUE_ID = "1450425788";

	protected HttpHeaders getEspnRequestHeaders(String leagueYear, String filters) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Referer", "http://fantasy.espn.com/baseball/recentactivity?leagueId=" + LEAGUE_ID + "&endDate=20190729&seasonId=" + leagueYear + "&startDate=20190727&teamId=-1&transactionType=2&activityType=2&page=1");
		headers.add("X-Fantasy-Source", "kona");
		headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		headers.add("X-Fantasy-Platform", "kona-PROD-6b1bde3ecf8dde941512a5c0d02d8fd8a7461f47");
		headers.add("Origin", "http://fantasy.espn.com");
		headers.add("Cache-Control", "no-cache");
		headers.add("Host", "fantasy.espn.com");
		headers.add("Cookie", "espn_s2=AECnyI7SbpkSmRq1e54dBUpvqq2AZZt8Jaxd+VLuAm/rk4u+WF/O2cGhTvyynwT9dDTzgeHpaUJByGwTY5ztJt0Snfd4l62TzIlufe8kjBdTESkZbzECGxpD8AN6CWl93R9eygEHvZPa4/5oN8eXAPVOUMW9uzpLsMK7zZAltwiT4M1WqNwi5ik7ohlW480yCPVL0eC+Vinn8EuSVi42cMF69l+Vlr82RMT+BATvMnlfQbZTRGjtzZW46SqM1FlCAlm5abo5qrV8xmTsevSmTw+d; SWID={581AFCF0-BF14-44D0-A003-5EE6A4002048}");
		headers.add("Connection", "keep-alive");
		headers.add("cache-control", "no-cache");

		if (filters != null) {
			headers.add("X-Fantasy-Filter", filters);
		}

		return headers;
	}

}
