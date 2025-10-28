package com.bootcamp.finalproject.project_stock_data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;

@Repository
public interface StockOHLCVRepository extends JpaRepository<StockOHLCVEntity, Long>{
  Optional<StockOHLCVEntity> findByStockSymbolEntity(StockSymbolEntity stockSymbolEntity);
  List<StockOHLCVEntity> findAllByStockSymbolEntity_Symbol(String symbol);
}
