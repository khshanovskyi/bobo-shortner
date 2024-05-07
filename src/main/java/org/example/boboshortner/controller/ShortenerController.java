package org.example.boboshortner.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

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
    public Object  shortenUrl(@RequestParam String originalUrl, HttpServletRequest request, Model model) {
        if (StringUtils.isBlank(originalUrl) || !(originalUrl.startsWith("http://") || originalUrl.startsWith("https://"))) {
            ModelAndView modelAndView = new ModelAndView("badrequest");
            modelAndView.addObject("message", "No URL provided, or url doesn't have 'http://' prefix");
            return modelAndView;
        }

        Url url = shortenerService.createShortUrl(request.getRequestURL().toString(), originalUrl);
        String shortUrl = url.getShortUrl();
        model.addAttribute("originalUrl", originalUrl);
        model.addAttribute("shortenedUrl", shortUrl);
        return "shortener";
    }

    @GetMapping(path = "/{key}")
    public Object redirectToSpecificURL(@PathVariable String key) {
        try {
            String originalUrl = shortenerService.getOriginalUrl(key);
            if (originalUrl == null) {
                ModelAndView modelAndView = new ModelAndView("notfound");
                modelAndView.addObject("message", "No URL found for this key: " + key);
                return modelAndView;
            }
            return ResponseEntity.status(302)
                    .header("Location", originalUrl)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while redirecting", e);
        }
    }

}
