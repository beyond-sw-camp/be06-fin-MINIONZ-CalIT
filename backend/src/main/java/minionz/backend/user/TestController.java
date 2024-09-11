package minionz.backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> ex01() {
        return ResponseEntity.ok("Hello World");
    }
}
