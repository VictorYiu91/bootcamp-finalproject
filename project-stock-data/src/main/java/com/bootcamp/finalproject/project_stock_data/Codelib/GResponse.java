package com.bootcamp.finalproject.project_stock_data.codelib;

import java.time.LocalDateTime;
import java.util.List;

public class GResponse<T> {
  private LocalDateTime time;
  private SysCode code;
  private List<String> messages;
  private T data;

  public LocalDateTime getTime() {
    return this.time;
  }

  public SysCode getCode() {
    return this.code;
  }

  public List<String> getMessages() {
    return this.messages;
  }

  public T getData() {
    return this.data;
  }

  public static <T> Builder<T> builder() {
    return new Builder<>();
  }

  public GResponse(Builder<T> builder) {
    this.time = builder.time;
    this.code = builder.code;
    this.messages = builder.messages;
    this.data = builder.data;
  }

  public static class Builder<T> {
    private LocalDateTime time;
    private SysCode code;
    private List<String> messages;
    private T data;

    public Builder() {
      this.time = LocalDateTime.now();
      this.code = SysCode.OK;
      this.messages = List.of("OK.");
    }

    public Builder<T> code(SysCode code) {
      this.code = code;
      return this;
    }

    public Builder<T> messages(List<String> messages) {
      this.messages = messages;
      return this;
    }

    public Builder<T> data(T data) {
      this.data = data;
      return this;
    }

    public GResponse<T> build() {
      return new GResponse<>(this);
    }
  }
}
