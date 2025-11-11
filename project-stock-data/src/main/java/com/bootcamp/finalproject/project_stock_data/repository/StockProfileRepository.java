package com.bootcamp.finalproject.project_stock_data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;

@Repository
public interface StockProfileRepository extends JpaRepository<StockProfileEntity, Long>{
  Optional<StockProfileEntity> findByStockSymbolEntity(StockSymbolEntity stockSymbolEntity);
  
  Optional<StockProfileEntity> findByStockSymbolEntity_Symbol(String symbol);

  @Query("SELECT p FROM StockProfileEntity p ORDER BY p.marketCapitalization DESC")
  List<StockProfileEntity> findTop20ByMarketCap(PageRequest pageRequest);
}
