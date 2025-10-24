package com.bootcamp.finalproject.project_stock_data.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;

@Repository
public interface StockProfileRepository extends JpaRepository<StockProfileEntity, Long>{
  Optional<StockProfileEntity> findByStockSymbolEntity(StockSymbolEntity stockSymbolEntity);
}
