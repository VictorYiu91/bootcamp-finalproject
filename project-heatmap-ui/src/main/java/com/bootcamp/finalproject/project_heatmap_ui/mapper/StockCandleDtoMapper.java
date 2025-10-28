package com.bootcamp.finalproject.project_heatmap_ui.mapper;

import java.util.List;
import java.util.stream.Collectors;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto;
import com.bootcamp.finalproject.project_heatmap_ui.dto.StockCandleDto.OHLCVs;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockOHLCVDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;

public class StockCandleDtoMapper {
  public StockCandleDto map(StockProfileDTO stockProfileDTO,
      List<StockOHLCVDTO> stockOHLCVDTOs) {
    List<OHLCVs> ohlcvs = stockOHLCVDTOs.stream().map(e -> {
      return OHLCVs.builder()//
          .date(e.getDate())//
          .open(e.getOpen())//
          .high(e.getHigh())//
          .low(e.getLow())//
          .close(e.getClose())//
          .volume(e.getVolume())//
          .build();
    }).collect(Collectors.toList());

    return StockCandleDto.builder()//
        .id(stockProfileDTO.getStockSymbolEntity().getId())//
        .symbol(stockProfileDTO.getStockSymbolEntity().getSymbol())//
        .companyName(stockProfileDTO.getName())//
        .marketCap(stockProfileDTO.getMarketCapitalization())//
        .industry(stockProfileDTO.getIndustry())//
        .shareOutstanding(stockProfileDTO.getShareOutstanding())//
        .logo(stockProfileDTO.getLogo())//
        .ohlcvs(ohlcvs)//
        .build();
  }
}
