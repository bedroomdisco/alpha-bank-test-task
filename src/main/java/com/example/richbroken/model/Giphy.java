package com.example.richbroken.model;

import java.util.*;

public class Giphy {
    private final List<String> urls;

    public Giphy(List<String> urls) {
        if (urls != null) {
            this.urls = urls;
        } else {
            this.urls = new ArrayList<>();
        }
    }

    public List<String> getUrls() {
        return urls;
    }

    public Optional<String> getRandomUrl() {
        if (urls.size() > 0) {
            Random random = new Random();
            return Optional.of(urls.get(random.nextInt(urls.size())));
        }
        return Optional.empty();
    }


}
