package com.bootcamp.finalproject.project_stock_data.service;

import java.util.List;
import com.bootcamp.finalproject.project_stock_data.Codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.QuoteDTO;

public interface StockDataService {
  QuoteDTO getQuote(String symbol);
  List<String> getSymbols();
  CompanyDTO getStockProfile(String symbol);
  GResponse<List<StockProfileEntity>> getStockProfileEntities() throws InterruptedException;
  OHLCVDTO getOhlcv(String symbol, Long period1, Long period2);
  GResponse<List<StockOHLCVEntity>> getStockOHLCVEntities(Long period1, Long period2) throws InterruptedException;
}
