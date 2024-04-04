package org.example.boboshortner.controller;

import lombok.RequiredArgsConstructor;
import org.example.boboshortner.model.Url;
import org.example.boboshortner.service.ShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortenerService shortenerService;

    @GetMapping("/")
    public String displayForm(Model model) {
        model.addAttribute("originalUrl", "");
        model.addAttribute("shortenedUrl", "");
        return "shortener";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl, Model model) {
        Url url = shortenerService.createShortUrl(originalUrl);
        String shortUrl = url.getShortUrl();
        model.addAttribute("originalUrl", originalUrl);
        model.addAttribute("shortenedUrl", shortUrl);
        return "shortener";
    }

    @GetMapping(path = "/{key}")
    public ResponseEntity<?> redirectToSpecificURL(@PathVariable String key) {
        String originalUrl = shortenerService.getOriginalUrl(key);
        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }

}
