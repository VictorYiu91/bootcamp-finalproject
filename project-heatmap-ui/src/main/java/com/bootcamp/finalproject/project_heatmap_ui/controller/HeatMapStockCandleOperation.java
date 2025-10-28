package com.bootcamp.finalproject.project_heatmap_ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.finalproject.project_heatmap_ui.dto.HeatMapDto;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto;

public interface HeatMapStockCandleOperation {

  @GetMapping(value = "/heatmap")
  HeatMapDto getHeatMapData(@RequestParam String symbol);
  
  @GetMapping(value = "/stockcandle")
  StockCandleDto getStockCandleData (@RequestParam String symbol);
}
