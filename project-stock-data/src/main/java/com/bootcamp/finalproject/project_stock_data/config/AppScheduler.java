package com.bootcamp.finalproject.project_stock_data.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bootcamp.finalproject.project_stock_data.codelib.GResponse;
import com.bootcamp.finalproject.project_stock_data.entity.StockOHLCVEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockProfileEntity;
import com.bootcamp.finalproject.project_stock_data.entity.StockSymbolEntity;
import com.bootcamp.finalproject.project_stock_data.repository.StockOHLCVRepository;
import com.bootcamp.finalproject.project_stock_data.repository.StockProfileRepository;
import com.bootcamp.finalproject.project_stock_data.service.StockDataService;

@Component
public class AppScheduler {
  @Autowired
  private StockDataService stockDataService;

  @Autowired
  private StockProfileRepository stockProfileRepository;

  @Autowired
  StockOHLCVRepository stockOHLCVRepository;

  @Scheduled(cron = " 0 0 0 * * 1-5")
  public void updateStockProfileEntities() throws InterruptedException{
    GResponse<List<StockProfileEntity>> newStockProfileEntities = this.stockDataService.getStockProfileEntities();
    for (StockProfileEntity s : newStockProfileEntities.getData()){
      StockSymbolEntity newStockSymbolEntity = s.getStockSymbolEntity();
      Optional<StockProfileEntity> existingProfileOpt = stockProfileRepository.findByStockSymbolEntity(newStockSymbolEntity);

      if (existingProfileOpt.isPresent()) {
        StockProfileEntity existingProfile = existingProfileOpt.get();
        existingProfile.setIndustry(s.getIndustry());
        existingProfile.setLogo(s.getLogo());
        existingProfile.setMarketCapitalization(s.getMarketCapitalization());
        existingProfile.setName(s.getName());
        existingProfile.setShareOutstanding(s.getShareOutstanding());
        stockProfileRepository.save(existingProfile);
      } else {
        stockProfileRepository.save(s);
      }
    }
  }

  @Scheduled(cron = " 0 0 16 * * 2-6")
  public void getOHLCVNewEntries() throws InterruptedException {
    LocalDate ytd = LocalDate.now().minusDays(1);
    LocalTime nine29 = LocalTime.of(21, 29, 0);
    LocalTime nine31 = LocalTime.of(21, 31, 0);
    LocalDateTime dateTime1 = LocalDateTime.of(ytd, nine29);
    LocalDateTime dateTime2 = LocalDateTime.of(ytd, nine31);
    Long period1 = dateTime1.atZone(ZoneId.systemDefault()).toEpochSecond();
    Long period2 = dateTime2.atZone(ZoneId.systemDefault()).toEpochSecond();
    GResponse<List<StockOHLCVEntity>> stockOHLCVEntities =
        this.stockDataService.getStockOHLCVEntities(period1, period2);
    this.stockOHLCVRepository.saveAll(stockOHLCVEntities.getData());
  }
}
