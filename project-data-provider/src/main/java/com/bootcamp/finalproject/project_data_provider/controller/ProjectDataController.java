package com.bootcamp.finalproject.project_data_provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.finalproject.project_data_provider.dto.QuoteDto;
import com.bootcamp.finalproject.project_data_provider.model.dto.CompanyDTO;

public interface ProjectDataController {
  @GetMapping(value = "/quote")
  QuoteDto getQuote(@RequestParam String symbol);

  @GetMapping(value = "/companyprofile")
  CompanyDTO getCompanyProfile(@RequestParam String symbol);
}
