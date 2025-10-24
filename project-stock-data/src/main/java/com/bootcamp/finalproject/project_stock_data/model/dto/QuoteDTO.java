package com.bootcamp.finalproject.project_stock_data.model.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class QuoteDTO {
  private String symbol;
  private Double currentPrice;
  private Double change;
  private Double percentageChange;
  private Double highPrice;
  private Double lowPrice;
  private Double openPrice;
  private Double previousClosePrice;
  private LocalDateTime dateTime = LocalDateTime.now();
}
