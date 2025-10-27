package com.bootcamp.finalproject.project_data_provider.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.finalproject.project_data_provider.controller.ProjectDataOperation;
import com.bootcamp.finalproject.project_data_provider.dto.QuoteDto;
import com.bootcamp.finalproject.project_data_provider.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_data_provider.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_data_provider.service.ProjectDataService;

@RestController
public class ProjectDataController implements ProjectDataOperation {
  @Autowired
  private ProjectDataService projectDataService;

  @Override
  public QuoteDto getQuote(String symbol) {
    QuoteDTO quoteDTO = this.projectDataService.getQuote(symbol);
    return QuoteDto.builder()//
        .symbol(symbol)//
        .currentPrice(quoteDTO.getCurrentPrice())//
        .change(quoteDTO.getChange())//
        .percentageChange(quoteDTO.getPercentageChange())//
        .highPrice(quoteDTO.getHighPrice())//
        .lowPrice(quoteDTO.getLowPrice())//
        .openPrice(quoteDTO.getOpenPrice())//
        .previousClosePrice(quoteDTO.getPreviousClosePrice())//
        .build();
  }

  @Override
  public CompanyDTO getCompanyProfile(String symbol) {
    return this.projectDataService.getCompanyProfile(symbol);
  }
}
