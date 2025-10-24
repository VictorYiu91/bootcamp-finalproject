package com.bootcamp.finalproject.project_data_provider.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuoteDto {
  private String symbol;
  private Double currentPrice;
  private Double change;
  private Double percentageChange;
  private Double highPrice;
  private Double lowPrice;
  private Double openPrice;
  private Double previousClosePrice;
  @Builder.Default
  private LocalDateTime dateTime = LocalDateTime.now();
}
