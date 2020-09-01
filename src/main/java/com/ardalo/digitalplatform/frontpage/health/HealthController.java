package com.ardalo.digitalplatform.frontpage.health;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

  @GetMapping("/alive")
  public ResponseEntity<String> isAlive() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/ready")
  public ResponseEntity<String> isReady() {
    return ResponseEntity.ok().build();
  }
}
