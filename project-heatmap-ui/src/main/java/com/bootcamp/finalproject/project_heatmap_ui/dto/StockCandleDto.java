package com.bootcamp.finalproject.project_heatmap_ui.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockCandleDto {
  private Long id;
  private String symbol;
  private String companyName;
  private Double marketCap;
  private String industry;
  private Double shareOutstanding;
  private String logo;
  private List<OHLCVs> ohlcvs;

  @Getter
  @Builder
  public static class OHLCVs {
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
  }
}
