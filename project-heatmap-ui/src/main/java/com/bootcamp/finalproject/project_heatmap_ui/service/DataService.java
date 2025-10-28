package com.bootcamp.finalproject.project_heatmap_ui.service;

import java.util.List;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockOHLCVDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;

public interface DataService {
  List<String> getSymbols();
  QuoteDTO getQuote(String symbol);
  List<StockOHLCVDTO> getStockOHLCV(String symbol);
  StockProfileDTO getStockProfile(String symbol);

}
