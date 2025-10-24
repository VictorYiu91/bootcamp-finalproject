package com.bootcamp.finalproject.project_data_provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.finalproject.project_data_provider.model.dto.CompanyDTO;
import com.bootcamp.finalproject.project_data_provider.model.dto.QuoteDTO;
import com.bootcamp.finalproject.project_data_provider.service.ProjectDataService;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public QuoteDTO getQuote(String symbol) {
    String quoteUrl = UriComponentsBuilder.newInstance() //
        .scheme("https")//
        .host("finnhub.io") //
        .path("/api/v1/quote") //
        .queryParam("symbol", symbol) //
        .queryParam("token", "d3r056hr01qna05k6fngd3r056hr01qna05k6fo0") //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(quoteUrl, QuoteDTO.class);
  }

  @Override
  public CompanyDTO getCompanyProfile(String symbol) {
    String companyProfileUrl = UriComponentsBuilder.newInstance() //
        .scheme("https")//
        .host("finnhub.io") //
        .path("api/v1/stock/profile2") //
        .queryParam("symbol", symbol) //
        .queryParam("token", "d3r056hr01qna05k6fngd3r056hr01qna05k6fo0") //
        .build() //
        .toUriString();
    return this.restTemplate.getForObject(companyProfileUrl, CompanyDTO.class);
  }
}
