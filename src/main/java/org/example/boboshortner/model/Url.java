package org.example.boboshortner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@SuperBuilder
@AllArgsConstructor
public class Url {
    private String key;
    private String shortUrl;
    private String originalUrl;

    @Builder.Default
    private AtomicInteger used = new AtomicInteger(0);
}
