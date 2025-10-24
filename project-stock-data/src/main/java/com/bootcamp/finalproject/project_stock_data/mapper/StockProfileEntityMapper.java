package com.bootcamp.finalproject.project_stock_data.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_stock_data.repository.StockSymbolRepository;

@Component
public class StockProfileEntityMapper {
  @Autowired
  private StockSymbolRepository stockSymbolRepository;

  public StockProfileEntity map(CompanyDTO companyDTO) {
    List<StockSymbolEntity> stockSymbolEntities =
        this.stockSymbolRepository.findAll();
    StockSymbolEntity stockSymbolEntity = stockSymbolEntities.stream()
        .filter(e -> e.getSymbol().equals(companyDTO.getTicker()))
        .collect(Collectors.toList()).getFirst();

    return StockProfileEntity.builder()//
        .industry(companyDTO.getFinnhubIndustry())//
        .logo(companyDTO.getLogo())//
        .marketCapitalization(companyDTO.getMarketCapitalization())//
        .name(companyDTO.getName())//
        .shareOutstanding(companyDTO.getShareOutstanding())//
        .stockSymbolEntity(stockSymbolEntity)//
        .build();
  }
}
