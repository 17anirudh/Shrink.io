package com.example.demo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Frontend {
    public String index() {
        return "index"; 
    }

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> payload) {
        Backend backend = new Backend();
        String longUrl = payload.get("url");
        if (longUrl == null || longUrl.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "URL cannot be empty");
            return ResponseEntity.ok(response);
        }
        String shortUrl = backend.isValidURL(longUrl);
        
        if (shortUrl.equals("Invalid")) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid URL");
            return ResponseEntity.ok(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("success", shortUrl);

        return ResponseEntity.ok(response);
    }
}