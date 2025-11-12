package com.bootcamp.finalproject.project_heatmap_ui.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeatMapPageController {
  @GetMapping(value = "/heatmap")
  public String getHeatMapPage(Model model) {
    return "heatmap";
  }

  @GetMapping(value = "/candlestick")
  public String getCandleStickPage(Model model) {
    return "candlestick";
  }
}
