package com.bootcamp.finalproject.project_heatmap_ui.model.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class StockOHLCVDTO {
  private Long id;
  private LocalDate date;
  private Double open;
  private Double close;
  private Double low;
  private Double high;
  private Long volume;
  private StockSymbolEntity stockSymbolEntity;

  @Getter
  public static class StockSymbolEntity {
    private Long id;
    private String symbol;
  }
}
