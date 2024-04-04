package org.example.boboshortner.controller;

import lombok.RequiredArgsConstructor;
import org.example.boboshortner.model.Url;
import org.example.boboshortner.service.ShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortenerService shortenerService;

    @GetMapping
    public String displayForm(Model model) {
        model.addAttribute("originalUrl", "");
        model.addAttribute("shortenedUrl", "");
        return "shortener";
    }

    @PostMapping
    public String shortenUrl(@RequestParam String originalUrl, Model model) {
        Url url = shortenerService.createShortUrl(originalUrl);
        String shortUrl = url.getShortUrl();
        model.addAttribute("originalUrl", originalUrl);
        model.addAttribute("shortenedUrl", shortUrl);
        return "shortener";
    }

    @GetMapping(path = "/{key}")
    public ResponseEntity<?> redirectToSpecificURL(@PathVariable String key) {
        try {
            String originalUrl = shortenerService.getOriginalUrl(key);
            if (originalUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found");
            }
            return ResponseEntity.status(302)
                    .header("Location", originalUrl)
                    .build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while redirecting");
        }
    }

}
