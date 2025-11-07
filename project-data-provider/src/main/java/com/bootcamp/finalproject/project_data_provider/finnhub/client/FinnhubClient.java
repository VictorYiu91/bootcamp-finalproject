package com.bootcamp.finalproject.project_data_provider.finnhub.client;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import com.bootcamp.finalproject.project_data_provider.finnhub.api.FinnFunction;

public class FinnhubClient implements FinnFunction {
  private RestTemplate restTemplate;

  public FinnhubClient() {
    this.restTemplate = new RestTemplateBuilder()//
    .connectTimeout(Duration.ofSeconds(5L))//
    .readTimeout(Duration.ofSeconds(5L))//
    .build();

    this.restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().set("User-Agent",
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
      request.getHeaders().set("Accept", "application/json");
      request.getHeaders().set("Accept-Language", "en-US,en;q=0.9");
      return execution.execute(request, body);
    });
  }

  @Override
  public RestTemplate getRestTemplate() {
    return this.restTemplate;
  }
}
