package com.bootcamp.finalproject.project_data_provider.service;

import com.bootcamp.finalproject.project_data_provider.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_data_provider.model.dto.QuoteDTO;

public interface ProjectDataService {
  QuoteDTO getQuote(String symbol);
  CompanyDTO getCompanyProfile(String symbol);
}
