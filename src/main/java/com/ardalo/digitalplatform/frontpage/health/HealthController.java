package com.ardalo.digitalplatform.frontpage.health;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

  @GetMapping("/alive")
  public ResponseEntity<Void> isAlive() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/ready")
  public ResponseEntity<Void> isReady() {
    return ResponseEntity.ok().build();
  }
}
