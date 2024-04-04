package org.example.boboshortner.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/bobo-shortner")
public class ShortnerController {


    private static final Map<String, String> SHORT_URL_MAP = new ConcurrentHashMap<>();
    private static final Integer URL_LENGTH = 5;

    @GetMapping
    public ResponseEntity<?> redirectToSpecificURL(@RequestParam String targetUrl) {
        return ResponseEntity.status(302)
                .header("Location", SHORT_URL_MAP.getOrDefault(targetUrl, targetUrl))
                .build();
    }

    @PostMapping
    public ResponseEntity<String> shortUrl(@RequestParam String targetUrl) {
        return ResponseEntity.ok(SHORT_URL_MAP.computeIfAbsent(targetUrl, currUrl -> shortenUrl()));
    }

    private String shortenUrl() {
        return RandomStringUtils.randomAlphanumeric(URL_LENGTH);
    }
}
