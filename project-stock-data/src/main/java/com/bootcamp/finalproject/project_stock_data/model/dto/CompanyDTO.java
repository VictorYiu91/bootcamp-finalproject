package com.bootcamp.finalproject.project_stock_data.model.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CompanyDTO {
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
  private String webUrl;
}
