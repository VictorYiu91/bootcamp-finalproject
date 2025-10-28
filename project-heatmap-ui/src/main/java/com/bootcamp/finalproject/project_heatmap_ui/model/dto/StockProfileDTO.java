package com.bootcamp.finalproject.project_heatmap_ui.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StockProfileDTO {
  private Long id;
  private String industry;
  private String logo;
  private Double marketCapitalization;
  private String name;
  private Double shareOutstanding;
  private LocalDate ipoDate;
  private String webUrl;
  private StockSymbolEntity stockSymbolEntity;
  private LocalDateTime lastUpdated;

  @Getter
  public static class StockSymbolEntity {
    private Long id;
    private String symbol;
  }
}
