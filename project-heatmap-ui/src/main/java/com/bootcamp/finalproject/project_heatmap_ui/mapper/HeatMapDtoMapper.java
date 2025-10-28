package com.bootcamp.finalproject.project_heatmap_ui.mapper;

import com.bootcamp.finalproject.project_heatmap_ui.dto.HeatMapDto;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_heatmap_ui.model.dto.StockProfileDTO;

public class HeatMapDtoMapper {
  public HeatMapDto map(StockProfileDTO stockProfileDTO, QuoteDTO quoteDTO) {
    return HeatMapDto.builder()//
        .id(stockProfileDTO.getStockSymbolEntity().getId())//
        .symbol(stockProfileDTO.getStockSymbolEntity().getSymbol())//
        .name(stockProfileDTO.getName())//
        .price(quoteDTO.getCurrentPrice())//
        .marketPriceChg(quoteDTO.getChange())//
        .marketPriceChgPct(quoteDTO.getPercentageChange())//
        .marketCap(stockProfileDTO.getMarketCapitalization())//
        .industry(stockProfileDTO.getIndustry())//
        .ipoDate(stockProfileDTO.getIpoDate())//
        .webUrl(stockProfileDTO.getWebUrl())//
        .shareOutStanding(stockProfileDTO.getShareOutstanding())//
        .logo(stockProfileDTO.getLogo())//
        .build();
  }
}
