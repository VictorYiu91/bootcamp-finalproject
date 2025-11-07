package com.bootcamp.finalproject.project_data_provider.finnhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FinnQuoteDTO {
  @JsonProperty(value = "c")
  private Double currentPrice;
  @JsonProperty(value = "d")
  private Double change;
  @JsonProperty(value = "dp")
  private Double percentageChange;
  @JsonProperty(value = "h")
  private Double highPrice;
  @JsonProperty(value = "l")
  private Double lowPrice;
  @JsonProperty(value = "o")
  private Double openPrice;
  @JsonProperty(value = "pc")
  private Double previousClosePrice;
  @JsonProperty(value = "t")
  private Long timestamp;
}
