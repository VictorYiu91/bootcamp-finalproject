package com.bootcamp.finalproject.project_data_provider.mapper;

import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_data_provider.dto.QuoteDto;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnQuoteDTO;

@Component
public class QuoteDtoMapper {
  public QuoteDto map(FinnQuoteDTO quoteDTO, String symbol) {
    return QuoteDto.builder()//
        .symbol(symbol)//
        .currentPrice(quoteDTO.getCurrentPrice())//
        .change(quoteDTO.getChange())//
        .percentageChange(quoteDTO.getPercentageChange())//
        .highPrice(quoteDTO.getHighPrice())//
        .lowPrice(quoteDTO.getLowPrice())//
        .openPrice(quoteDTO.getOpenPrice())//
        .previousClosePrice(quoteDTO.getPreviousClosePrice())//
        .build();
  }
}
