package com.bootcamp.finalproject.project_stock_data.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_ohlcv")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOHLCVEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate date;
  private Double open;
  private Double close;
  private Double low;
  private Double high;
  private Long volume;

  @ManyToOne
  @JoinColumn(name = "stock_id", nullable = false)
  private StockSymbolEntity stockSymbolEntity;
}
