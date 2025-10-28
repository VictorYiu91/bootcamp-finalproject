package com.bootcamp.finalproject.project_heatmap_ui.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeatMapDto {
  private Long id;
  private String symbol;
  private String name;
  private Double price;
  private Double marketPriceChg;
  private Double marketPriceChgPct;
  private Double marketCap;
  private String industry;
  private LocalDate ipoDate;
  private String webUrl;
  private Double shareOutStanding;
  private String logo;
}
