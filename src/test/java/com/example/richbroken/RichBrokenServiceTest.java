package com.example.richbroken;

import com.example.richbroken.model.Giphy;
import com.example.richbroken.model.OpenExchangeRates;
import com.example.richbroken.service.RichBrokenService;
import com.example.richbroken.service.clients.GiphyFeignClient;
import com.example.richbroken.service.clients.OpenExchangeFeignClient;
import com.example.richbroken.utils.GiphyQueryParams;
import com.example.richbroken.utils.OpenExchangeQueryParams;
import org.junit.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RichBrokenServiceTest {
    @Value("${giphy.apikey}")
    private String giphyAPIKey;

    @Mock
    private GiphyFeignClient giphyFeignClient;
    @Mock
    private OpenExchangeFeignClient openExchangeFeignClient;

    @InjectMocks
    private RichBrokenService richBrokenService;


    @Test
    public void givenNullMapInOpenExchangeResponse_whenServiceInvoked_thenIllegalArgumentException() {
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(null));
        assertThrows(IllegalArgumentException.class, () -> richBrokenService.compareExchangeRate(""));
    }

    @Test
    public void givenNoCurrencyInOpenExchangeResponse_whenServiceInvoked_thenIllegalArgumentException() {
        Map<String,Float> rates = Map.of("AED", 1.0f);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(rates));
        assertThrows(IllegalArgumentException.class, () -> richBrokenService.compareExchangeRate(""));
    }

    @Test
    public void givenNullValueInOpenExchangeResponse_whenServiceInvoked_thenIllegalArgumentException() {
        Map<String,Float> rates = new HashMap<>();
        rates.put("", null);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(rates));
        assertThrows(IllegalArgumentException.class, () -> richBrokenService.compareExchangeRate(""));
    }


    @Test
    public void givenCurrencyRateRisen_whenServiceInvoked_thenGetGiphyWithRich() {
        Map<String,Float> today = Map.of("AED", 2.0f);
        Map<String,Float> yesterday = Map.of("AED", 1.0f);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(today)).thenReturn(new OpenExchangeRates(yesterday));
        when(giphyFeignClient.getGiphy(any())).thenReturn(new Giphy(List.of("")));
        richBrokenService.compareExchangeRate("AED");
        verify(giphyFeignClient).getGiphy(new GiphyQueryParams(giphyAPIKey, "rich",
                10, 0, "g", "en"));
    }

    @Test
    public void givenCurrencyRateDropped_whenServiceInvoked_thenGetGiphyWithBroke() {
        Map<String,Float> today = Map.of("AED", 1.0f);
        Map<String,Float> yesterday = Map.of("AED", 2.0f);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(today)).thenReturn(new OpenExchangeRates(yesterday));
        when(giphyFeignClient.getGiphy(any())).thenReturn(new Giphy(List.of("")));
        richBrokenService.compareExchangeRate("AED");
        verify(giphyFeignClient).getGiphy(new GiphyQueryParams(giphyAPIKey, "broke",
                10, 0, "g", "en"));
    }

    @Test
    public void givenNullGiphyResponse_whenServiceInvoked_thenIllegalArgumentException() {
        Map<String,Float> rates = Map.of("", 1.0f);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(rates));
        when(giphyFeignClient.getGiphy(any())).thenReturn(new Giphy(null));
        assertThrows(IllegalArgumentException.class, () -> richBrokenService.compareExchangeRate(""));
    }

    @Test
    public void givenEmptyGiphyResponse_whenServiceInvoked_thenIllegalArgumentException() {
        Map<String,Float> rates = Map.of("", 1.0f);
        when(openExchangeFeignClient.getExchangeRate(any(String.class), any(OpenExchangeQueryParams.class)))
                .thenReturn(new OpenExchangeRates(rates));
        when(giphyFeignClient.getGiphy(any())).thenReturn(new Giphy(new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> richBrokenService.compareExchangeRate(""));
    }

















}
