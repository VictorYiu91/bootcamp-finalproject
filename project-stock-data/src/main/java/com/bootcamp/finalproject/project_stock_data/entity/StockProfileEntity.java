package com.bootcamp.finalproject.project_stock_data.entity;

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
import lombok.Setter;

@Entity
@Table(name = "stock_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String industry;

  private String logo;

  @Column(name = "market_capitalization")
  private Double marketCapitalization;

  @Column(nullable = false)
  private String name;

  @Column(name = "shares_outstanding")
  private Double shareOutstanding;

  @ManyToOne
  @JoinColumn(name = "stock_id", nullable = false)
  private StockSymbolEntity stockSymbolEntity;
}
