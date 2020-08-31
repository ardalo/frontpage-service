package com.ardalo.digitalplatform.frontpage.frontpage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontpageController {

  @GetMapping("/api/pages/frontpage")
  public ModelAndView frontpage() {
    ModelAndView modelAndView = new ModelAndView("frontpage");
    modelAndView.getModel().put("currentTimestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    return modelAndView;
  }
}
