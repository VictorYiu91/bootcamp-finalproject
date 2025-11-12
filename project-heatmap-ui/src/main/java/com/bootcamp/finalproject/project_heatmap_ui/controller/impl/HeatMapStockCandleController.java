package com.bootcamp.finalproject.project_heatmap_ui.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.finalproject.project_heatmap_ui.controller.HeatMapStockCandleOperation;
import com.bootcamp.finalproject.project_heatmap_ui.dto.HeatMapDto;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto;
import com.bootcamp.finalproject.project_heatmap_ui.mapper.HeatMapDtoMapper;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;
import com.bootcamp.finalproject.project_heatmap_ui.service.DataService;

@RestController
public class HeatMapStockCandleController
    implements HeatMapStockCandleOperation {
  @Autowired
  private DataService dataService;

  @Autowired
  private HeatMapDtoMapper heatMapDtoMapper;


  @Override
  public HeatMapDto getHeatMapData(String symbol) {
    StockProfileDTO stockProfileDTO = this.dataService.getStockProfile(symbol);
    QuoteDTO quoteDTO = this.dataService.getQuote(symbol);
    return this.heatMapDtoMapper.map(stockProfileDTO, quoteDTO);
  }

  @Override
  public StockCandleDto getStockCandleData(String symbol) {
    return this.dataService.getStockCandleData(symbol);
  }

  @Override
  public List<HeatMapDto> getAllHeatMapData(){
    return this.dataService.getAllHeatMapData();
  }
}
