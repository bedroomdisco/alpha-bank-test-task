package com.example.richbroken.service.clients;


import com.example.richbroken.config.OpenExchangeConfig;
import com.example.richbroken.model.OpenExchangeRates;
import com.example.richbroken.utils.OpenExchangeQueryParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="openexchange", url = "${openexchange.baseurl}", configuration = OpenExchangeConfig.class)
public interface OpenExchangeFeignClient {

    @GetMapping(value="/{param}")
    OpenExchangeRates getExchangeRate (@RequestParam("param") String param, @SpringQueryMap OpenExchangeQueryParams queryParams);

}
