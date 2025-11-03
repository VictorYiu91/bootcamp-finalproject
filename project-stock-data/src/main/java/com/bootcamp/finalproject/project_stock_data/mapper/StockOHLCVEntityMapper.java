package com.bootcamp.finalproject.project_stock_data.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO;
import com.bootcamp.finalproject.project_stock_data.model.dto.OHLCVDTO.Chart.Result.Indicators.Quote;
import com.bootcamp.finalproject.project_stock_data.repository.StockSymbolRepository;

@Component
public class StockOHLCVEntityMapper {
  @Autowired
  private StockSymbolRepository stockSymbolRepository;

  public StockOHLCVEntity map(OHLCVDTO ohlcvDTO) {
    String symbol = ohlcvDTO.getChart().getResult().getFirst().getMeta().getSymbol();
    String yahooSymbol = symbol;
    if (yahooSymbol.equals("BRK-B")) {
      yahooSymbol = "BRK.B";
    } else if (symbol.equals("BF-B")) {
      yahooSymbol = "BF.B";
    }

    StockSymbolEntity stockSymbolEntity = this.stockSymbolRepository
        .findBySymbol(yahooSymbol).orElseThrow(() -> new IllegalArgumentException(
            "Symbol not found in database: " + symbol));

    Quote quote = ohlcvDTO.getChart().getResult().getFirst().getIndicators()
        .getQuote().getFirst();

    Long timestamp =
        ohlcvDTO.getChart().getResult().getFirst().getTimestamp().getFirst();
    Double open = quote.getOpen().getFirst();
    Double high = quote.getHigh().getFirst();
    Double low = quote.getLow().getFirst();
    Double close = quote.getClose().getFirst();
    Long volume = quote.getVolume().getFirst();
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
