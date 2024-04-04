package org.example.boboshortner.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.boboshortner.model.Url;
import org.example.boboshortner.settings.UrlSettings;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ShortenerService {

    private final UrlSettings urlSettings;

    private Map<String, Url> urlStorage;

    @PostConstruct
    void init() {
        urlStorage = new ConcurrentHashMap<>();
    }

    public Url createShortUrl(String originalUrl) {
        String randomKey = RandomStringUtils.randomAlphanumeric(urlSettings.getLength());

        Url url = Url.builder().key(randomKey)
                .originalUrl(originalUrl)
                .shortUrl(urlSettings.getBaseUrl() + randomKey)
                .build();

        urlStorage.put(randomKey, url);

        return url;
    }

    public String getOriginalUrl(String key) {
        return Optional.ofNullable(urlStorage.get(key))
                .map(url -> {
                    url.getUsed().incrementAndGet();
                    return url.getOriginalUrl();
                }).orElse(null);
    }
}
