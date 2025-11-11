package com.bootcamp.finalproject.project_stock_data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;

@Repository
public interface StockSymbolRepository
    extends JpaRepository<StockSymbolEntity, Long> {
  List<StockSymbolEntity> findByIsActive(String isActive);

  Optional<StockSymbolEntity> findBySymbol(String symbol);

  boolean existsBySymbol(String symbol);
}
