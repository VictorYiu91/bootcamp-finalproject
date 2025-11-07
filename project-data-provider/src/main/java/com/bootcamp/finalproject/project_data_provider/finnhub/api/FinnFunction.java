package com.bootcamp.finalproject.project_data_provider.finnhub.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnProfileDTO;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnQuoteDTO;
import com.bootcamp.finalproject.project_data_provider.finnhub.exception.FinnhubError;
import com.bootcamp.finalproject.project_data_provider.finnhub.util.Finnhub;
import com.bootcamp.finalproject.project_data_provider.lib.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface FinnFunction {
  RestTemplate getRestTemplate();

  default FinnProfileDTO getProfile(String symbol, String apiKey) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("symbol", List.of(symbol));

    System.out.println("apikey: " + apiKey);
    params.put("token", List.of(apiKey));

    String profileUrl = UriComponentsBuilder.newInstance()//
        .scheme("https")//
        .host(Finnhub.API_HOST)//
        .pathSegment(Finnhub.VERSION)//
        .path(Finnhub.ENDPOINT_STOCKPROFILE)//
        .queryParams(params)//
        .build()//
        .toUriString();

    System.out.println("Profile URL: " + profileUrl);
    ResponseEntity<String> response =
        this.getRestTemplate().getForEntity(profileUrl, String.class);

    try {
      if (!response.getStatusCode().equals(HttpStatus.OK)) {
        throw new BusinessException(FinnhubError.PROFILE2_EX);
      }
      return new ObjectMapper().readValue(response.getBody(),
          FinnProfileDTO.class);
    } catch (JsonProcessingException e) {
      throw new BusinessException(FinnhubError.JSON_PROCESSING_EX);
    }
  }

    default FinnQuoteDTO getQuote(String symbol, String apiKey) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("symbol", List.of(symbol));

    System.out.println("apikey: " + apiKey);
    params.put("token", List.of(apiKey));

    String quoteUrl = UriComponentsBuilder.newInstance()//
        .scheme("https")//
        .host(Finnhub.API_HOST)//
        .pathSegment(Finnhub.VERSION)//
        .path(Finnhub.ENDPOINT_QUOTE)//
        .queryParams(params)//
        .build()//
        .toUriString();

    System.out.println("QuoteURL: " + quoteUrl);
    ResponseEntity<String> response =
        this.getRestTemplate().getForEntity(quoteUrl, String.class);

    try {
      if (!response.getStatusCode().equals(HttpStatus.OK)) {
        throw new BusinessException(FinnhubError.QUOTE_EX);
      }
      return new ObjectMapper().readValue(response.getBody(),
          FinnQuoteDTO.class);
    } catch (JsonProcessingException e) {
      throw new BusinessException(FinnhubError.JSON_PROCESSING_EX);
    }
  }
}
