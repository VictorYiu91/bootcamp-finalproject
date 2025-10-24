package com.bootcamp.finalproject.project_stock_data.model.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OHLCVDTO {
  private Chart chart;

  @Getter
  public static class Chart {
    private List<Result> result;

    @Getter
    public static class Result {
      private Meta meta;

      @Getter
      public static class Meta {
        private String currency;
        private String symbol;
        private String exchangeName;
        private String instrumentType;
        private Long firstTradeDate;
        private Long regularMarketTime;
        private Boolean hasPrePostMarketData;
        private Integer gmtoffset;
        private String timezone;
        private String exchangeTimezoneName;
        private Double regularMarketPrice;
        private Double fiftyTwoWeekHigh;
        private Double fiftyTwoWeekLow;
        private Double regularMarketDayHigh;
        private Double regularMarketDayLow;
        private Long regularMarketVolume;
        private String longName;
        private String shortName;
        private Double chartPreviousClose;
        private Integer priceHint;
        private CurrentTradingPeriod CurrentTradingPeriod;

        @Getter
        public static class CurrentTradingPeriod {
          private Pre pre;

          @Getter
          public static class Pre {
            private String timezone;
            private Long end;
            private Long start;
            private Integer gmtoffset;
          }

          private Regular regular;

          @Getter
          public static class Regular {
            private String timezone;
            private Long end;
            private Long start;
            private Integer gmtoffset;
          }

          private Post post;

          @Getter
          public static class Post {
            private String timezone;
            private Long end;
            private Long start;
            private Integer gmtoffset;
          }
        }
        private String dataGranularity;
        private String range;
        private List<String> validRanges;
      }
      private List<Long> timestamp;
      private Indicators indicators;

      @Getter
      public static class Indicators {
        List<Quote> quote;

        @Getter
        public static class Quote {
          List<Double> low;
          List<Double> high;
          List<Long> volume;
          List<Double> open;
          List<Double> close;
        }
        private List<adjclose> adjclose;

        @Getter
        public static class adjclose {
          private List<Double> adjclose;
        }
      }
    }
    private Error error;

    @Getter
    public static class Error {
      private String code;
      private String description;
    }
  }
}
