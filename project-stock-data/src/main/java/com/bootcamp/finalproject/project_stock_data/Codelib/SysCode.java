package com.bootcamp.finalproject.project_stock_data.Codelib;

public enum SysCode {
  OK(0, "OK."), //
  FAIL(99999, "Fail."), //
  ;

  private Integer code;
  private String message;

  private SysCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
