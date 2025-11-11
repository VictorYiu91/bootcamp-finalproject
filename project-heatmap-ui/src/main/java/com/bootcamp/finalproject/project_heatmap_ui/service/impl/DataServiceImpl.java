package com.bootcamp.finalproject.project_heatmap_ui.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_heatmap_ui.dto.HeatMapDto;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto;
import com.bootcamp.finalproject.project_heatmap_ui.mapper.HeatMapDtoMapper;
import com.bootcamp.finalproject.project_heatmap_ui.mapper.StockCandleDtoMapper;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockOHLCVDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;
import com.bootcamp.finalproject.project_heatmap_ui.service.DataService;

@Service
public class DataServiceImpl implements DataService {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private HeatMapDtoMapper heatMapDtoMapper;

  @Autowired
  private StockCandleDtoMapper stockCandleDtoMapper;

  @Override
  public List<String> getSymbols() {
    String symbolsUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8081") //
        .path("symbols")//
        .build() //
        .toUriString();
    return Arrays
        .asList(this.restTemplate.getForObject(symbolsUrl, String[].class));
  }

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
    return Arrays.asList(
        this.restTemplate.getForObject(ohlcvUrl, StockOHLCVDTO[].class));
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

  @Override
  public List<StockProfileDTO> getTopStockProfiles() {
    String topCompanyUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8081") //
        .path("dbtopstockprofiles") //
        .build() //
        .toUriString();
    return Arrays.asList(
        this.restTemplate.getForObject(topCompanyUrl, StockProfileDTO[].class));
  }

  @Override
  public StockCandleDto getStockCandleData(String symbol) {
    StockProfileDTO stockProfileDTO = this.getStockProfile(symbol);
    List<StockOHLCVDTO> stockOHLCVDTOs = this.getStockOHLCV(symbol);
    return this.stockCandleDtoMapper.map(stockProfileDTO, stockOHLCVDTOs);
  }

  @Override
  public List<HeatMapDto> getAllHeatMapData() {
    List<StockProfileDTO> topStockProfilesDTO = this.getTopStockProfiles();
    List<HeatMapDto> heatMapDtos = new ArrayList<>();
    HeatMapDto heatMapDto = null;
    for (StockProfileDTO s : topStockProfilesDTO) {
      try {
        QuoteDTO quoteDTO = this.getQuote(s.getStockSymbolEntity().getSymbol());
        heatMapDto = this.heatMapDtoMapper.map(s, quoteDTO);
      } catch (Exception e) {
        System.out.println("Error fetching quote for symbol: " + s.getStockSymbolEntity().getSymbol() + " - " + e.getMessage());
        continue;
      }
      heatMapDtos.add(heatMapDto);
    }
    return heatMapDtos;
  }
}
