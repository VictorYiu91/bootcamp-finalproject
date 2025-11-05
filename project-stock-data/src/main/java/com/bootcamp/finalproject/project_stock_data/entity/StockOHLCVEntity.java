package com.bootcamp.finalproject.project_stock_data.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
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
  @Column(nullable = false)
  private LocalDate date;
  @Column(nullable = false)
  private Double open;
  @Column(nullable = false)
  private Double close;
  @Column(nullable = false)
  private Double low;
  @Column(nullable = false)
  private Double high;
  @Column(nullable = false)
  private Long volume;

  @ManyToOne
  @JoinColumn(name = "stock_id", nullable = false)
  private StockSymbolEntity stockSymbolEntity;
}
