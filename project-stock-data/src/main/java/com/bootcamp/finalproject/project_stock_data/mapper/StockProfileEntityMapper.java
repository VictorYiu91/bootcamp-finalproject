package com.bootcamp.finalproject.project_stock_data.mapper;

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
    String ticker = companyDTO.getTicker();
    StockSymbolEntity stockSymbolEntity = this.stockSymbolRepository
        .findBySymbol(ticker).orElseThrow(() -> new IllegalArgumentException(
            "Symbol not found in database: " + ticker));

    return StockProfileEntity.builder()//
        .industry(companyDTO.getFinnhubIndustry())//
        .logo(companyDTO.getLogo())//
        .marketCapitalization(companyDTO.getMarketCapitalization())//
        .name(companyDTO.getName())//
        .shareOutstanding(companyDTO.getShareOutstanding())//
        .ipoDate(companyDTO.getIpo())//
        .webUrl(companyDTO.getWebUrl())//
        .stockSymbolEntity(stockSymbolEntity)//
        .build();
  }
}
