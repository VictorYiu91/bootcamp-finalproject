package com.bootcamp.finalproject.project_stock_data.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_stock_data.Codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.repository.StockProfileRepository;
import com.bootcamp.finalproject.project_stock_data.service.StockDataService;

@Component
public class AppStartRunner implements CommandLineRunner{
  @Autowired
  private StockDataService stockDataService;

  @Autowired
  private StockProfileRepository stockProfileRepository;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Deleting stock profiles.");
    this.stockProfileRepository.deleteAll();
    System.out.println("Retrieving updated stock profiles.");
    GResponse<List<StockProfileEntity>> stockProfileEntities = this.stockDataService.getStockProfileEntities();
    this.stockProfileRepository.saveAll(stockProfileEntities.getData());
  }
}
