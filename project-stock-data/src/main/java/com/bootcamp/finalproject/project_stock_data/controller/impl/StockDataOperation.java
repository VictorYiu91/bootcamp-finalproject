package com.bootcamp.finalproject.project_stock_data.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.finalproject.project_stock_data.Codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.controller.StockDataController;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_stock_data.service.StockDataService;

@RestController
public class StockDataOperation implements StockDataController {
  @Autowired
  private StockDataService stockDataService;

  @Override
  public QuoteDTO getQuote(String symbol) {
    return this.stockDataService.getQuote(symbol);
  }

  @Override
  public List<String> getSymbols() {
    return this.stockDataService.getSymbols();
  }

  @Override
  public CompanyDTO getStockProfile(String symbol) {
    return this.stockDataService.getStockProfile(symbol);
  }

  @Override
  public GResponse<List<StockProfileEntity>> getStockProfileEntities()
      throws InterruptedException {
    return this.stockDataService.getStockProfileEntities();
  }

  @Override
  public GResponse<List<StockOHLCVEntity>> getStockOHLCVEntities(Long period1, Long period2)
      throws InterruptedException {
    return this.stockDataService.getStockOHLCVEntities(period1, period2);
  }
}
