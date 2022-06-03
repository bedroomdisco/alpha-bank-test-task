package com.example.richbroken.service;

import com.example.richbroken.model.Giphy;
import com.example.richbroken.model.OpenExchangeRates;
import com.example.richbroken.service.clients.GiphyFeignClient;
import com.example.richbroken.service.clients.OpenExchangeFeignClient;
import com.example.richbroken.utils.GiphyQueryParams;
import com.example.richbroken.utils.OpenExchangeQueryParams;
import com.example.richbroken.utils.RichOrBroken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class RichBrokenService {
    private final GiphyFeignClient giphyFeignClient;
    private final OpenExchangeFeignClient openExchangeFeignClient;
    public static final Logger logger = LoggerFactory.getLogger(RichBrokenService.class);

    @Value("${giphy.apikey}")
    private String giphyAPIKey;

    @Value("${openexchange.apiid}")
    private String openExchangeAPIId;

    // == constructors ==
    @Autowired
    public RichBrokenService(GiphyFeignClient giphyFeignClient, OpenExchangeFeignClient openExchangeFeignClient) {
        this.giphyFeignClient = giphyFeignClient;
        this.openExchangeFeignClient = openExchangeFeignClient;
    }

    // == public methods ==
    public String compareExchangeRate(String currency) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        OpenExchangeQueryParams queryParams = new OpenExchangeQueryParams();
        Optional<Float> currentCurrencyRate = openExchangeFeignClient.getExchangeRate(today + ".json", queryParams).getRate(currency);
        Optional<Float> yesterdayCurrencyRate = openExchangeFeignClient.getExchangeRate(yesterday + ".json", queryParams).getRate(currency);
        if (currentCurrencyRate.isEmpty() || yesterdayCurrencyRate.isEmpty()) {
            throw new IllegalArgumentException("No currency exchange rates available");
        }
        if (currentCurrencyRate.get() > yesterdayCurrencyRate.get()) {
            logger.info("I am kinda rich");
            return getGiphyUrl(RichOrBroken.RICH);
        } else {
            logger.info("Haven't really worked out as I expected daamnn");
            return getGiphyUrl(RichOrBroken.BROKEN);
        }
    }
    // == private methods ==
    private String getGiphyUrl(RichOrBroken gifType) throws IllegalArgumentException {
        if (gifType == RichOrBroken.RICH) {
            Giphy giphy = giphyFeignClient.getGiphy(new GiphyQueryParams(giphyAPIKey, "rich", 10, 0, "g", "en"));
            if (giphy.getRandomUrl().isEmpty()) {
                throw new IllegalArgumentException("No Giphy URLs available for redirection");
            }
            return giphy.getRandomUrl().get();
        } else {
            Giphy giphy = giphyFeignClient.getGiphy(new GiphyQueryParams(giphyAPIKey, "broke", 10, 0, "g", "en"));
            if (giphy.getRandomUrl().isEmpty()) {
                throw new IllegalArgumentException("No Giphy URLs available for redirection");
            }
            return giphy.getRandomUrl().get();
        }
    }
}
