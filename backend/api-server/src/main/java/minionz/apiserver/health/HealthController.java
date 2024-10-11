package minionz.apiserver.health;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @RequestMapping(method = RequestMethod.GET, value = "/health")
  public String test() {
    return "health check!";
  }
}