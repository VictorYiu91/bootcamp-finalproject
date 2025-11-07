package com.bootcamp.finalproject.project_data_provider.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.finalproject.project_data_provider.controller.ProjectDataOperation;
import com.bootcamp.finalproject.project_data_provider.dto.QuoteDto;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnProfileDTO;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnQuoteDTO;
import com.bootcamp.finalproject.project_data_provider.mapper.QuoteDtoMapper;
import com.bootcamp.finalproject.project_data_provider.service.ProjectDataService;

@RestController
public class ProjectDataController implements ProjectDataOperation {
  @Autowired
  private ProjectDataService projectDataService;

  @Autowired
  private QuoteDtoMapper quoteDtoMapper;

  @Override
  public QuoteDto getQuote(String symbol) {
    FinnQuoteDTO quoteDTO = this.projectDataService.getQuote(symbol);
    return this.quoteDtoMapper.map(quoteDTO, symbol);
  }

  @Override
  public FinnProfileDTO getCompanyProfile(String symbol) {
    return this.projectDataService.getCompanyProfile(symbol);
  }
}
