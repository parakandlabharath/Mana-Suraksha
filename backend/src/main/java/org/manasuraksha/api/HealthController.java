package org.manasuraksha.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@org.springframework.web.bind.annotation.CrossOrigin(origins = "http://localhost:5500")
public class HealthController {
    @GetMapping("/api/v1/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "mana-suraksha-api");
    }
}
