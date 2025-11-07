package com.bootcamp.finalproject.project_data_provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bootcamp.finalproject.project_data_provider.finnhub.client.FinnhubClient;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnProfileDTO;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnQuoteDTO;
import com.bootcamp.finalproject.project_data_provider.service.ProjectDataService;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {
  @Value("${service-api.finnhub.api-key}")
  private String apiKey;

  @Autowired
  private FinnhubClient finnhubClient;

  @Override
  public FinnQuoteDTO getQuote(String symbol) {
    return this.finnhubClient.getQuote(symbol, apiKey);
  }

  @Override
  public FinnProfileDTO getCompanyProfile(String symbol) {
    return this.finnhubClient.getProfile(symbol, apiKey);
  }
}
