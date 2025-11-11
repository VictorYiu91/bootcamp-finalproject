package com.bootcamp.finalproject.project_stock_data.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_stock_data.codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.codelib.SysCode;
import com.bootcamp.finalproject.project_stock_data.entity.ErrorLogEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.mapper.StockOHLCVEntityMapper;
import com.bootcamp.finalproject.project_stock_data.mapper.StockProfileEntityMapper;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_stock_data.repository.StockOHLCVRepository;
import com.bootcamp.finalproject.project_stock_data.repository.StockProfileRepository;
import com.bootcamp.finalproject.project_stock_data.repository.StockSymbolRepository;
import com.bootcamp.finalproject.project_stock_data.repository.errorlog.ErrorLogRepository;
import com.bootcamp.finalproject.project_stock_data.service.StockDataService;

@Service
public class StockDataServiceImpl implements StockDataService {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StockSymbolRepository stockSymbolRepository;

  @Autowired
  private StockProfileRepository stockProfileRepository;

  @Autowired
  private StockOHLCVRepository stockOHLCVRepository;

  @Autowired
  private ErrorLogRepository errorLogRepository;

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
  public GResponse<List<StockProfileEntity>> getStockProfileEntities()
      throws InterruptedException {
    boolean isFail = false;
    List<String> symbols = this.getSymbols();
    // List<String> symbols = List.of("TSLA", "AAPL", "MSFT");
    List<StockProfileEntity> stockProfileEntities = new ArrayList<>();
    List<String> warnings = new ArrayList<>();
    int idx = 0;

    for (String symbol : symbols) {
      try {
        CompanyDTO company = this.getStockProfile(symbol);
        StockProfileEntity stockProfileEntity =
            this.stockProfileEntityMapper.map(company);
        stockProfileEntities.add(stockProfileEntity);
      } catch (IllegalArgumentException e) {
        isFail = true;
        warnings.add(e.getMessage() + " - skipped");
        this.errorLogRepository.save(ErrorLogEntity.builder()
            .errorMessage(e.getMessage() + " - skipped").build());
      } catch (Exception e) {
        isFail = true;
        warnings
            .add("Failed to process symbol " + symbol + ": " + e.getMessage());
        ErrorLogEntity errorLog = ErrorLogEntity.builder()
            .errorMessage(
                "Failed to process symbol " + symbol + ": " + e.getMessage())
            .build();
        this.errorLogRepository.save(errorLog);
      }
      idx++;
      System.out
          .println((idx) + "/" + symbols.size() + " completed: " + symbol);
      Thread.sleep(Duration.ofSeconds(3));
    }
    if (isFail == true) {
      return GResponse.<List<StockProfileEntity>>builder().code(SysCode.FAIL)
          .messages(warnings).data(stockProfileEntities).build();
    } else {
      return GResponse.<List<StockProfileEntity>>builder()
          .data(stockProfileEntities).build();
    }
  }

  @Override
  public StockProfileEntity getStockProfileEntityDB(String symbol) {
    StockProfileEntity stockProfileEntity = null;
    try {
      if (!stockSymbolRepository.existsBySymbol(symbol)) {
        throw new IllegalArgumentException("Symbol not found: " + symbol);
      }
      stockProfileEntity = stockProfileRepository
          .findByStockSymbolEntity_Symbol(symbol).orElse(null);
    } catch (IllegalArgumentException e) {
      ErrorLogEntity errorLog =
          ErrorLogEntity.builder().errorMessage(e.getMessage()).build();
      this.errorLogRepository.save(errorLog);
    }
    return stockProfileEntity;
  }

  @Override
  public OHLCVDTO getOhlcv(String symbol, Long period1, Long period2) {
    String ohlcvUrl = UriComponentsBuilder.newInstance() //
        .scheme("https")//
        .host("query1.finance.yahoo.com") //
        .path("/v8/finance/chart/{symbol}") //
        .queryParam("period1", period1)//
        .queryParam("period2", period2)//
        .queryParam("interval", "1d")//
        .queryParam("events", "history")//
        .buildAndExpand(symbol)//
        .toUriString();
    return this.restTemplate.getForObject(ohlcvUrl, OHLCVDTO.class);
  }

  @Override
  public GResponse<List<StockOHLCVEntity>> getStockOHLCVEntities(Long period1,
      Long period2) throws InterruptedException {
    boolean isFail = false;
    List<String> symbols = this.getSymbols();
    // List<String> symbols = List.of("TSLA", "AAPL", "MSFT");
    List<StockOHLCVEntity> stockOHLCVEntities = new ArrayList<>();
    List<String> warnings = new ArrayList<>();
    int idx = 0;

    for (String symbol : symbols) {
      String yahooSymbol = symbol;
      if (symbol.equals("BRK.B")) {
        yahooSymbol = "BRK-B";
      } else if (symbol.equals("BF.B")) {
        yahooSymbol = "BF-B";
      }

      try {
        OHLCVDTO ohlcvDTO = this.getOhlcv(yahooSymbol, period1, period2);
        StockOHLCVEntity stockOHLCVEntity =
            this.stockOHLCVEntityMapper.map(ohlcvDTO);
        stockOHLCVEntities.add(stockOHLCVEntity);
      } catch (IllegalArgumentException e) {
        isFail = true;
        warnings.add(e.getMessage() + " - skipped");
        ErrorLogEntity errorLog = ErrorLogEntity.builder()
            .errorMessage(e.getMessage() + " - skipped").build();

        this.errorLogRepository.save(errorLog);
      } catch (Exception e) {
        isFail = true;
        warnings
            .add("Failed to process symbol " + symbol + ": " + e.getMessage());

        ErrorLogEntity errorLog = ErrorLogEntity.builder()
            .errorMessage(
                "Failed to process symbol " + symbol + ": " + e.getMessage())
            .build();
        this.errorLogRepository.save(errorLog);
      }
      idx++;
      System.out
          .println((idx) + "/" + symbols.size() + " completed: " + symbol);
      Thread.sleep(Duration.ofSeconds(3));
    }
    if (isFail == true) {
      return GResponse.<List<StockOHLCVEntity>>builder().code(SysCode.FAIL)
          .messages(warnings).data(stockOHLCVEntities).build();
    } else {
      return GResponse.<List<StockOHLCVEntity>>builder()
          .data(stockOHLCVEntities).build();
    }
  }

  @Override
  public List<StockOHLCVEntity> getStockOHLCVEntitiesDB(String symbol) {
    List<StockOHLCVEntity> stockOHLCVEntities = null;
    try {
      if (!stockSymbolRepository.existsBySymbol(symbol)) {
        throw new IllegalArgumentException("Symbol not found: " + symbol);
      }
      stockOHLCVEntities =
          stockOHLCVRepository.findAllByStockSymbolEntity_Symbol(symbol);
    } catch (IllegalArgumentException e) {
      ErrorLogEntity errorLog =
          ErrorLogEntity.builder().errorMessage(e.getMessage()).build();
      this.errorLogRepository.save(errorLog);
    }
    return stockOHLCVEntities;
  }

  @Override
  public List<StockProfileEntity> getTopProfilesPerIndustry() {
    PageRequest top20 =
        PageRequest.of(0, 20, Sort.by("marketCapitalization").descending());
    return stockProfileRepository.findTop20ByMarketCap(top20);
  }
}
