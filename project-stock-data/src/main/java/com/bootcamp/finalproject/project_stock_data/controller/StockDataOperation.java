package com.bootcamp.finalproject.project_stock_data.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.finalproject.project_stock_data.codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.QuoteDTO;

public interface StockDataOperation {
  @GetMapping(value = "/quote")
  QuoteDTO getQuote(@RequestParam String symbol);
  
  @GetMapping(value = "/symbols")
  List<String> getSymbols();

  @GetMapping(value = "/company")
  CompanyDTO getStockProfile(@RequestParam String symbol);

  @GetMapping(value = "/companies")
  GResponse<List<StockProfileEntity>> getStockProfileEntities() throws InterruptedException;

  @GetMapping(value= "/dbstockprofile")
  StockProfileEntity getStockProfileEntityDB(@RequestParam String symbol);

  @GetMapping(value = "/ohlcv")
  GResponse<List<StockOHLCVEntity>> getStockOHLCVEntities(@RequestParam Long period1, @RequestParam Long period2) throws InterruptedException;
  
  @GetMapping(value= "/dbstockohlcv")
  List<StockOHLCVEntity> getStockOHLCVEntitiesDB(@RequestParam String symbol);
}
