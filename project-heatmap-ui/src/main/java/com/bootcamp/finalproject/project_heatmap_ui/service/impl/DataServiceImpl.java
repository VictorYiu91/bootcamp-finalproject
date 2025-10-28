package com.bootcamp.finalproject.project_heatmap_ui.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockOHLCVDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;
import com.bootcamp.finalproject.project_heatmap_ui.service.DataService;

@Service
public class DataServiceImpl implements DataService {
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public QuoteDTO getQuote(String symbol) {
    String quoteUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8081") //
        .path("quote") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(quoteUrl, QuoteDTO.class);
  }

  @Override
  public List<StockOHLCVDTO> getStockOHLCV(String symbol) {
    String ohlcvUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8081") //
        .path("dbstockohlcv") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    return Arrays.asList(this.restTemplate.getForObject(ohlcvUrl, StockOHLCVDTO[].class));
  }

  @Override
  public StockProfileDTO getStockProfile(String symbol) {
    String companyUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8081") //
        .path("dbstockprofile") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(companyUrl, StockProfileDTO.class);
  }
}
