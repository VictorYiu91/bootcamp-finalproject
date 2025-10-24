package com.bootcamp.finalproject.project_stock_data.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.mapper.StockOHLCVEntityMapper;
import com.bootcamp.finalproject.project_stock_data.mapper.StockProfileEntityMapper;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_stock_data.repository.StockSymbolRepository;
import com.bootcamp.finalproject.project_stock_data.service.StockDataService;

@Service
public class StockDataServiceImpl implements StockDataService {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StockSymbolRepository stockSymbolRepository;

  @Autowired
  private StockProfileEntityMapper stockProfileEntityMapper;

  @Autowired
  private StockOHLCVEntityMapper stockOHLCVEntityMapper;

  @Override
  public QuoteDTO getQuote(String symbol) {
    String quoteUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8080") //
        .path("quote") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(quoteUrl, QuoteDTO.class);
  }

  @Override
  public List<String> getSymbols() {
    List<StockSymbolEntity> stockSymbolEntities =
        this.stockSymbolRepository.findAll();
    List<String> symbols = stockSymbolEntities.stream().map(e -> {
      return e.getSymbol();
    }).collect(Collectors.toList());
    return symbols;
  }

  @Override
  public CompanyDTO getStockProfile(String symbol) {
    String companyUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("localhost:8080") //
        .path("companyprofile") //
        .queryParam("symbol", symbol) //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(companyUrl, CompanyDTO.class);
  }

  @Override
  public List<StockProfileEntity> getStockProfileEntities()
      throws InterruptedException {
    // List<String> symbols = this.getSymbols();
    List<String> symbols = List.of("TSLA", "AAPL", "GOOG");
    List<CompanyDTO> stockProfiles = new ArrayList<>();
    for (String symbol : symbols) {
      CompanyDTO company = this.getStockProfile(symbol);
      stockProfiles.add(company);

      Thread.sleep(Duration.ofSeconds(2));
    }
    List<StockProfileEntity> stockProfileEntities =
        stockProfiles.stream().map(e -> {
          return this.stockProfileEntityMapper.map(e);
        }).collect(Collectors.toList());
    return stockProfileEntities;
  }

  @Override
  public OHLCVDTO getOhlcv(String symbol, Long period1, Long period2) {
    String ohlcvUrl = UriComponentsBuilder.newInstance() //
        .scheme("http")//
        .host("query1.finance.yahoo.com") //
        .path("/v8/finance/chart") //
        .pathSegment(symbol).queryParam("period1", period1)//
        .queryParam("period2", period2)//
        .queryParam("interval", "1d")//
        .queryParam("events", "history").build() //
        .toUriString();
    System.out.println(ohlcvUrl);
    return this.restTemplate.getForObject(ohlcvUrl, OHLCVDTO.class);
  }

  @Override
  public List<StockOHLCVEntity> getStockOHLCVEntities(Long period1,
      Long period2) throws InterruptedException {
    // List<String> symbols = this.getSymbols();
    List<String> symbols = List.of("TSLA", "AAPL", "GOOG");
    List<OHLCVDTO> ohlcvDTOs = new ArrayList<>();

    for (String symbol : symbols) {
      OHLCVDTO ohlcvDTO = this.getOhlcv(symbol, period1, period2);
      ohlcvDTOs.add(ohlcvDTO);

      Thread.sleep(Duration.ofSeconds(2));
    }
    List<StockOHLCVEntity> stockOHLCVEntities = ohlcvDTOs.stream().map(e -> {
      return this.stockOHLCVEntityMapper.map(e);
    }).collect(Collectors.toList());
    return stockOHLCVEntities;
  }
}
