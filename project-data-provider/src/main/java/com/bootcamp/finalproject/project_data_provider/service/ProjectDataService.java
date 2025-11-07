package com.bootcamp.finalproject.project_data_provider.service;

import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnProfileDTO;
import com.bootcamp.finalproject.project_data_provider.finnhub.dto.FinnQuoteDTO;

public interface ProjectDataService {
  FinnQuoteDTO getQuote(String symbol);
  FinnProfileDTO getCompanyProfile(String symbol);
}
