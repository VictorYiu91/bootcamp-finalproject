package com.bootcamp.finalproject.project_heatmap_ui.controller.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.finalproject.project_heatmap_ui.controller.HeatMapStockCandleOperation;
import com.bootcamp.finalproject.project_heatmap_ui.dto.HeatMapDto;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto;
import com.bootcamp.finalproject.project_heatmap_ui.mapper.HeatMapDtoMapper;
import com.bootcamp.finalproject.project_heatmap_ui.mapper.StockCandleDtoMapper;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockOHLCVDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;
import com.bootcamp.finalproject.project_heatmap_ui.service.DataService;

@RestController
public class HeatMapStockCandleController
    implements HeatMapStockCandleOperation {
  @Autowired
  private DataService dataService;

  @Autowired
  private HeatMapDtoMapper heatMapDtoMapper;

  @Autowired
  private StockCandleDtoMapper stockCandleDtoMapper;

  @Override
  public HeatMapDto getHeatMapData(String symbol) {
    StockProfileDTO stockProfileDTO = this.dataService.getStockProfile(symbol);
    QuoteDTO quoteDTO = this.dataService.getQuote(symbol);
    return this.heatMapDtoMapper.map(stockProfileDTO, quoteDTO);
  }

  @Override
  public StockCandleDto getStockCandleData(String symbol) {
    StockProfileDTO stockProfileDTO = this.dataService.getStockProfile(symbol);
    List<StockOHLCVDTO> stockOHLCVDTOs = this.dataService.getStockOHLCV(symbol);
    return this.stockCandleDtoMapper.map(stockProfileDTO, stockOHLCVDTOs);
  }

  @Override
  public List<HeatMapDto> getAllHeatMapData() {
    List<String> symbols = List.of("AAPL", "TSLA", "GOOGL");
    return symbols.stream().map(symbol -> {
      try {
        return getHeatMapData(symbol);
      } catch (Exception e) {
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }
}
