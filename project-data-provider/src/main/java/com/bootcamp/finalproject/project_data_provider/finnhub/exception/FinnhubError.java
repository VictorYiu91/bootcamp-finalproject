package com.bootcamp.finalproject.project_data_provider.finnhub.exception;

import com.bootcamp.finalproject.project_data_provider.lib.Errorable;

public enum FinnhubError implements Errorable{
  PROFILE2_EX(99, "Finnhub profile2 Webcall Error."),//
  JSON_PROCESSING_EX(98, "Finnhub profile2 JSON Serialization Error."),
  QUOTE_EX(97, "Finnhub quote Webcall Error."),
  ;

  private int code;
  private String message;

  private FinnhubError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
