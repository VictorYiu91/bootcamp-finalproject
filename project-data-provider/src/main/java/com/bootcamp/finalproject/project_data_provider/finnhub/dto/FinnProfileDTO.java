package com.bootcamp.finalproject.project_data_provider.finnhub.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class FinnProfileDTO {
  private String country;
  private String currency;
  private String estimateCurrency;
  private String exchange;
  private String finnhubIndustry;
  private LocalDate ipo;
  private String logo;
  private Double marketCapitalization;
  private String name;
  private String phone;
  private Double shareOutstanding;
  private String ticker;
  private String weburl;
}
