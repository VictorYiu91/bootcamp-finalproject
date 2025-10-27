package com.bootcamp.finalproject.project_stock_data.repository.errorlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.finalproject.project_stock_data.entity.ErrorLogEntity;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLogEntity, Long>{
  
}
