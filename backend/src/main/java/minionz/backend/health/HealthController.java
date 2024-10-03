package minionz.backend.health;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
  @RequestMapping(method = RequestMethod.GET, value = "/health")
  public String test() {
    return "health ckeck!";
  }
}