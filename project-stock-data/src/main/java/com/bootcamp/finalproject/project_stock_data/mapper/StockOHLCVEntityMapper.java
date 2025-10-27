package com.bootcamp.finalproject.project_stock_data.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO;
import com.bootcamp.finalproject.project_stock_data.repository.StockSymbolRepository;

@Component
public class StockOHLCVEntityMapper {
  @Autowired
  private StockSymbolRepository stockSymbolRepository;

  public StockOHLCVEntity map(OHLCVDTO ohlcvDTO) {
    String symbol = ohlcvDTO.getChart().getResult().getFirst().getMeta().getSymbol();
    StockSymbolEntity stockSymbolEntity = this.stockSymbolRepository
        .findBySymbol(symbol).orElseThrow(() -> new IllegalArgumentException(
            "Symbol not found in database: " + symbol));

    Long timestamp =
        ohlcvDTO.getChart().getResult().getFirst().getTimestamp().getFirst();
    Double open = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst().getOpen().getFirst();
    Double high = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst().getHigh().getFirst();
    Double low = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst().getLow().getFirst();
    Double close = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst().getClose().getFirst();
    Long volume = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst().getVolume().getFirst();
    LocalDate date = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault()).toLocalDate();

    return StockOHLCVEntity.builder()//
        .date(date)//
        .open(open)//
        .high(high)//
        .low(low)//
        .close(close)//
        .volume(volume)//
        .stockSymbolEntity(stockSymbolEntity)//
        .build();
  }
}
