package dev.tk2575.fantasybaseball;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofMillis;

@Component
public class Beans {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(ofMillis(500000)).setReadTimeout(ofMillis(500000)).build();
	}
}
