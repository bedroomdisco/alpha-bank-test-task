package com.example.richbroken.service.clients;

import com.example.richbroken.config.GiphyFeignConfig;
import com.example.richbroken.model.Giphy;
import com.example.richbroken.utils.GiphyQueryParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="giphy", url = "${giphy.baseurl}", configuration = GiphyFeignConfig.class)
public interface GiphyFeignClient {

    @GetMapping
    Giphy getGiphy(@SpringQueryMap GiphyQueryParams queryParams);
}
