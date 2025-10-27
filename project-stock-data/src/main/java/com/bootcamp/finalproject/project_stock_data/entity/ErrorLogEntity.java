package com.bootcamp.finalproject.project_stock_data.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="error_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Builder.Default
  private LocalDateTime time = LocalDateTime.now();
  private String errorMessage;
}
